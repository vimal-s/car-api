package com.udacity.vehicles.service;

import com.udacity.vehicles.client.map.MapClient;
import com.udacity.vehicles.client.price.PriceClient;
import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.domain.car.CarRepository;
import com.udacity.vehicles.domain.manufacturer.Manufacturer;
import com.udacity.vehicles.domain.manufacturer.ManufacturerRepository;
import java.util.List;
import java.util.function.Supplier;
import javax.transaction.Transactional;
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
    private final CarRepository carRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final PriceClient priceClient;
    private final MapClient mapClient;

    public CarService(CarRepository carRepository,
            ManufacturerRepository manufacturerRepository,
            PriceClient priceClient, MapClient mapClient) {
        this.carRepository = carRepository;
        this.manufacturerRepository = manufacturerRepository;
        this.priceClient = priceClient;
        this.mapClient = mapClient;
    }

    /**
     * Gathers a list of all vehicles
     *
     * @return a list of all vehicles in the CarRepository
     */
    public List<Car> list() {
        List<Car> cars = carRepository.findAll();
        cars.forEach(this::updatePriceAndLocation);
        return cars;
    }

    /**
     * Gets car information by ID (or throws exception if non-existent)
     *
     * @param id the ID number of the car to gather information on
     * @return the requested car's information, including location and price
     */
    public Car findById(Long id) throws Throwable {
        Car car =
                carRepository
                        .findById(id)
                        .orElseThrow((Supplier<Throwable>) () ->
                                new CarNotFoundException("Car not found with id: " + id));
        updatePriceAndLocation(car);
        return car;
    }

    /**
     * Either creates or updates a vehicle, based on prior existence of car
     *
     * @param car A car object, which can be either new or existing
     * @return the new/updated car is stored in the repository
     */
    public Car save(Car car) throws Throwable {
        manufacturerRepository
                .findById(car.getDetails().getManufacturer().getCode())
                .orElseThrow((Supplier<Throwable>) ManufacturerNotFoundException::new);

        if (car.getId() != null && car.getId() != 0) {
            return carRepository
                    .findById(car.getId())
                    .map(carToBeUpdated -> {
                        carToBeUpdated.setDetails(car.getDetails());
                        carToBeUpdated.setLocation(car.getLocation());
                        Car car1 = carRepository.save(carToBeUpdated);
                        updatePriceAndLocation(car1);
                        return car1;
                    })
                    .orElseThrow((Supplier<Throwable>) () ->
                            new CarNotFoundException("Car not found with id: " + car.getId()));
        }

        Car car1 = carRepository.save(car);
        updatePriceAndLocation(car1);
        return car1;
    }

    /**
     * Deletes a given car by ID
     *
     * @param id the ID number of the car to delete
     */
    @Transactional
    public void delete(Long id) {
        carRepository.findById(id).ifPresentOrElse(car -> carRepository.deleteById(id), () -> {
            throw new CarNotFoundException("Car not found with id: " + id);
        });
        try {
            priceClient.deletePrice(id);
        } catch (Exception e) {
            throw new ServiceUnavailableException();
        }
    }

    private void updatePriceAndLocation(Car car) {
        car.setPrice(priceClient.getPrice(car.getId()));
        car.setLocation(mapClient.getAddress(car.getLocation()));
    }

    public List<Manufacturer> getManufacturers() {
        return manufacturerRepository.findAll();
    }
}
