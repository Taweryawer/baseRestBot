package taweryawer.statemachine.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Update;
import taweryawer.service.InlineKeyboardBuilder;
import taweryawer.service.OrderService;
import taweryawer.statemachine.UserEvent;
import taweryawer.statemachine.UserState;

@Component
public class AddAction implements Action<UserState, UserEvent> {

    @Autowired
    private TelegramLongPollingBot bot;

    @Autowired
    private OrderService orderService;

    @Autowired
    private InlineKeyboardBuilder builder;

    @Override
    public void execute(StateContext<UserState, UserEvent> context) {
        try {
            final Update update = (Update) context.getMessageHeader("update");
            String telegramId = update.getCallbackQuery().getFrom().getId().toString();
            String data = update.getCallbackQuery().getData().split(" ")[1];
            Long id = orderService.addPieceToOrder(telegramId, Long.parseLong(data));

            String inlineMessageId = update.getCallbackQuery().getInlineMessageId();
            EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup();
            editMessageReplyMarkup.setInlineMessageId(inlineMessageId);
            editMessageReplyMarkup.setReplyMarkup(builder
                    .addButton("➖", "minus " + id)
                    .addButton("1 шт.", "stub")
                    .addButton("➕", "plus " + id)
                    .finishRow()
                    .addButton("Перейти до оформлення замовлення✅", "proceed")
                    .finishRow()
                    .addButton("Меню\uD83D\uDCD5", "menu")
                    .addButton("Кошик\uD83D\uDED2", "basket")
                    .build()
            );
            bot.execute(editMessageReplyMarkup);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
