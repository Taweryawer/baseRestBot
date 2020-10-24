package taweryawer.service;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import taweryawer.entities.Category;

import java.util.List;

public interface CategoryService {

    public List<Category> getAllCategories();
    public InlineKeyboardMarkup getCategoriesKeyboard();

    Category getCategoryByName(String name);
}
