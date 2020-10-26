package taweryawer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import taweryawer.dto.FoodDTO;
import taweryawer.entities.Category;
import taweryawer.entities.Food;
import taweryawer.entities.PriceCategory;
import taweryawer.entities.PriceLabel;
import taweryawer.forms.FoodForm;
import taweryawer.mappers.FoodMapper;
import taweryawer.service.CategoryService;
import taweryawer.service.FoodService;

import javax.validation.Valid;
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
        model.addAttribute("foodForm", new FoodForm());
        return "additem";
    }

    @PostMapping("/additem")
    public String addItem(@Valid FoodForm foodForm, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("itemcategories", categoryService.getAllCategories());
            model.addAttribute("pricecategories", foodService.getAllPriceCategories());
            return "additem";
        }
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

        return "redirect:/additem";
    }

    @GetMapping("/edititem")
    public String editItemPage(@RequestParam(name = "id") Long id, Model model) {
        model.addAttribute("itemcategories", categoryService.getAllCategories());
        model.addAttribute("pricecategories", foodService.getAllPriceCategories());
        model.addAttribute("item", foodMapper.toDto(foodService.getFoodById(id)));
        model.addAttribute("foodForm", new FoodForm());
        return "edititem";
    }

    @PostMapping("/editItem")
    public String editItem(@Valid FoodForm foodForm, BindingResult result, @RequestParam(name = "id") Long foodId, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("itemcategories", categoryService.getAllCategories());
            model.addAttribute("pricecategories", foodService.getAllPriceCategories());
            return "additem";
        }
        Food food = foodService.getFoodById(foodId);
        food.setTitle(foodForm.getTitle());
        food.setDescription(foodForm.getDescription());
        food.setWeight(foodForm.getWeight());
        food.setPhotoURL(foodForm.getPhotoURL());
        food.setCategory(categoryService.getCategoryByName(foodForm.getCategory()));
        foodForm.getPriceLabels().forEach(x -> {
            boolean found = false;
            String name = x.split(" ")[0];
            Double value = Double.valueOf(x.split(" ")[1]);
            for (PriceLabel priceLabel : food.getPriceLabels()) {
                if (priceLabel.getPriceCategory().getTitle().equals(name)) {
                    priceLabel.setValue(value);
                    found = true;
                }
            }
            if (!found) {
                PriceLabel priceLabel = new PriceLabel();
                PriceCategory priceCategory = foodService.getPriceCategoryByTitle(name);
                priceLabel.setPriceCategory(priceCategory);
                priceLabel.setValue(value);
                foodService.savePriceLabel(priceLabel);
                food.getPriceLabels().add(priceLabel);
            }
        });
        foodService.updateFood(food);

        return "redirect:/f/" + foodId;
    }

    @GetMapping("/categorieslist")
    public String categoriesList(Model model) {
        model.addAttribute("itemcategories", categoryService.getAllCategories());
        model.addAttribute("pricecategories", foodService.getAllPriceCategories());
        return "categorieslist";
    }

    @PostMapping("/addcategory")
    public String addCategory(String name) {
        Category category = new Category();
        category.setName(name);
        categoryService.save(category);
        return "redirect:/categorieslist";
    }

    @PostMapping("/addpricecategory")
    public String addPriceCategory(String title) {
        PriceCategory category = new PriceCategory();
        category.setTitle(title);
        foodService.savePriceCategory(category);
        return "redirect:/categorieslist";
    }

    @GetMapping("/removeitem")
    public String removeItem(@RequestParam(name = "id") Long id) {
        foodService.removeFoodById(id);
        return "redirect:/foodlist";
    }
}
