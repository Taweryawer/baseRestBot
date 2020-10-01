package taweryawer.statemachine.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.telegram.telegrambots.meta.api.objects.Update;
import taweryawer.statemachine.UserEvent;
import taweryawer.statemachine.UserState;

public class MenuAction implements Action<UserState, UserEvent> {


    @Override
    public void execute(StateContext<UserState, UserEvent> context) {
        final Update update = (Update) context.getMessageHeader("update");
    }
}
