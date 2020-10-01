package taweryawer.statemachine.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import taweryawer.service.FoodService;
import taweryawer.statemachine.UserEvent;
import taweryawer.statemachine.UserState;

@Component
public class InlineQueryAction implements Action<UserState, UserEvent> {

    @Autowired
    private FoodService foodService;

    @Autowired
    private TelegramLongPollingBot bot;

    @Override
    public void execute(StateContext<UserState, UserEvent> context) {
        final Update update = (Update) context.getMessageHeader("update");
        String telegramId = update.getInlineQuery().getFrom().getId().toString();
    }
}
