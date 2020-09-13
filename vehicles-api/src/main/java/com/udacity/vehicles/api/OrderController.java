package com.udacity.vehicles.api;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import com.udacity.vehicles.client.order.Order;
import com.udacity.vehicles.service.OrderService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
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

    private final OrderService orderService;
    private final OrderResourceAssembler resourceAssembler;

    public OrderController(OrderService orderService,
            OrderResourceAssembler resourceAssembler) {
        this.orderService = orderService;
        this.resourceAssembler = resourceAssembler;
    }


    @PostMapping
    @ApiOperation(value = "Create a new order")
    ResponseEntity<Resource<Order>> newOrder(@RequestBody Order order) {
        Order order1 = orderService.saveOrder(order);
        Resource<Order> resource = resourceAssembler.toResource(order1);
        return ResponseEntity.ok().body(resource);
    }

    @DeleteMapping
    @ApiOperation(value = "Delete orders corresponding to this vehicleId")
    ResponseEntity<?> deleteOrder(@RequestParam Long vehicleId) {
        orderService.deleteOrders(vehicleId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @ApiOperation(value = "Retrieve all orders")
    Resources<Resource<Order>> getAllOrders() {
        List<Resource<Order>> resources = orderService
                .findAllOrders()
                .stream()
                .map(resourceAssembler::toResource)
                .collect(Collectors.toList());
        return new Resources<>(resources,
                linkTo(methodOn(OrderController.class).getAllOrders()).withSelfRel());
    }
}
