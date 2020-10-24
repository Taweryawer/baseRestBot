package taweryawer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import taweryawer.dto.FoodDTO;
import taweryawer.entities.Food;
import taweryawer.entities.PriceLabel;
import taweryawer.forms.FoodForm;
import taweryawer.mappers.FoodMapper;
import taweryawer.service.CategoryService;
import taweryawer.service.FoodService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MenuCMSController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private FoodMapper foodMapper;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/foodlist")
    public String foodListPage(Model model) {
        List<FoodDTO> foodDTOList = new ArrayList<>();
        for (Food food : foodService.getAllFood()) {
            foodDTOList.add(foodMapper.toDto(food));
        }
        model.addAttribute("foodList", foodDTOList);
        return "foodlist";
    }

    @GetMapping("/f/{id}")
    public String itemPage(@PathVariable(name = "id", required = true) Long id, Model model) {
        model.addAttribute("item", foodMapper.toDto(foodService.getFoodById(id)));
        return "foodPage";
    }

    @GetMapping("/additem")
    public String addItemPage(Model model) {
        model.addAttribute("itemcategories", categoryService.getAllCategories());
        model.addAttribute("pricecategories", foodService.getAllPriceCategories());
        return "additem";
    }

    @PostMapping("/additem")
    public String addItem(FoodForm foodForm, Model model) {
        Food food = new Food();
        food.setTitle(foodForm.getTitle());
        food.setDescription(foodForm.getDescription());
        food.setWeight(foodForm.getWeight());
        food.setPhotoURL(foodForm.getPhotoURL());
        food.setCategory(categoryService.getCategoryByName(foodForm.getCategory()));
        List<PriceLabel> priceLabels = new ArrayList<>();
        for (String data : foodForm.getPriceLabels()) {
            PriceLabel priceLabel = new PriceLabel();
            priceLabel.setPriceCategory(foodService.getPriceCategoryByTitle(data.split(" ")[0]));
            priceLabel.setValue(Double.valueOf(data.split(" ")[1]));
            priceLabels.add(priceLabel);
        }
        food.setPriceLabels(priceLabels);
        foodService.saveFood(food);

        model.addAttribute("itemcategories", categoryService.getAllCategories());
        model.addAttribute("pricecategories", foodService.getAllPriceCategories());
        return "additem";
    }
}
