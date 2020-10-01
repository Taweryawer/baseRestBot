package taweryawer.statemachine.actions;

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
public class MessageAction implements Action<UserState, UserEvent> {

    @Autowired
    private MessageBuilderBuilder<UserEvent> messageBuilderBuilder;

    @Override
    public void execute(StateContext<UserState, UserEvent> context) {
        final Update update = (Update) context.getMessageHeader("update");
        String telegramId = update.getMessage().getFrom().getId().toString();
        String text = update.getMessage().getText();
        messageBuilderBuilder.addHeader("update", update);
        StateMachine<UserState, UserEvent> currentStateMachine = context.getStateMachine();

        switch (text) {
            case "Профіль": {
                currentStateMachine.sendEvent(messageBuilderBuilder.build(UserEvent.PROFILE));
                break;
            }
            case "Меню": {
                currentStateMachine.sendEvent(messageBuilderBuilder.build(UserEvent.MENU));
                break;
            }
        }
    }
}
