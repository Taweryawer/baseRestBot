package taweryawer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import taweryawer.entities.Category;
import taweryawer.repository.CategoryRepository;
import taweryawer.service.CategoryService;
import taweryawer.service.InlineKeyboardBuilder;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private InlineKeyboardBuilder builder;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.getAllCategories();
    }

    public InlineKeyboardMarkup getCategoriesKeyboard() {
        List<Category> categories = getAllCategories();
        for (Category category : categories) {
            builder.addInlineQueryButton(category.getName());
        }
        return builder.build();
    }
}
