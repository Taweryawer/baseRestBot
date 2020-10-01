package taweryawer.statemachine.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import taweryawer.entities.Food;
import taweryawer.service.FoodInlineAnswerBuilder;
import taweryawer.service.FoodService;
import taweryawer.statemachine.UserEvent;
import taweryawer.statemachine.UserState;

@Component
public class InlineQueryAction implements Action<UserState, UserEvent> {

    @Autowired
    private FoodService foodService;

    @Autowired
    private TelegramLongPollingBot bot;

    @Autowired
    private FoodInlineAnswerBuilder foodInlineAnswerBuilder;

    @Override
    public void execute(StateContext<UserState, UserEvent> context) {
        try {
            final Update update = (Update) context.getMessageHeader("update");
            String data = update.getInlineQuery().getQuery();
            String queryId = update.getInlineQuery().getId();
            for (Food food : foodService.getAllFoodByCategory(data)) {
                foodInlineAnswerBuilder.addFood(food);
            }
            bot.execute(foodInlineAnswerBuilder.build(queryId));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
