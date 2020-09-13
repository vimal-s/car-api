package com.udacity.vehicles.client.order;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;

@Component
public class OrderClient {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String SERVICE_NAME = "order-service";
    private final WebClient.Builder clientBuilder;

    public OrderClient(Builder clientBuilder) {
        this.clientBuilder = clientBuilder;
    }

    public Order saveOrder(Order order) {
        return clientBuilder
                .build()
                .post()
                .uri("http://" + SERVICE_NAME + "/orders")
                .body(BodyInserters.fromObject(order))
                .retrieve()
                .bodyToMono(Order.class)
                .block();
    }

    public void deleteOrders(Long vehicleId) {
        clientBuilder
                .build()
                .delete()
                .uri("http://" + SERVICE_NAME + "/orders?vehicleId=" + vehicleId)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public List<Order> findAll() {
        return clientBuilder
                .build()
                .get()
                .uri("http://" + SERVICE_NAME + "/orders")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Order>>() {
                })
                .block();
    }
}
