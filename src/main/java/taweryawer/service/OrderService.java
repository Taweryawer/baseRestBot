package taweryawer.service;

public interface OrderService {
    public Integer increaseOrderPieceQuantityByOne(Long pieceId);

    public Integer decreaseOrderPieceQuantityByOne(Long pieceId);

    public Long addPieceToOrder(String telegramId, Long foodId);

    public void removeOrderPieceFromOrder(Long pieceId);
}
