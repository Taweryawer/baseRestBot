package taweryawer.repository;

import taweryawer.entities.Order;
import taweryawer.entities.OrderPiece;

import java.util.List;

public interface OrderRepository {
    public Order getNewOrderByTelegramIdOrCreate(String telegramId);
    public void addOrderPieceToOrder(Long orderId, OrderPiece piece);

    public Order getOrderById(Long orderId);

    List<Order> getOrdersOrderedByWaitingWithLimit(Integer lowerBound, Integer higherBound);
}
