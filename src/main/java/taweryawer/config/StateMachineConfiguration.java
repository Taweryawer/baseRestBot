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
import taweryawer.service.ActionFactory;
import taweryawer.statemachine.UserEvent;
import taweryawer.statemachine.UserState;

import java.util.EnumSet;

@Configuration
@EnableStateMachineFactory
public class StateMachineConfiguration extends EnumStateMachineConfigurerAdapter<UserState, UserEvent> {

    @Autowired
    private StateMachineRuntimePersister<UserState, UserEvent, String> stateMachineRuntimePersister;

    @Autowired
    private ActionFactory actionFactory;

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
                .source(UserState.NORMAL).target(UserState.TYPING_NAME).event(UserEvent.START).action(actionFactory.startAction(), actionFactory.errorAction())
                .and()
                .withExternal()
                .source(UserState.TYPING_NAME).target(UserState.TYPING_NUMBER).event(UserEvent.MESSAGE).action(actionFactory.persistNameAction(), actionFactory.errorAction())
                .and()
                .withExternal()
                .source(UserState.TYPING_NUMBER).target(UserState.TYPING_ADDRESS).event(UserEvent.MESSAGE).action(actionFactory.persistPhoneNumberAction(), actionFactory.errorAction())
                .and()
                .withExternal()
                .source(UserState.TYPING_ADDRESS).target(UserState.NORMAL).event(UserEvent.MESSAGE).action(actionFactory.persistAddressAction(), actionFactory.errorAction())
                .and()
                .withInternal()
                .source(UserState.NORMAL).event(UserEvent.MESSAGE).action(actionFactory.messageAction(), actionFactory.errorAction())
                .and()
                .withInternal()
                .source(UserState.NORMAL).event(UserEvent.PROFILE).action(actionFactory.profileAction(), actionFactory.errorAction())
                .and()
                .withInternal()
                .source(UserState.NORMAL).event(UserEvent.MENU).action(actionFactory.menuAction(), actionFactory.errorAction())
                .and()
                .withInternal()
                .source(UserState.NORMAL).event(UserEvent.INLINE).action(actionFactory.inlineQueryAction(), actionFactory.errorAction())
                .and()
                .withInternal()
                .source(UserState.NORMAL).event(UserEvent.CALLBACK).action(actionFactory.callbackAction(), actionFactory.errorAction())
                .and()
                .withInternal()
                .source(UserState.NORMAL).event(UserEvent.ADDORDERPIECE).action(actionFactory.addAction(), actionFactory.errorAction())
                .and()
                .withInternal()
                .source(UserState.NORMAL).event(UserEvent.CHANGINGQUANTITY).action(actionFactory.changingQuantityAction(), actionFactory.errorAction())
                .and()
                .withInternal()
                .source(UserState.NORMAL).event(UserEvent.REMOVINGPIECE).action(actionFactory.removePieceAction(), actionFactory.errorAction())
                .and()
                .withInternal()
                .source(UserState.NORMAL).event(UserEvent.SHOWBASKET).action(actionFactory.showBasketAction(), actionFactory.errorAction())
                .and()
                .withInternal()
                .source(UserState.NORMAL).event(UserEvent.PROCEEDTOCONFIRMATION).action(actionFactory.proceedToConfirmationAction(), actionFactory.errorAction())
                .and()
                .withInternal()
                .source(UserState.NORMAL).event(UserEvent.CONFIRMCASHORDER).action(actionFactory.cashPaymentAction(), actionFactory.errorAction())
                .and()
                .withInternal()
                .source(UserState.NORMAL).event(UserEvent.CONFIRMLIQPAYORDER).action(actionFactory.liqpayPaymentAction(), actionFactory.errorAction())
                .and()
                .withInternal()
                .source(UserState.NORMAL).event(UserEvent.PRECHECKOUTQUERY).action(actionFactory.preCheckoutQueryAction(), actionFactory.errorAction())
                .and()
                .withInternal()
                .source(UserState.NORMAL).event(UserEvent.SUCCESSFULPAYMENT).action(actionFactory.successfulPaymentAction(), actionFactory.errorAction())
        ;
    }

    @Bean
    public StateMachineRuntimePersister<UserState, UserEvent, String> stateMachineRuntimePersister(JpaStateMachineRepository jpaStateMachineRepository) {
        return new JpaPersistingStateMachineInterceptor<>(jpaStateMachineRepository);
    }
}
