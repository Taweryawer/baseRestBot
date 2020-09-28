package taweryawer.statemachine.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import taweryawer.entities.User;
import taweryawer.service.UserService;
import taweryawer.statemachine.UserEvent;
import taweryawer.statemachine.UserState;

@Component
public class ProfileAction implements Action<UserState, UserEvent> {

    @Autowired
    private TelegramLongPollingBot bot;

    @Autowired
    private UserService userService;

    @Override
    public void execute(StateContext<UserState, UserEvent> context) {
        try {
            final Update update = (Update) context.getMessageHeader("update");
            String telegramId = update.getMessage().getFrom().getId().toString();
            User user = userService.getUserByTelegramId(telegramId);
            StringBuilder sb = new StringBuilder("Ваш профіль,\n" + update.getMessage().getFrom().getFirstName() + "\n\n");
            sb.append("*Ім'я:* " + user.getName() + "\n");
            sb.append("*Номер:* " + user.getPhoneNumber() + "\n");
            sb.append("*Адрес:* " + user.getAddress() + "\n");
            sb.append("*Ваші замовлення:* /u" + user.getId());
            SendMessage sm = new SendMessage(telegramId, sb.toString());
            sm.setParseMode(ParseMode.MARKDOWN);
            bot.execute(sm);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
