package taweryawer.statemachine.actions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerPreCheckoutQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import taweryawer.entities.Order;
import taweryawer.entities.enums.OrderStatus;
import taweryawer.repository.OrderRepository;
import taweryawer.statemachine.UserEvent;
import taweryawer.statemachine.UserState;

@Component
public class PreCheckoutQueryAction implements Action<UserState, UserEvent> {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TelegramLongPollingBot bot;

    private Log log = LogFactory.getLog(PreCheckoutQueryAction.class);

    @Override
    public void execute(StateContext<UserState, UserEvent> context) {
        try {
            final Update update = (Update) context.getMessageHeader("update");
            String orderId = update.getPreCheckoutQuery().getInvoicePayload().split(" ")[1];

            Order order = orderRepository.getOrderById(Long.valueOf(orderId));
            AnswerPreCheckoutQuery answerPreCheckoutQuery = new AnswerPreCheckoutQuery();
            answerPreCheckoutQuery.setPreCheckoutQueryId(update.getPreCheckoutQuery().getId());
            if (order.getOrderStatus().equals(OrderStatus.AWAITING_LIQPAY_PAYMENT)) {
                answerPreCheckoutQuery.setOk(true);
                log.info("precheckout query OK for order " + update.getPreCheckoutQuery().getInvoicePayload());
            } else {
                answerPreCheckoutQuery.setOk(false);
                answerPreCheckoutQuery.setErrorMessage("Запит на оплату замовлення відхилено. Можливо, замовлення вже оплачено або було скасовано.");
                log.info("precheckout query rejected for order " + update.getPreCheckoutQuery().getInvoicePayload());
            }
            bot.execute(answerPreCheckoutQuery);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
