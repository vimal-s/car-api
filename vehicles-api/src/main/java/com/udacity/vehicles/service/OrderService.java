package com.udacity.vehicles.service;

import com.udacity.vehicles.client.order.Order;
import com.udacity.vehicles.client.order.OrderClient;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderClient orderClient;

    public OrderService(OrderClient orderClient) {
        this.orderClient = orderClient;
    }

    public void deleteOrders(Long vehicleId) {
        try {
            orderClient.deleteOrders(vehicleId);
        } catch (Exception e) {
            throw new ServiceUnavailableException("Order service may be down");
        }
    }

    public Order saveOrder(Order order) {
        if (order.getVehicleId() == 0) {
            throw new RuntimeException("vehicleId must not be " + order.getVehicleId());
        }
        Order order1 = null;
        try {
            order1 = orderClient.saveOrder(order);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return order1;
    }

    public List<Order> findAllOrders() {
        return orderClient.findAll();
    }
}
