package taweryawer.statemachine.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendInvoice;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.payments.LabeledPrice;
import taweryawer.entities.Order;
import taweryawer.entities.enums.OrderStatus;
import taweryawer.entities.enums.PaymentMethod;
import taweryawer.repository.OrderRepository;
import taweryawer.service.OrderService;
import taweryawer.statemachine.UserEvent;
import taweryawer.statemachine.UserState;

import java.util.ArrayList;

@Component
public class LiqpayPaymentAction implements Action<UserState, UserEvent> {

    @Autowired
    private TelegramLongPollingBot bot;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @Value("${LIQPAY_TOKEN}")
    private String providerToken;

    @Override
    public void execute(StateContext<UserState, UserEvent> context) {
        try {
            final Update update = (Update) context.getMessageHeader("update");
            String telegramId = update.getCallbackQuery().getFrom().getId().toString();

            final Order order = orderRepository.getNewOrderByTelegramIdOrCreate(telegramId);
            SendInvoice invoice = new SendInvoice();
            invoice.setChatId(Integer.valueOf(telegramId));
            invoice.setTitle("Замовлення №" + order.getId());
            invoice.setDescription(orderService.getOrderSummary(order.getId()).replace('*', ' '));
            invoice.setPayload("liqpay " + order.getId());
            invoice.setProviderToken(providerToken);
            invoice.setStartParameter("liqpay" + order.getId());
            invoice.setCurrency("UAH");
            ArrayList<LabeledPrice> prices = new ArrayList<>();
            prices.add(new LabeledPrice("Сума ", (int) (orderService.getPriceSum(order.getId()) * (100))));
            invoice.setPrices(prices);

            bot.execute(new AnswerCallbackQuery().setCallbackQueryId(update.getCallbackQuery().getId()));
            bot.execute(invoice);
            orderService.confirmOrder(telegramId, PaymentMethod.LIQPAY, OrderStatus.AWAITING_LIQPAY_PAYMENT);
            bot.execute(new DeleteMessage(telegramId, update.getCallbackQuery().getMessage().getMessageId()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
