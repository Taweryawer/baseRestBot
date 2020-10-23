package taweryawer.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrdersPageController {

    @GetMapping("/orders")
    public String orders(Model model) {
        return "orders";
    }
}
