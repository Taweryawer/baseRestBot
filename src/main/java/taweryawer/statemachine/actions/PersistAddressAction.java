package taweryawer.statemachine.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import taweryawer.service.UserService;
import taweryawer.statemachine.UserEvent;
import taweryawer.statemachine.UserState;

@Component
public class PersistAddressAction implements Action<UserState, UserEvent> {

    @Autowired
    private TelegramLongPollingBot bot;

    @Autowired
    private UserService userService;

    @Value("${message.profileSuccessful}")
    private String profileSuccessfulMessage;

    @Autowired
    @Qualifier("main")
    private ReplyKeyboardMarkup mainKeyboard;

    @Override
    public void execute(StateContext<UserState, UserEvent> context) {
        try {
            final Update update = (Update) context.getMessageHeader("update");
            String telegramId = update.getMessage().getFrom().getId().toString();
            userService.changeUserAddress(telegramId, update.getMessage().getText());
            SendMessage message = new SendMessage(telegramId, profileSuccessfulMessage);
            message.setReplyMarkup(mainKeyboard);
            bot.execute(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
