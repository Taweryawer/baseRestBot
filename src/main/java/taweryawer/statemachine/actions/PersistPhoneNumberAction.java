package taweryawer.statemachine.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import taweryawer.RestBot;
import taweryawer.service.UserService;
import taweryawer.statemachine.UserEvent;
import taweryawer.statemachine.UserState;
import taweryawer.statemachine.WrongPhoneNumberFormat;

@Component
public class PersistPhoneNumberAction implements Action<UserState, UserEvent> {

    @Autowired
    private RestBot bot;

    @Autowired
    private UserService userService;

    @Value("${message.address}")
    private String addressMessage;

    @Value("${message.wrongNumberFormat}")
    private String wrongNumberMessage;

    @Override
    public void execute(StateContext<UserState, UserEvent> context) {
        try {
            final Update update = (Update) context.getMessageHeader("update");
            String telegramId = update.getMessage().getFrom().getId().toString();
            String number = update.getMessage().getText();
            if (number.matches("(\\+380\\d{9})|(0{1}\\d{9})")) {
                userService.changeUserPhoneNumber(telegramId, number);
                bot.execute(new SendMessage(telegramId, addressMessage));
            } else  {
                bot.execute(new SendMessage(telegramId, wrongNumberMessage));
                throw new WrongPhoneNumberFormat();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
