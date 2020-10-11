package taweryawer.repository;

import taweryawer.entities.Order;
import taweryawer.entities.OrderPiece;

public interface OrderRepository {
    public Order getNewOrderByTelegramIdOrCreate(String telegramId);
    public void addOrderPieceToOrder(Long orderId, OrderPiece piece);
}
