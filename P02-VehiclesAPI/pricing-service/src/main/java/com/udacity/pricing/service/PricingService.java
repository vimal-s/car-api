package com.udacity.pricing.service;

import com.udacity.pricing.domain.price.Price;
import com.udacity.pricing.domain.price.PriceRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.stereotype.Service;

/**
 * Implements the pricing service to get prices for each vehicle.
 */
@Service
public class PricingService {

    private final PriceRepository priceRepository;

    public PricingService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    /**
     * If a valid vehicle ID, gets the price of the vehicle from the stored array.
     *
     * @param vehicleId ID number of the vehicle the price is requested for.
     * @return price of the requested vehicle
     */
    public Price getPrice(Long vehicleId) {
        return priceRepository
                .findByVehicleId(vehicleId)
                .orElseGet(() -> priceRepository.save(new Price("INR", randomPrice(), vehicleId)));
    }

    public void deletePrice(Long vehicleId) {
        priceRepository.deleteByVehicleId(vehicleId);
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
