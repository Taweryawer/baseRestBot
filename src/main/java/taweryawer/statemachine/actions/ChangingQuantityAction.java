package taweryawer.statemachine.actions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import taweryawer.service.InlineKeyboardBuilder;
import taweryawer.service.OrderService;
import taweryawer.statemachine.UserEvent;
import taweryawer.statemachine.UserState;

@Component
public class ChangingQuantityAction implements Action<UserState, UserEvent> {

    @Autowired
    private OrderService orderService;

    @Autowired
    private TelegramLongPollingBot bot;

    private Log log = LogFactory.getLog(ChangingQuantityAction.class);

    @Autowired
    private InlineKeyboardBuilder builder;

    @Override
    public void execute(StateContext<UserState, UserEvent> context) {
        try {
            final Update update = (Update) context.getMessageHeader("update");
            Long pieceId = Long.parseLong(update.getCallbackQuery().getData().split(" ")[1]);
            String inlineMessageId = update.getCallbackQuery().getInlineMessageId();
            String action = update.getCallbackQuery().getData().split(" ")[0];

            Integer newQuantity = 0;
            switch (action) {
                case "minus": {
                    newQuantity = orderService.decreaseOrderPieceQuantityByOne(pieceId);
                    break;
                }
                case "plus": {
                    newQuantity = orderService.increaseOrderPieceQuantityByOne(pieceId);
                    break;
                }
                default: {
                    log.warn("Unknown action " + action);
                }
            }


            EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup();
            editMessageReplyMarkup.setInlineMessageId(inlineMessageId);
            editMessageReplyMarkup.setReplyMarkup(builder
                    .addButton("➖", "minus " + pieceId)
                    .addButton(newQuantity + " шт.", "stub")
                    .addButton("➕", "plus " + pieceId)
                    .finishRow()
                    .addButton("Перейти до оформлення замовлення✅", "proceed")
                    .finishRow()
                    .addButton("Меню\uD83D\uDCD5", "menu")
                    .addButton("Кошик\uD83D\uDED2", "basket")
                    .build()
            );

            bot.execute(editMessageReplyMarkup);

        } catch (TelegramApiException e) {
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
