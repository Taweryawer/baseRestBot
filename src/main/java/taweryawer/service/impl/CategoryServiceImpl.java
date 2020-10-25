package taweryawer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import taweryawer.entities.Category;
import taweryawer.repository.CategoryRepository;
import taweryawer.service.CategoryService;
import taweryawer.service.InlineKeyboardBuilder;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
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
        int counter = 0;
        for (Category category : categories) {
            builder.addInlineQueryButton(category.getName());
            counter++;
            if (counter == 2) {
                builder.finishRow();
                counter = 0;
            }
        }
        builder.finishRow();
        return builder.build();
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.getCategoryByName(name);
    }

    @Override
    public void save(Category category) {
        categoryRepository.save(category);
    }
}
