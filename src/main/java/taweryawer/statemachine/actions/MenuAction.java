package taweryawer.statemachine.actions;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import taweryawer.statemachine.UserEvent;
import taweryawer.statemachine.UserState;

public class MenuAction implements Action<UserState, UserEvent> {

    @Override
    public void execute(StateContext<UserState, UserEvent> context) {

    }
}
