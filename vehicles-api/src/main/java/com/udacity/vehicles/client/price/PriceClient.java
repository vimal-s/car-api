package com.udacity.vehicles.client.price;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;

/**
 * Implements a class to interface with the Pricing Client for price data.
 */
@Component
public class PriceClient {

    private static final Logger logger = LoggerFactory.getLogger(PriceClient.class);
    private final String SERVICE_NAME = "pricing-service";
    private final WebClient.Builder clientBuilder;

    public PriceClient(Builder clientBuilder) {
        this.clientBuilder = clientBuilder;
    }

// In a real-world application we'll want to add some resilience
    // to this method with retries/CB/failover capabilities
    // We may also want to cache the results so we don't need to
    // do a request every time

    /**
     * Gets a vehicle price from the pricing client, given vehicle ID.
     *
     * @param vehicleId ID number of the vehicle for which to get the price
     * @return Currency and price of the requested vehicle, error message that the vehicle ID is
     * invalid, or note that the service is down.
     */
    public String getPrice(Long vehicleId) {
        try {
            Price price = clientBuilder.build()
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .host(SERVICE_NAME)
                            .path("services/price")
                            .queryParam("vehicleId", vehicleId)
                            .build())
                    .retrieve()
                    .bodyToMono(Price.class)
                    .block();
            return String.format("%s %s", price.getCurrency(), price.getPrice());
        } catch (Exception e) {
            logger.error("Unexpected error retrieving price for vehicle {}", vehicleId);
            return "(consult price)";
        }
    }

    public void deletePrice(Long vehicleId) {
        clientBuilder.build()
                .delete()
                .uri(uriBuilder -> uriBuilder
                        .host(SERVICE_NAME)
                        .path("/services/price")
                        .queryParam("vehicleId", vehicleId)
                        .build())
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
