package taweryawer.service;

import taweryawer.entities.OrderPiece;

public interface OrderService {
    public void increaseOrderPieceQuantityByOne(Long pieceId);
    public void decreaseOrderPieceQuantityByOne(Long pieceId);
    public Long addPieceToOrder(String telegramId, Long foodId);
}
