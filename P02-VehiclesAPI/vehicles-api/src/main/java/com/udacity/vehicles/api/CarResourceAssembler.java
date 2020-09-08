package com.udacity.vehicles.api;

import com.udacity.vehicles.domain.car.Car;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

/**
 * Maps the CarController to the Car class using HATEOAS
 */
@Component
public class CarResourceAssembler implements ResourceAssembler<Car, Resource<Car>> {

    // todo: what is this?
    @Override
    public Resource<Car> toResource(Car car) {
        Resource<Car> resource = null;
        try {
            resource = new Resource<>(car,
                    linkTo(methodOn(CarController.class).get(car.getId())).withSelfRel(),
                    linkTo(methodOn(CarController.class).list()).withRel("cars"));
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return resource;
    }
}
