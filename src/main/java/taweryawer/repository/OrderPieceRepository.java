package taweryawer.repository;

import org.springframework.stereotype.Repository;
import taweryawer.entities.OrderPiece;

public interface OrderPieceRepository {
    public OrderPiece getOrderPieceByid(Long id);
}
