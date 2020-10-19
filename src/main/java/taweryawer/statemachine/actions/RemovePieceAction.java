package taweryawer.statemachine.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import taweryawer.service.OrderService;
import taweryawer.statemachine.UserEvent;
import taweryawer.statemachine.UserState;

@Component
public class RemovePieceAction implements Action<UserState, UserEvent> {

    @Autowired
    private TelegramLongPollingBot bot;

    @Autowired
    private OrderService orderService;

    @Value("${message.removed}")
    private String pieceRemovedMessage;

    @Override
    public void execute(StateContext<UserState, UserEvent> context) {
        try {
            final Update update = (Update) context.getMessageHeader("update");
            String telegramId = update.getCallbackQuery().getId();
            Long pieceId = Long.valueOf(update.getCallbackQuery().getData().split(" ")[1]);

            orderService.removeOrderPieceFromOrder(pieceId);
            bot.execute(new DeleteMessage(telegramId, update.getCallbackQuery().getMessage().getMessageId()));

            AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
            answerCallbackQuery.setCallbackQueryId(update.getCallbackQuery().getId());
            answerCallbackQuery.setText(pieceRemovedMessage);
            bot.execute(answerCallbackQuery);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
