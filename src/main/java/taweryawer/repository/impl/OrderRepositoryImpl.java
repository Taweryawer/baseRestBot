package taweryawer.repository.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import taweryawer.entities.Order;
import taweryawer.entities.OrderPiece;
import taweryawer.entities.enums.OrderStatus;
import taweryawer.repository.OrderRepository;
import taweryawer.repository.UserRepository;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    private Log log = LogFactory.getLog(OrderRepositoryImpl.class);

    @Override
    public Order getNewOrderByTelegramIdOrCreate(String telegramId) {
        log.info("Searching for new orders for user " + telegramId);
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> root = cq.from(Order.class);
        cq.select(root).where(cb.and(cb.equal(root.get("user").get("machineId"), telegramId), cb.equal(root.get("orderStatus"), OrderStatus.NEW)));
        TypedQuery<Order> query = entityManager.createQuery(cq);
        try {
            log.info("New order for user " + telegramId + " found.");
            return query.getSingleResult();
        } catch (NoResultException e) {
            log.info("Order for user " + telegramId + " not found. Creating a new one");
            return createNewOrderForUser(telegramId);
        } catch (NonUniqueResultException e) {
            log.error("Two NEW orders for telegramId " + telegramId + " NPE will most likely occur now");
            return null;
        }
    }

    @Override
    public void addOrderPieceToOrder(Long orderId, OrderPiece piece) {
        Order order = entityManager.find(Order.class, orderId);
        order.getOrderPieces().removeIf(orderPiece -> orderPiece.getFood().getId().equals(piece.getFood().getId()));
        piece.setOrder(order);
        order.getOrderPieces().add(piece);
        entityManager.persist(order);
    }

    private Order createNewOrderForUser(String telegramId) {
        try {
            Order order = new Order();
            order.setUser(userRepository.getUserByTelegramId(telegramId));
            order.setOrderStatus(OrderStatus.NEW);
            order.setOrderPieces(new ArrayList<>());
            entityManager.persist(order);
            log.info("Order " + order.getId() + " for user " + telegramId + " created.");
            return order;
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }
}
