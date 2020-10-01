package taweryawer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import taweryawer.entities.Food;
import taweryawer.repository.FoodRepository;
import taweryawer.service.FoodService;

import java.util.List;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    private FoodRepository foodRepository;

    @Override
    public List<Food> getAllFoodByCategory(String category) {
        return foodRepository.getFoodByCategory(category);
    }
}
