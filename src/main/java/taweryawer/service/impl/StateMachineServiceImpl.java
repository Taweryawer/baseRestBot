package taweryawer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.data.jpa.JpaRepositoryStateMachinePersist;
import taweryawer.service.StateMachineService;
import taweryawer.statemachine.UserEvent;
import taweryawer.statemachine.UserState;

public class StateMachineServiceImpl implements StateMachineService {

    private JpaRepositoryStateMachinePersist<UserState, UserEvent> jpaRepositoryStateMachinePersist;
    private StateMachineFactory<UserState, UserEvent> stateMachineFactory;


    @Autowired
    public StateMachineServiceImpl(JpaRepositoryStateMachinePersist<UserState, UserEvent> jpaRepositoryStateMachinePersist, StateMachineFactory<UserState, UserEvent> stateMachineFactory) {
        this.jpaRepositoryStateMachinePersist = jpaRepositoryStateMachinePersist;
        this.stateMachineFactory = stateMachineFactory;
    }

    @Override
    public StateMachine<UserState, UserEvent> getStateMachine(String userId) throws Exception {
        StateMachine<UserState, UserEvent> sm = createStateMachine(userId);
        if (sm == null) {
            return null;
        }
        StateMachineContext<UserState, UserEvent> smc = jpaRepositoryStateMachinePersist.read(userId);
        if (smc != null) {
            sm.getStateMachineAccessor()
                    .doWithAllRegions(sma -> sma.resetStateMachine(smc));
        }
        sm.start();
        return sm;
    }

    public StateMachine<UserState, UserEvent> createStateMachine(String userId) {
        if (userId != null) {
            return stateMachineFactory.getStateMachine(userId);
        } else return null;
    }
}
