package taweryawer.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import taweryawer.entities.Food;
import taweryawer.entities.Order;
import taweryawer.entities.OrderPiece;
import taweryawer.entities.enums.OrderStatus;
import taweryawer.entities.enums.PaymentMethod;
import taweryawer.mappers.OrderMapper;
import taweryawer.repository.FoodRepository;
import taweryawer.repository.OrderPieceRepository;
import taweryawer.repository.OrderRepository;
import taweryawer.service.OrderService;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderPieceRepository orderPieceRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private TelegramLongPollingBot bot;

    @Autowired
    private Environment environment;

    @Autowired
    private OrderMapper orderMapper;

    private Log log = LogFactory.getLog(OrderServiceImpl.class);

    @Override
    public Integer increaseOrderPieceQuantityByOne(Long pieceId) {
        OrderPiece orderPiece = orderPieceRepository.getOrderPieceByid(pieceId);
        if (orderPiece.getOrder().getOrderStatus().equals(OrderStatus.NEW)) {
            orderPiece.setQuantity(orderPiece.getQuantity() + 1);
        }
        return orderPiece.getQuantity();
    }

    @Override
    public Integer decreaseOrderPieceQuantityByOne(Long pieceId) {
        OrderPiece orderPiece = orderPieceRepository.getOrderPieceByid(pieceId);
        if (orderPiece.getQuantity() > 1 && orderPiece.getOrder().getOrderStatus().equals(OrderStatus.NEW)) {
            orderPiece.setQuantity(orderPiece.getQuantity() - 1);
        }
        return orderPiece.getQuantity();
    }

    @Override
    public Long addPieceToOrder(String telegramId, Long foodId) {
        log.info("Adding " + foodId + " in order for user " + telegramId);
        Order order = orderRepository.getNewOrderByTelegramIdOrCreate(telegramId);
        Food food = foodRepository.getFoodById(foodId);
        OrderPiece piece = new OrderPiece();
        piece.setFood(food);
        piece.setQuantity(1);
        piece.setOrder(order);
        orderRepository.addOrderPieceToOrder(order.getId(), piece);
        return piece.getId();
    }

    @Override
    public void removeOrderPieceFromOrder(Long pieceId) {
        OrderPiece piece = orderPieceRepository.getOrderPieceByid(pieceId);
        if (piece.getOrder().getOrderStatus().equals(OrderStatus.NEW)) {
            piece.getOrder().getOrderPieces().remove(piece);
        }
    }

    @Override
    public List<OrderPiece> getOrderPiecesForOrder(String telegramId) {
        return new ArrayList<>(orderRepository.getNewOrderByTelegramIdOrCreate(telegramId).getOrderPieces());
    }

    @Override
    public void confirmOrder(String telegramId, PaymentMethod paymentMethod, OrderStatus newOrderStatus) {
        Order order = orderRepository.getNewOrderByTelegramIdOrCreate(telegramId);
        order.setPaymentMethod(paymentMethod);
        order.setOrderStatus(newOrderStatus);
        order.setDateTime(LocalDateTime.now());
        if (newOrderStatus.equals(OrderStatus.WAITING)) {
            convertToDtoAndSendSTOMP(order);
        }
    }

    //TODO different price categories support
    @Override
    public String getOrderSummary(Long orderId) {
        Order order = orderRepository.getOrderById(orderId);
        StringBuilder sb = new StringBuilder("*Ваше замовлення:*\n\n");
        Double summary = 0.0;

        for (OrderPiece orderPiece : order.getOrderPieces()) {
            sb.append("\uD83D\uDD36 *" + orderPiece.getFood().getTitle() + "* x " + orderPiece.getQuantity() + " = ");
            sb.append(orderPiece.getFood().getPriceLabels().get(0).getValue() * orderPiece.getQuantity() + " грн\n");
            summary += orderPiece.getFood().getPriceLabels().get(0).getValue();
        }
        sb.append("\n*Сума:* " + getPriceSum(order.getId()) + " грн");

        return sb.toString();
    }

    //TODO different price categories support
    @Override
    public Double getPriceSum(Long orderId) {
        Order order = orderRepository.getOrderById(orderId);

        Double summary = 0.0;

        for (OrderPiece orderPiece : order.getOrderPieces()) {
            summary += orderPiece.getFood().getPriceLabels().get(0).getValue() * orderPiece.getQuantity();
        }

        return summary;
    }

    @Override
    public void confirmLiqpayPayment(Long orderId) {
        Order order = orderRepository.getOrderById(orderId);
        order.setOrderStatus(OrderStatus.CONFIRMED_LIQPAY_PAYMENT);
        order.setDateTime(LocalDateTime.now());
        convertToDtoAndSendSTOMP(order);
    }

    @Override
    public List<Order> getOrdersForPage(Integer page) {
        Integer lowerBound = (page - 1) * 20;
        Integer higherBound = lowerBound + 20;
        return orderRepository.getOrdersOrderedByWaitingWithLimit(lowerBound, higherBound);
    }

    @Override
    public Order getOrderById(Long orderId) {
        Order order = orderRepository.getOrderById(orderId);
        return order;
    }

    @Override
    public Long getOrdersCount() {
        return orderRepository.getOrdersCount();
    }

    @Override
    public void acceptOrder(Long orderId) {
        Order order = orderRepository.getOrderById(orderId);
        order.setOrderStatus(OrderStatus.CONFIRMED);
        String telegramId = order.getUser().getMachineId();
        try {
            log.info("Confirmed order " + order.getId());
            bot.execute(new SendMessage(telegramId, environment.getProperty("message.confirmed")));
        } catch (TelegramApiException e) {
            log.error(e);
        }
    }

    @Override
    public void rejectOrder(Long orderId) {
        Order order = orderRepository.getOrderById(orderId);
        order.setOrderStatus(OrderStatus.CANCELED);
        String telegramId = order.getUser().getMachineId();
        try {
            log.info("Rejecting order " + order.getId());
            bot.execute(new SendMessage(telegramId, environment.getProperty("message.rejected")));
        } catch (TelegramApiException e) {
            log.error(e);
        }
    }


    private void convertToDtoAndSendSTOMP(Order order) {
        simpMessagingTemplate.convertAndSend("/orderspage/orders", orderMapper.toDto(order));
    }

}
