package com.udacity.vehicles.client.map;

import com.udacity.vehicles.domain.Location;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;

/**
 * Implements a class to interface with the Maps Client for location data.
 */
@Component
public class MapClient {

    private static final Logger logger = LoggerFactory.getLogger(MapClient.class);
    private final String SERVICE_NAME = "map-service";
    private final WebClient.Builder clientBuilder;
    private final ModelMapper mapper;

    public MapClient(Builder clientBuilder, ModelMapper mapper) {
        this.clientBuilder = clientBuilder;
        this.mapper = mapper;
    }

    /**
     * Gets an address from the Maps client, given latitude and longitude.
     *
     * @param location An object containing "lat" and "lon" of location
     * @return An updated location including street, city, state and zip, or an exception message
     * noting the Maps service is down
     */
    public Location getAddress(Location location) {
        try {
            Address address = clientBuilder.build()
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .host(SERVICE_NAME)
                            .path("maps")
                            .queryParam("lat", location.getLat())
                            .queryParam("lon", location.getLon())
                            .build())
                    .retrieve()
                    .bodyToMono(Address.class)
                    .block();
            mapper.map(address, location);
        } catch (Exception e) {
            logger.warn("Map service is down");
            Address address = new Address("will be updated", "will be updated", "will be updated",
                    "will be updated");
            mapper.map(address, location);
        }
        return location;
    }
}
