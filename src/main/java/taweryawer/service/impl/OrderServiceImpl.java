package taweryawer.service.impl;

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

    @Override
    public void increaseOrderPieceQuantityByOne(Long pieceId) {
        OrderPiece orderPiece = orderPieceRepository.getOrderPieceByid(pieceId);
        orderPiece.setQuantity(orderPiece.getQuantity() + 1);
    }

    @Override
    public void decreaseOrderPieceQuantityByOne(Long pieceId) {
        OrderPiece orderPiece = orderPieceRepository.getOrderPieceByid(pieceId);
        if(orderPiece.getQuantity() > 1) {
            orderPiece.setQuantity(orderPiece.getQuantity() - 1);
        }
    }

    @Override
    public Long addPieceToOrder(String telegramId, Long foodId) {
        Order order = orderRepository.getNewOrderByTelegramIdOrCreate(telegramId);
        Food food = foodRepository.getFoodById(foodId);
        OrderPiece piece = new OrderPiece();
        piece.setFood(food);
        piece.setQuantity(1);
        piece.setOrder(order);
        orderRepository.addOrderPieceToOrder(order.getId(), piece);
        return piece.getId();
    }
}
