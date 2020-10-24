package taweryawer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import taweryawer.service.OrderService;

import java.util.Arrays;
import java.util.List;

@Controller
public class OrdersPageController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/orders")
    public String orders(@RequestParam(name = "page", defaultValue = "1") Integer page, Model model) {
        model.addAttribute("orders", orderService.getOrdersForPage(page));
        model.addAttribute("currentPage", page);
        model.addAttribute("ordersCount", orderService.getOrdersCount());
        return "orders";
    }

    @GetMapping("/o/{id}")
    public String orderPage(@PathVariable(name = "id", required = true) Long orderId, Model model) {
        model.addAttribute("order", orderService.getOrderById(orderId));
        List<String> summary = Arrays.asList(orderService.getOrderSummary(orderId).replace("*", "").replace("Ваше замовлення:", "").split("\n"));
        model.addAttribute("summary", summary);
        return "order";
    }
}
