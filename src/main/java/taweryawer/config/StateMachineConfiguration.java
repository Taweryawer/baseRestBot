package taweryawer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.data.jpa.JpaPersistingStateMachineInterceptor;
import org.springframework.statemachine.data.jpa.JpaStateMachineRepository;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;
import taweryawer.statemachine.UserEvent;
import taweryawer.statemachine.UserState;

import java.util.EnumSet;

@Configuration
@EnableStateMachineFactory
public class StateMachineConfiguration extends EnumStateMachineConfigurerAdapter<UserState, UserEvent> {

    @Autowired
    private StateMachineRuntimePersister<UserState, UserEvent, String> stateMachineRuntimePersister;

    @Override
    public void configure(StateMachineConfigurationConfigurer<UserState, UserEvent> config) throws Exception {
        config
                .withConfiguration()
                    .autoStartup(false)
                .and()
                    .withPersistence()
                        .runtimePersister(stateMachineRuntimePersister);
    }

    @Override
    public void configure(StateMachineStateConfigurer<UserState, UserEvent> states) throws Exception {
        states
                .withStates()
                .initial(UserState.NORMAL)
                .states(EnumSet.allOf(UserState.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<UserState, UserEvent> transitions) throws Exception {
        transitions
                .withExternal()
                    .source(UserState.NORMAL).target(UserState.TYPING_NAME).event(UserEvent.START);
    }

    @Bean
    public StateMachineRuntimePersister<UserState, UserEvent, String> stateMachineRuntimePersister(JpaStateMachineRepository jpaStateMachineRepository) {
        return new JpaPersistingStateMachineInterceptor<>(jpaStateMachineRepository);
    }
}
