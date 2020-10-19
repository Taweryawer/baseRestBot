package taweryawer.statemachine.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import taweryawer.entities.OrderPiece;
import taweryawer.service.FoodService;
import taweryawer.service.InlineKeyboardBuilder;
import taweryawer.service.OrderService;
import taweryawer.statemachine.UserEvent;
import taweryawer.statemachine.UserState;

@Component
public class ShowBasketAction implements Action<UserState, UserEvent> {

    @Autowired
    private FoodService foodService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private TelegramLongPollingBot bot;

    @Autowired
    private InlineKeyboardBuilder builder;

    @Override
    public void execute(StateContext<UserState, UserEvent> context) {
        try {
            final Update update = (Update) context.getMessageHeader("update");
            String telegramId;
            if (update.hasCallbackQuery()) {
                telegramId = update.getCallbackQuery().getFrom().getId().toString();
            } else {
                telegramId = update.getMessage().getFrom().getId().toString();
            }

            for (OrderPiece orderPiece : orderService.getOrderPiecesForOrder(telegramId)) {
                SendMessage message = new SendMessage(telegramId, foodService.getContentDescriptionForFood(orderPiece.getFood()));
                message.setReplyMarkup(builder
                        .addButton("➖", "minus " + orderPiece.getId())
                        .addButton(orderPiece.getQuantity() + " шт.", "stub")
                        .addButton("➕", "plus " + orderPiece.getId())
                        .finishRow()
                        .addButton("Видалити з кошика❌", "remove " + orderPiece.getId())
                        .finishRow()
                        .addButton("Перейти до оформлення замовлення✅", "proceed")
                        .finishRow()
                        .addButton("Меню\uD83D\uDCD5", "menu")
                        .addButton("Кошик\uD83D\uDED2", "basket")
                        .build());
                message.setParseMode(ParseMode.MARKDOWN);
                bot.execute(message);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
