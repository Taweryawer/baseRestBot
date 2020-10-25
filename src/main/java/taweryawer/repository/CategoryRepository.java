package taweryawer.repository;

import taweryawer.entities.Category;

import java.util.List;

public interface CategoryRepository {

    public List<Category> getAllCategories();

    Category getCategoryByName(String name);

    void save(Category category);
}
