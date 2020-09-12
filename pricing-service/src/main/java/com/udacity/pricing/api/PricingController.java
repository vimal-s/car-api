package com.udacity.pricing.api;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import com.udacity.pricing.domain.price.Price;
import com.udacity.pricing.domain.price.PriceRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RepositoryRestController
public class PricingController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final PriceRepository priceRepository;

    public PricingController(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    /**
     * Gets the price for a requested vehicle.
     *
     * @param vehicleId ID number of the vehicle for which the price is requested
     * @return price of the vehicle, or error that it was not found.
     */
    @GetMapping("/prices/{vehicleId}")
    public ResponseEntity<Resource<Price>> get(@PathVariable Long vehicleId) {
        Price price = priceRepository
                .findById(vehicleId)
                .orElseGet(() -> priceRepository.save(new Price("INR", randomPrice(), vehicleId)));

        Resource<Price> resource = new Resource<>(price);
        resource.add(linkTo(methodOn(PricingController.class).get(vehicleId)).withSelfRel());

        return ResponseEntity.ok(resource);
    }

    /**
     * Gets a random price to fill in for a given vehicle ID.
     *
     * @return random price for a vehicle
     */
    private static BigDecimal randomPrice() {
        return BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(1, 5))
                .multiply(new BigDecimal("5000")).setScale(2, RoundingMode.HALF_UP);
    }
}
