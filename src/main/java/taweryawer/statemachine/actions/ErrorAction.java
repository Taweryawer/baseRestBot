package taweryawer.statemachine.actions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import taweryawer.RestBot;
import taweryawer.statemachine.UserEvent;
import taweryawer.statemachine.UserState;

@Component
@Scope("prototype")
public class ErrorAction implements Action<UserState, UserEvent> {

    @Autowired
    private RestBot bot;

    private Log log = LogFactory.getLog(ErrorAction.class);
    @Value("${message.error}")
    private String errorMesssage;

    @Override
    public void execute(StateContext<UserState, UserEvent> context) {
        try {
            final Update update = (Update) context.getMessageHeader("update");
            log.error("Ooops! Something went wrong with statemachine " + context.getStateMachine().getId(), context.getException());
            bot.execute(new SendMessage(context.getStateMachine().getId(), errorMesssage));
        } catch (Exception e) {
            log.error("Something went so terribly wrong that you got an exception in an exception handler LOL!", e);
        }
    }
}
