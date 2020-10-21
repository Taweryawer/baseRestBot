package taweryawer.statemachine.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import taweryawer.service.OrderService;
import taweryawer.statemachine.UserEvent;
import taweryawer.statemachine.UserState;

@Component
public class SuccessfulPaymentAction implements Action<UserState, UserEvent> {

    @Autowired
    private TelegramLongPollingBot bot;

    @Autowired
    private OrderService orderService;

    @Override
    public void execute(StateContext<UserState, UserEvent> context) {
        try {
            final Update update = (Update) context.getMessageHeader("update");
            String orderId = update.getMessage().getSuccessfulPayment().getInvoicePayload().split(" ")[1];
            String telegramId = update.getMessage().getFrom().getId().toString();

            orderService.confirmLiqpayPayment(Long.valueOf(orderId));

            bot.execute(new SendMessage(telegramId, "Оплату замовлення №" + orderId + " за допомогою Liqpay підтверджено✅"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
