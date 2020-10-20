package taweryawer.statemachine.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import taweryawer.service.CategoryService;
import taweryawer.statemachine.UserEvent;
import taweryawer.statemachine.UserState;

@Component
public class MenuAction implements Action<UserState, UserEvent> {

    @Autowired
    private CategoryService categoryService;

    @Value("${message.menu}")
    private String menuMsg;

    @Autowired
    private TelegramLongPollingBot bot;

    @Override
    public void execute(StateContext<UserState, UserEvent> context) {
        try {
            final Update update = (Update) context.getMessageHeader("update");
            String telegramId;
            if (update.hasCallbackQuery()) {
                telegramId = update.getCallbackQuery().getFrom().getId().toString();
                bot.execute(new AnswerCallbackQuery()
                        .setCallbackQueryId(update.getCallbackQuery().getId()));
            } else {
                telegramId = update.getMessage().getFrom().getId().toString();
            }
            SendMessage message = new SendMessage(telegramId, menuMsg);
            message.setReplyMarkup(categoryService.getCategoriesKeyboard());
            bot.execute(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
