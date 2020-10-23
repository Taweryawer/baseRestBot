package taweryawer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import taweryawer.service.OrderService;

@Controller
public class OrdersPageController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/orders")
    public String orders(@RequestParam(name = "page", defaultValue = "1") Integer page, Model model) {
        model.addAttribute("orders", orderService.getOrdersForPage(page));
        return "orders";
    }
}
