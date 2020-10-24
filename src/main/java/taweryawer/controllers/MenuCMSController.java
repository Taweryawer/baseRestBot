package taweryawer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import taweryawer.dto.FoodDTO;
import taweryawer.entities.Food;
import taweryawer.mappers.FoodMapper;
import taweryawer.service.FoodService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MenuCMSController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private FoodMapper foodMapper;

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
}