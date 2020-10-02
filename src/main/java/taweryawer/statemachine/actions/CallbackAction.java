package taweryawer.statemachine.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Action;
import org.telegram.telegrambots.meta.api.objects.Update;
import taweryawer.service.MessageBuilderBuilder;
import taweryawer.statemachine.UserEvent;
import taweryawer.statemachine.UserState;

public class CallbackAction implements Action<UserState, UserEvent> {

    @Autowired
    private MessageBuilderBuilder<UserEvent> messageBuilderBuilder;

    @Override
    public void execute(StateContext<UserState, UserEvent> context) {
        try {
            final Update update = (Update) context.getMessageHeader("update");
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
