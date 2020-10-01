package taweryawer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultArticle;
import taweryawer.entities.Food;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class FoodInlineAnswerBuilder {

    @Autowired
    private FoodService foodService;

    private ArrayList<InlineQueryResult> results = new ArrayList<>();

    public void addFood(Food food) {
        InlineQueryResultArticle result = new InlineQueryResultArticle();
        result.setTitle(food.getTitle());
        result.setDescription(foodService.getDescriptionForFood(food));
        result.setThumbUrl(food.getPhotoURL());
        result.setId(UUID.randomUUID().toString());
        InputTextMessageContent content = new InputTextMessageContent();
        content.setMessageText(foodService.getContentDescriptionForFood(food));
        content.setParseMode(ParseMode.MARKDOWN);
        result.setInputMessageContent(content);
        results.add(result);
    }

    public AnswerInlineQuery build(String queryId) {
        AnswerInlineQuery answerInlineQuery = new AnswerInlineQuery();
        answerInlineQuery.setResults(results);
        answerInlineQuery.setInlineQueryId(queryId);
        answerInlineQuery.setCacheTime(0);
        results = new ArrayList<>();
        return answerInlineQuery;
    }
}
