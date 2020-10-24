package taweryawer.statemachine.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import taweryawer.entities.enums.OrderStatus;
import taweryawer.entities.enums.PaymentMethod;
import taweryawer.service.OrderService;
import taweryawer.statemachine.UserEvent;
import taweryawer.statemachine.UserState;

@Component
public class CashPaymentAction implements Action<UserState, UserEvent> {

    @Autowired
    private TelegramLongPollingBot bot;

    @Autowired
    private OrderService orderService;

    @Value("${message.cashconfirmed}")
    private String successfulMessage;

    @Override
    public void execute(StateContext<UserState, UserEvent> context) {
        try {
            final Update update = (Update) context.getMessageHeader("update");
            String telegramId = update.getCallbackQuery().getFrom().getId().toString();

            orderService.confirmOrder(telegramId, PaymentMethod.CASH, OrderStatus.WAITING);

            bot.execute(new DeleteMessage(telegramId, update.getCallbackQuery().getMessage().getMessageId()));

            bot.execute(new AnswerCallbackQuery()
                    .setCallbackQueryId(update.getCallbackQuery().getId()));
            bot.execute(new SendMessage(telegramId, successfulMessage));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
