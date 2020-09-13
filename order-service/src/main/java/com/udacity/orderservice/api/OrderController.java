package com.udacity.orderservice.api;

import com.udacity.orderservice.domain.Order;
import com.udacity.orderservice.service.OrderService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return service.getAll();
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return service.saveOne(order);
    }

    @DeleteMapping
    public void deleteOrders(@RequestParam Long vehicleId) {
        service.delete(vehicleId);
    }

/*

    @PutMapping
    public Order updateOrder(@RequestParam Long orderId, @RequestBody Order order) {
        order.setId(orderId);
        return service.saveOne(order);
    }

    @DeleteMapping
    public void deleteOrder(@RequestParam Long orderId) {
        service.deleteOne(orderId);
    }
*/
}
