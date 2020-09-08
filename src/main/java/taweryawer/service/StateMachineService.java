package taweryawer.service;

import org.springframework.statemachine.StateMachine;
import taweryawer.statemachine.UserEvent;
import taweryawer.statemachine.UserState;

public interface StateMachineService {
    StateMachine<UserState, UserEvent> getStateMachine(String userId) throws Exception;
}
