package taweryawer.statemachine.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import taweryawer.service.InlineKeyboardBuilder;
import taweryawer.statemachine.UserEvent;
import taweryawer.statemachine.UserState;

import java.util.List;

@Component
public class ProceedToConfirmationAction implements Action<UserState, UserEvent> {

    @Autowired
    private TelegramLongPollingBot bot;

    @Autowired
    private InlineKeyboardBuilder inlineKeyboardBuilder;

    @Value("#{'${paymentconfig.methods}'.split(',')}")
    private List<String> paymentMethods;

    @Override
    public void execute(StateContext<UserState, UserEvent> context) {
        try {
            final Update update = (Update) context.getMessageHeader("update");
            String telegramId = update.getCallbackQuery().getFrom().getId().toString();

            SendMessage sm = new SendMessage(telegramId, "*Будь ласка, оберіть тип оплати.*");
            sm.setParseMode(ParseMode.MARKDOWN);

            if (paymentMethods.contains("cash")) {
                inlineKeyboardBuilder.addButton("Готівкою", "cash");
            }
            if (paymentMethods.contains("liqpay")) {
                inlineKeyboardBuilder.addButton("Картою (Liqpay)", "liqpay");
            }

            sm.setReplyMarkup(inlineKeyboardBuilder.build());
            bot.execute(sm);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
