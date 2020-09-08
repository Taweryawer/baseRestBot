package taweryawer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import taweryawer.service.MessageBuilderBuilder;
import taweryawer.service.StateMachineService;
import taweryawer.service.UserService;
import taweryawer.statemachine.UserEvent;
import taweryawer.statemachine.UserState;

@Component
public class RestBot extends TelegramLongPollingBot {

    private StateMachineService stateMachineService;
    private Log log = LogFactory.getLog(RestBot.class);
    private UserService userService;
    private MessageBuilderBuilder<UserEvent> messageBuilderBuilder;

    @Autowired
    public RestBot(StateMachineService stateMachineService, UserService userService, MessageBuilderBuilder<UserEvent> messageBuilderBuilder) {
        this.stateMachineService = stateMachineService;
        this.userService = userService;
        this.messageBuilderBuilder = messageBuilderBuilder;
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.hasMessage()) {
                StateMachine<UserState, UserEvent> stateMachine = stateMachineService.getStateMachine(update.getMessage().getFrom().getId().toString());
                messageBuilderBuilder
                        .addHeader("bot", this)
                        .addHeader("update", update);
                if(update.getMessage().getText().equals("/start")) {
                    userService.getUserByTelegramId(update.getMessage().getFrom().getId().toString());
                    Message<UserEvent> message = messageBuilderBuilder.build(UserEvent.START);
                    stateMachine.sendEvent(message);
                } else {
                    stateMachine.sendEvent(messageBuilderBuilder.build(UserEvent.MESSAGE));
                }
            }
        } catch (Exception e) {
            log.error("Something went wrong", e);
        }

    }

    @Override
    public String getBotUsername() {
        return System.getenv("BOT_USERNAME");
    }

    @Override
    public String getBotToken() {
        return System.getenv("BOT_TOKEN");
    }
}
