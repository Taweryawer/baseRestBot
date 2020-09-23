package taweryawer.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;

@Service
@Scope("singleton")
public class ReplyKeyboardBuilder {

    private ArrayList<KeyboardRow> keyboard = new ArrayList<>();
    private ArrayList<String> currentRow = new ArrayList<>();

    public ReplyKeyboardBuilder add(String buttonText) {
        currentRow.add(buttonText);
        if (currentRow.size() >= 2) {
            finishRow();
        }
        return this;
    }

    private void finishRow() {
        KeyboardRow row = new KeyboardRow();
        for (String buttonText : currentRow) {
            row.add(buttonText);
        }
        keyboard.add(row);
        currentRow = new ArrayList<>();
    }

    public ReplyKeyboardMarkup build() {
        finishRow();
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboard);
        replyKeyboardMarkup.setResizeKeyboard(true);
        keyboard = new ArrayList<>();
        return replyKeyboardMarkup;
    }

}
