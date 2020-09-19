package taweryawer.statemachine.actions;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import taweryawer.statemachine.UserEvent;
import taweryawer.statemachine.UserState;

@Component
public class PersistAddressAction implements Action<UserState, UserEvent> {

    @Override
    public void execute(StateContext<UserState, UserEvent> context) {

    }
}
