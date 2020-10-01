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

    @Override
    public String getDescriptionForFood(Food food) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n")
            .append("Київ: ")
            .append(food.getPriceKyiv())
            .append(" грн")
            .append(" Харків: ")
            .append(food.getPriceKharkiv())
            .append(" грн")
            .append("\n")
            .append(food.getWeight())
            .append("\n")
            .append(food.getDescription());
        return sb.toString();
    }

    @Override
    public String getContentDescriptionForFood(Food food) {
        StringBuilder sb = new StringBuilder();
        sb.append("*" + food.getTitle() + "*")
                .append("\n\n")
                .append(food.getDescription())
                .append("\n\n")
                .append("*Ціна:* ")
                .append(food.getPriceKyiv() + " | " + food.getPriceKharkiv())
                .append("\n")
                .append("*Кількість:* ")
                .append(food.getWeight());
        return sb.toString();
    }


}
