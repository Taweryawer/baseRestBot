package taweryawer.service;

import taweryawer.entities.OrderPiece;
import taweryawer.entities.enums.OrderStatus;
import taweryawer.entities.enums.PaymentMethod;

import java.util.List;

public interface OrderService {
    public Integer increaseOrderPieceQuantityByOne(Long pieceId);

    public Integer decreaseOrderPieceQuantityByOne(Long pieceId);

    public Long addPieceToOrder(String telegramId, Long foodId);

    public void removeOrderPieceFromOrder(Long pieceId);

    public List<OrderPiece> getOrderPiecesForOrder(String telegramId);

    void confirmOrder(String telegramId, PaymentMethod paymentMethod, OrderStatus newOrderStatus);

    String getOrderSummary(Long orderId);

    Double getPriceSum(Long orderId);
}
