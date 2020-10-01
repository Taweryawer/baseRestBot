package taweryawer.service;

import taweryawer.entities.Food;

import java.util.List;

public interface FoodService {

    public List<Food> getAllFoodByCategory(String category);
}
