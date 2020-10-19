package taweryawer.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import taweryawer.entities.Food;
import taweryawer.entities.Order;
import taweryawer.entities.OrderPiece;
import taweryawer.repository.FoodRepository;
import taweryawer.repository.OrderPieceRepository;
import taweryawer.repository.OrderRepository;
import taweryawer.service.OrderService;

import javax.transaction.Transactional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderPieceRepository orderPieceRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private FoodRepository foodRepository;

    private Log log = LogFactory.getLog(OrderServiceImpl.class);

    @Override
    public Integer increaseOrderPieceQuantityByOne(Long pieceId) {
        OrderPiece orderPiece = orderPieceRepository.getOrderPieceByid(pieceId);
        orderPiece.setQuantity(orderPiece.getQuantity() + 1);
        return orderPiece.getQuantity();
    }

    @Override
    public Integer decreaseOrderPieceQuantityByOne(Long pieceId) {
        OrderPiece orderPiece = orderPieceRepository.getOrderPieceByid(pieceId);
        if (orderPiece.getQuantity() > 1) {
            orderPiece.setQuantity(orderPiece.getQuantity() - 1);
        }
        return orderPiece.getQuantity();
    }

    @Override
    public Long addPieceToOrder(String telegramId, Long foodId) {
        log.info("Adding " + foodId + " in order for user " + telegramId);
        Order order = orderRepository.getNewOrderByTelegramIdOrCreate(telegramId);
        Food food = foodRepository.getFoodById(foodId);
        OrderPiece piece = new OrderPiece();
        piece.setFood(food);
        piece.setQuantity(1);
        piece.setOrder(order);
        orderRepository.addOrderPieceToOrder(order.getId(), piece);
        return piece.getId();
    }

    @Override
    public void removeOrderPieceFromOrder(Long pieceId) {
        OrderPiece piece = orderPieceRepository.getOrderPieceByid(pieceId);
        piece.getOrder().getOrderPieces().remove(piece);
    }
}
