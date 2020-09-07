package com.udacity.pricing;

import com.udacity.pricing.domain.price.PriceRepository;
import com.udacity.pricing.service.PricingService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Bean;

/**
 * Creates a Spring Boot Application to run the Pricing Service. TODO: Convert the application from
 * a REST API to a microservice.
 */
@SpringBootApplication
public class PricingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PricingServiceApplication.class, args);
    }
}
