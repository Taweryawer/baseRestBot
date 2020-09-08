package taweryawer.config;

import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import taweryawer.statemachine.UserEvent;
import taweryawer.statemachine.UserState;

import java.util.EnumSet;

public class StateMachineConfiguration extends EnumStateMachineConfigurerAdapter<UserState, UserEvent> {

    @Override
    public void configure(StateMachineConfigurationConfigurer<UserState, UserEvent> config) throws Exception {
        config
                .withConfiguration()
                    .autoStartup(false);
    }

    @Override
    public void configure(StateMachineStateConfigurer<UserState, UserEvent> states) throws Exception {
        states
                .withStates()
                .initial(UserState.NORMAL)
                .states(EnumSet.allOf(UserState.class));
    }
}
