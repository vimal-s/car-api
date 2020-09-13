package com.udacity.orderservice.service;

import com.udacity.orderservice.domain.Order;
import com.udacity.orderservice.domain.OrderRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public List<Order> getAll() {
        return repository.findAll();
    }

    public void delete(Long vehicleId) {
        repository.deleteByVehicleId(vehicleId);
    }

    public Order saveOne(Order order) {
        return repository.save(order);
    }

    public void deleteOne(Long orderId) {
        repository.deleteById(orderId);
    }
}
