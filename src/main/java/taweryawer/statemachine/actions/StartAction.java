package taweryawer.statemachine.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import taweryawer.statemachine.UserEvent;
import taweryawer.statemachine.UserState;

@Component
public class StartAction implements Action<UserState, UserEvent> {

    @Autowired
    private TelegramLongPollingBot bot;

    @Value("${message.start}")
    private String startMessage;

    @Override
    public void execute(StateContext<UserState, UserEvent> context) {
        try {
            final Update update = (Update) context.getMessageHeader("update");
            String telegramId = update.getMessage().getFrom().getId().toString();
            bot.execute(new SendMessage(telegramId, startMessage));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
