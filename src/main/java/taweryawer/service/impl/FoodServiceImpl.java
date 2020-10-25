package taweryawer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import taweryawer.entities.Food;
import taweryawer.entities.PriceCategory;
import taweryawer.entities.PriceLabel;
import taweryawer.repository.FoodRepository;
import taweryawer.repository.PriceCategoryRepository;
import taweryawer.service.FoodService;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class FoodServiceImpl implements FoodService {

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private PriceCategoryRepository priceCategoryRepository;

    @Override
    public List<Food> getAllFoodByCategory(String category) {
        return foodRepository.getFoodByCategory(category);
    }

    @Override
    public String getDescriptionForFood(Food food) {
        StringBuilder sb = new StringBuilder();

        if (food.getPriceLabels().size() == 1) {
            sb.append("Ціна: " + food.getPriceLabels().get(0).getValue() + " грн\n");
        } else {
            sb.append("Ціни: \n");
            for (PriceLabel priceLabel : food.getPriceLabels()) {
                sb.append(priceLabel.getPriceCategory().getTitle() + ": " + priceLabel.getValue() + " грн\n");
            }
        }

        sb.append(food.getWeight())
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
                .append("\n\n");

        if (food.getPriceLabels().size() == 1) {
            sb.append("*Ціна:* " + food.getPriceLabels().get(0).getValue() + " грн\n");
        } else {
            sb.append("*Ціни:* \n");
            for (PriceLabel priceLabel : food.getPriceLabels()) {
                sb.append(priceLabel.getPriceCategory().getTitle() + ": " + priceLabel.getValue() + " грн\n");
            }
        }


        sb.append("\n")
                .append("*Кількість:* ")
                .append(food.getWeight())
                .append(" [⠀]" + "(" + food.getPhotoURL() + ")");
        return sb.toString();
    }

    @Override
    public List<Food> getAllFood() {
        return foodRepository.getAllFood();
    }

    @Override
    public Food getFoodById(Long id) {
        return foodRepository.getFoodById(id);
    }

    @Override
    public List<PriceCategory> getAllPriceCategories() {
        return priceCategoryRepository.getAllPriceCategories();
    }

    @Override
    public PriceCategory getPriceCategoryByTitle(String title) {
        return priceCategoryRepository.getPriceCategoryByTitle(title);
    }

    @Override
    public void saveFood(Food food) {
        foodRepository.save(food);
    }

    @Override
    public void updateFood(Food food) {
        foodRepository.update(food);
    }


}
