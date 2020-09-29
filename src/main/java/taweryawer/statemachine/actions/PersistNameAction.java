package taweryawer.statemachine.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import taweryawer.RestBot;
import taweryawer.service.UserService;
import taweryawer.statemachine.UserEvent;
import taweryawer.statemachine.UserState;

@Component
public class PersistNameAction implements Action<UserState, UserEvent> {

    @Autowired
    private RestBot bot;

    @Autowired
    private UserService userService;

    @Value("${message.number}")
    private String numberMessage;

    @Override
    public void execute(StateContext<UserState, UserEvent> context) {
        try {
            final Update update = (Update) context.getMessageHeader("update");
            String telegramId = update.getMessage().getFrom().getId().toString();
            userService.changeUserName(telegramId, update.getMessage().getText());
            bot.execute(new SendMessage(telegramId, numberMessage));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
