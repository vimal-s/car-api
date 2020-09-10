package com.udacity.vehicles.service;

import com.udacity.vehicles.client.maps.MapsClient;
import com.udacity.vehicles.client.prices.PriceClient;
import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.domain.car.CarRepository;
import java.util.List;
import java.util.function.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Implements the car service create, read, update or delete information about vehicles, as well as
 * gather related location and price data when desired.
 */
@Service
public class CarService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final CarRepository repository;
    private final PriceClient priceClient;
    private final MapsClient mapsClient;

    public CarService(CarRepository repository, PriceClient priceClient, MapsClient mapsClient) {
        this.repository = repository;
        this.priceClient = priceClient;
        this.mapsClient = mapsClient;
    }

    /**
     * Gathers a list of all vehicles
     *
     * @return a list of all vehicles in the CarRepository
     */
    public List<Car> list() {
        return repository.findAll();
    }

    /**
     * Gets car information by ID (or throws exception if non-existent)
     *
     * @param id the ID number of the car to gather information on
     * @return the requested car's information, including location and price
     */
    public Car findById(Long id) throws Throwable {
        Car car =
                repository
                        .findById(id)
                        .orElseThrow((Supplier<Throwable>) CarNotFoundException::new);

        logger.info(car.getPrice());
        car.setPrice(priceClient.getPrice(id));
        logger.info(car.getPrice());

        logger.info(car.getLocation().toString());
        car.setLocation(mapsClient.getAddress(car.getLocation()));
        logger.info(car.getLocation().toString());
        return car;
    }

    /**
     * Either creates or updates a vehicle, based on prior existence of car
     *
     * @param car A car object, which can be either new or existing
     * @return the new/updated car is stored in the repository
     */
    public Car save(Car car) {
        logger.info(car.toString());
        if (car.getId() != null) {
            return repository
                    .findById(car.getId())
                    .map(carToBeUpdated -> {
                        carToBeUpdated.setDetails(car.getDetails());
                        carToBeUpdated.setLocation(car.getLocation());
                        return repository.save(carToBeUpdated);
                    })
                    .orElseThrow(CarNotFoundException::new);
        }

        return repository.save(car);
    }

    /**
     * Deletes a given car by ID
     *
     * @param id the ID number of the car to delete
     */
    public void delete(Long id) {
        repository.findById(id).ifPresentOrElse(repository::delete, () -> {
            throw new CarNotFoundException();
        });
        priceClient.deletePrice(id);
    }
}
