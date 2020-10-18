package taweryawer.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Service
public class InlineKeyboardBuilder {

    private ArrayList<InlineKeyboardButton> currentRow = new ArrayList<>();
    private ArrayList<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

    public InlineKeyboardBuilder addButton(String text, String data) {
       InlineKeyboardButton button = new InlineKeyboardButton(text);
       button.setCallbackData(data);
       currentRow.add(button);
       return this;
    }

    public InlineKeyboardBuilder finishRow() {
        keyboard.add(currentRow);
        currentRow = new ArrayList<>();
        return this;
    }

    public InlineKeyboardMarkup build() {
        finishRow();
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(keyboard);
        keyboard = new ArrayList<>();
        return markup;
    }

    public InlineKeyboardBuilder addInlineQueryButton(String text) {
        InlineKeyboardButton button = new InlineKeyboardButton(text);
        button.setSwitchInlineQueryCurrentChat(text);
        currentRow.add(button);
        return this;
    }
}
