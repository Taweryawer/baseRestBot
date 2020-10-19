package taweryawer.service;

import taweryawer.entities.OrderPiece;

import java.util.List;

public interface OrderService {
    public Integer increaseOrderPieceQuantityByOne(Long pieceId);

    public Integer decreaseOrderPieceQuantityByOne(Long pieceId);

    public Long addPieceToOrder(String telegramId, Long foodId);

    public void removeOrderPieceFromOrder(Long pieceId);

    public List<OrderPiece> getOrderPiecesForOrder(String telegramId);
}
