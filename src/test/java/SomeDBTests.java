import org.flywaydb.test.annotation.FlywayTest;
import org.flywaydb.test.junit5.FlywayTestExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import taweryawer.config.TestBeansConfig;
import taweryawer.entities.*;
import taweryawer.entities.enums.OrderStatus;
import taweryawer.repository.OrderRepository;
import taweryawer.repository.UserRepository;
import taweryawer.service.OrderService;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = {TestBeansConfig.class})
@DataJpaTest
@ExtendWith({SpringExtension.class})
@ExtendWith({FlywayTestExtension.class})
@EnableAutoConfiguration
@EntityScan("taweryawer")
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SomeDBTests {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    @DisplayName("getUser")
    @FlywayTest
    public void getUser() throws Exception {
        User user = userRepository.getUserByTelegramId("test");
        assertThat(user.getMachineId()).isEqualTo("test");
    }

    @BeforeEach
    public void initWithSomeFoodCategoryAndUser() {
        Category category = new Category();
        category.setName("testcategory");
        testEntityManager.persist(category);
        Category category1 = testEntityManager.find(Category.class, 1L);
        assertThat(category1).isNotNull();

        PriceCategory priceCategory = new PriceCategory();
        priceCategory.setTitle("testPriceCategory");
        testEntityManager.persist(priceCategory);

        PriceLabel priceLabel = new PriceLabel();
        priceLabel.setPriceCategory(priceCategory);
        priceLabel.setValue(100.34);
        testEntityManager.persist(priceLabel);

        Food food = new Food();
        food.setCategory(testEntityManager.getEntityManager().getReference(Category.class, 1L));
        ArrayList<PriceLabel> priceLabels = new ArrayList<>();
        priceLabels.add(priceLabel);
        food.setPriceLabels(priceLabels);
        food.setTitle("testtitle");
        testEntityManager.persist(food);

        User user = new User();
        user.setName("testname");
        user.setMachineId("testmachineid");
        testEntityManager.persist(user);
    }

    @Test
    @FlywayTest
    public void createFoodWithCategoryAndUserThenSaveOrderAndOrderPieceThenFindAndCheck() throws Exception {
        User user = userRepository.getUserByTelegramId("testmachineid");

        Order order = new Order();
        order.setOrderStatus(OrderStatus.NEW);
        order.setDateTime(LocalDateTime.now());
        order.setUser(user);
        ArrayList<OrderPiece> orderPieces = new ArrayList<>();
        OrderPiece piece1 = new OrderPiece();
        piece1.setFood(testEntityManager.getEntityManager().getReference(Food.class, 1L));
        piece1.setQuantity(2);
        orderPieces.add(piece1);
        order.setOrderPieces(orderPieces);
        testEntityManager.persist(order);

        testEntityManager.flush();

        Order orderToVerifyValues = testEntityManager.find(Order.class, 1L);

        OrderPiece pieceToVerifyValues = orderToVerifyValues.getOrderPieces().get(0);
        User userToVerifyValues = order.getUser();
        Food foodToVerifyValues = pieceToVerifyValues.getFood();
        Category categoryToVerifyValues = foodToVerifyValues.getCategory();

        assertThat(orderToVerifyValues.getOrderStatus()).isEqualTo(OrderStatus.NEW);
        assertThat(pieceToVerifyValues.getQuantity()).isEqualTo(2);
        assertThat(userToVerifyValues.getName()).isEqualTo("testname");
        assertThat(foodToVerifyValues.getTitle()).isEqualTo("testtitle");
        assertThat(foodToVerifyValues.getPriceLabels().get(0).getValue()).isEqualTo(100.34);
        assertThat(foodToVerifyValues.getPriceLabels().get(0).getPriceCategory().getTitle()).isEqualTo("testPriceCategory");
        assertThat(categoryToVerifyValues.getName()).isEqualTo("testcategory");
    }

    @Test
    @FlywayTest
    public void orderCreateAddPieceIncreaseQuantityDecreaseQuantityTests() throws Exception {
        Long id = orderService.addPieceToOrder("testmachineid", 1L);

        Order order = orderRepository.getNewOrderByTelegramIdOrCreate("testmachineid");
        assertThat(id).isEqualTo(1L);
        assertThat(order).isNotNull();
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.NEW);
        assertThat(order.getUser().getMachineId()).isEqualTo("testmachineid");
        assertThat(order.getOrderPieces().get(0).getFood().getTitle()).isEqualTo("testtitle");
        assertThat(order.getOrderPieces().get(0).getQuantity()).isEqualTo(1);

        orderService.increaseOrderPieceQuantityByOne(id);
        assertThat(order.getOrderPieces().get(0).getQuantity()).isEqualTo(2);

        orderService.decreaseOrderPieceQuantityByOne(id);
        assertThat(order.getOrderPieces().get(0).getQuantity()).isEqualTo(1);

        orderService.decreaseOrderPieceQuantityByOne(id);
        assertThat(order.getOrderPieces().get(0).getQuantity()).isEqualTo(1);
    }
}
