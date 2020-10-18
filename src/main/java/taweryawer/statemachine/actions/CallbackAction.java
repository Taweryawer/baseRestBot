package taweryawer.statemachine.actions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import taweryawer.service.MessageBuilderBuilder;
import taweryawer.statemachine.UserEvent;
import taweryawer.statemachine.UserState;

@Component
public class CallbackAction implements Action<UserState, UserEvent> {

    @Autowired
    private MessageBuilderBuilder<UserEvent> messageBuilderBuilder;

    private Log log = LogFactory.getLog(CallbackAction.class);

    @Override
    public void execute(StateContext<UserState, UserEvent> context) {
        try {
            final Update update = (Update) context.getMessageHeader("update");
            log.debug(update.getCallbackQuery().getData());

            StateMachine<UserState, UserEvent> stateMachine = context.getStateMachine();
            messageBuilderBuilder.addHeader("update", update);
            String data = update.getCallbackQuery().getData();

            if (data.startsWith("add")) {
                stateMachine.sendEvent(messageBuilderBuilder.build(UserEvent.ADDORDERPIECE));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
