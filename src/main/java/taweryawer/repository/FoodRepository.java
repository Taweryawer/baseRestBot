package taweryawer.repository;

import taweryawer.entities.Food;

import java.util.List;

public interface FoodRepository {

    public List<Food> getFoodByCategory(String category);
    public Food getFoodById(Long foodId);
}
