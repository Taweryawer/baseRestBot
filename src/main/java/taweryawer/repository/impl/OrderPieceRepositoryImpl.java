package taweryawer.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import taweryawer.entities.OrderPiece;
import taweryawer.repository.OrderPieceRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class OrderPieceRepositoryImpl implements OrderPieceRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public OrderPiece getOrderPieceByid(Long id) {
        return entityManager.find(OrderPiece.class, id);
    }
}
