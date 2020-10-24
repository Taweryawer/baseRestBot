package taweryawer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import taweryawer.dto.OrderDTO;
import taweryawer.entities.Order;
import taweryawer.mappers.OrderMapper;
import taweryawer.service.OrderService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class OrdersPageController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderMapper orderMapper;

    @GetMapping("/orders")
    public String orders(@RequestParam(name = "page", defaultValue = "1") Integer page, Model model) {
        List<OrderDTO> orders = new ArrayList<>();
        for (Order order : orderService.getOrdersForPage(page)) {
            orders.add(orderMapper.toDto(order));
        }
        model.addAttribute("orders", orders);
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
