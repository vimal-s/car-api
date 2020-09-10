package com.udacity.boogle.maps.controller;

import com.udacity.boogle.maps.model.Address;
import com.udacity.boogle.maps.model.Coordinate;
import com.udacity.boogle.maps.service.MapService;
import com.udacity.pricing.domain.price.Price;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;

@RestController
@RequestMapping("/maps")
public class MapController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final MapService mapService;
    RestTemplate restTemplate;
    WebClient.Builder webclient;

    public MapController(MapService mapService,
            RestTemplate restTemplate,
            Builder webclient) {
        this.mapService = mapService;
        this.restTemplate = restTemplate;
        this.webclient = webclient;
    }

    @GetMapping
    public Address get(@RequestParam double lat, @RequestParam double lon) {
        Coordinate coordinate = new Coordinate(lat, lon);
        logger.info(coordinate.toString());
        printPrice();
        print();
        return mapService.getAddress(coordinate);
    }

    private void printPrice() {
        Price price = restTemplate
                .getForObject("http://pricing-service/services/price?vehicleId=1", Price.class);
        logger.info(price.toString());
    }

    private void print() {
        Price price = webclient.build()
                .get()
                .uri("http://pricing-service/services/price?vehicleId=1")
                .retrieve()
                .bodyToMono(Price.class)
                .block();
        logger.info("from " + price.toString());
    }
}
