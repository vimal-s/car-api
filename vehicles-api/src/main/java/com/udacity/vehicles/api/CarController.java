package com.udacity.vehicles.api;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.domain.manufacturer.Manufacturer;
import com.udacity.vehicles.service.CarService;
import io.swagger.annotations.ApiOperation;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implements a REST-based controller for the Vehicles API.
 */
@RestController
@RequestMapping("/cars")
class CarController {

    private final CarService carService;
    private final CarResourceAssembler carAssembler;
    private final ManufacturerResourceAssembler manufacturerAssembler;

    CarController(CarService carService, CarResourceAssembler carAssembler,
            ManufacturerResourceAssembler manufacturerAssembler) {
        this.carService = carService;
        this.carAssembler = carAssembler;
        this.manufacturerAssembler = manufacturerAssembler;
    }

    /**
     * Creates a list to store any vehicles.
     *
     * @return list of vehicles
     */
    @GetMapping
    @ApiOperation(value = "Retrieve all stored cars")
    Resources<Resource<Car>> list() {
        List<Resource<Car>> resources =
                carService
                        .list()
                        .stream()
                        .map(carAssembler::toResource)
                        .collect(Collectors.toList());

        return new Resources<>(resources,
                linkTo(methodOn(CarController.class).list()).withSelfRel());
    }

    /**
     * Gets information of a specific car by ID.
     *
     * @param id the id number of the given vehicle
     * @return all information for the requested vehicle
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "Retrieve a specific car")
    Resource<Car> get(@PathVariable Long id) throws Throwable {
        Car car = carService.findById(id);
        return carAssembler.toResource(car);
    }

    /**
     * Posts information to create a new vehicle in the system.
     *
     * @param car A new vehicle to add to the system.
     * @return response that the new vehicle was added to the system
     * @throws URISyntaxException if the request contains invalid fields or syntax
     */
    @PostMapping
    @ApiOperation(value = "Save a new car")
    ResponseEntity<?> post(@Valid @RequestBody Car car) throws Throwable {
        Car car1 = carService.save(car);
        Resource<Car> resource = carAssembler.toResource(car1);
        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    /**
     * Updates the information of a vehicle in the system.
     *
     * @param id  The ID number for which to update vehicle information.
     * @param car The updated information about the related vehicle.
     * @return response that the vehicle was updated in the system
     */
    @PutMapping("/{id}")
    @ApiOperation(value = "Update details and location of a specific car")
    ResponseEntity<?> put(@PathVariable Long id, @Valid @RequestBody Car car) throws Throwable {
        car.setId(id);
        Car car1 = carService.save(car);
        Resource<Car> resource = carAssembler.toResource(car1);
        return ResponseEntity.ok(resource);
    }

    /**
     * Removes a vehicle from the system.
     *
     * @param id The ID number of the vehicle to remove.
     * @return response that the related vehicle is no longer in the system
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a specific car")
    ResponseEntity<?> delete(@PathVariable Long id) {
        carService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/manufacturers")
    @ApiOperation(value = "Get all available manufacturers")
    Resources<Resource<Manufacturer>> getManufacturers() {
        List<Resource<Manufacturer>> resources =
                carService
                        .getManufacturers()
                        .stream()
                        .map(manufacturerAssembler::toResource)
                        .collect(Collectors.toList());

        return new Resources<>(resources,
                linkTo(methodOn(CarController.class).getManufacturers()).withSelfRel());
    }
}
