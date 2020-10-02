import org.flywaydb.core.Flyway;
import org.flywaydb.test.annotation.FlywayTest;
import org.flywaydb.test.junit5.FlywayTestExtension;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import taweryawer.Main;
import taweryawer.config.TestBeansConfig;
import taweryawer.entities.*;
import taweryawer.entities.enums.OrderStatus;
import taweryawer.repository.UserRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.*;

@ContextConfiguration(classes = {TestBeansConfig.class})
@DataJpaTest
@ExtendWith({SpringExtension.class})
@ExtendWith({FlywayTestExtension.class})
@EnableAutoConfiguration
@EntityScan("taweryawer")
public class SomeDBTests {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("getUser")
    public void getUser() throws Exception {
        User user = userRepository.getUserByTelegramId("test");
        assertThat(user.getMachineId()).isEqualTo("test");
    }

    @Test
    @FlywayTest
    public void CreateFoodWithCategoryAndUserThenSaveOrderAndOrderPieceThenFindAndCheck() {
        Category category = new Category();
        category.setName("testcategory");
        testEntityManager.persist(category);
        Category category1 = testEntityManager.find(Category.class, 1L);
        assertThat(category1).isNotNull();

        Food food = new Food();
        food.setCategory(testEntityManager.getEntityManager().getReference(Category.class, 1L));
        food.setPriceKharkiv(100);
        food.setTitle("testtitle");
        testEntityManager.persist(food);

        User user = new User();
        user.setName("testname");
        user.setMachineId("testmachineid");
        testEntityManager.persist(user);

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


        Order orderToVerifyValues = testEntityManager.find(Order.class, 1L);

        OrderPiece pieceToVerifyValues = orderToVerifyValues.getOrderPieces().get(0);
        User userToVerifyValues = order.getUser();
        Food foodToVerifyValues = pieceToVerifyValues.getFood();
        Category categoryToVerifyValues = foodToVerifyValues.getCategory();

        assertThat(orderToVerifyValues.getOrderStatus()).isEqualTo(OrderStatus.NEW);
        assertThat(pieceToVerifyValues.getQuantity()).isEqualTo(2);
        assertThat(userToVerifyValues.getName()).isEqualTo("testname");
        assertThat(foodToVerifyValues.getTitle()).isEqualTo("testtitile");
        assertThat(foodToVerifyValues.getPriceKharkiv()).isEqualTo(100);
        assertThat(categoryToVerifyValues.getName()).isEqualTo("testcategory");
    }
}
