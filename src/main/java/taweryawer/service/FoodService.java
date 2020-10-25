package taweryawer.service;

import taweryawer.entities.Food;
import taweryawer.entities.PriceCategory;

import java.util.List;

public interface FoodService {

    public List<Food> getAllFoodByCategory(String category);

    public String getDescriptionForFood(Food food);

    public String getContentDescriptionForFood(Food food);

    public List<Food> getAllFood();

    public Food getFoodById(Long id);

    List<PriceCategory> getAllPriceCategories();

    PriceCategory getPriceCategoryByTitle(String title);

    void saveFood(Food food);

    void updateFood(Food food);
}
