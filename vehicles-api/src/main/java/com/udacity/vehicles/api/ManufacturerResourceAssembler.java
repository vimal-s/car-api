package com.udacity.vehicles.api;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import com.udacity.vehicles.domain.manufacturer.Manufacturer;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

/**
 * Maps the CarController to the Car class using HATEOAS
 */
@Component
public class ManufacturerResourceAssembler implements
        ResourceAssembler<Manufacturer, Resource<Manufacturer>> {

    @Override
    public Resource<Manufacturer> toResource(Manufacturer manufacturer) {
        Resource<Manufacturer> resource = new Resource<>(manufacturer);
        resource.add(
                linkTo(methodOn(CarController.class).getManufacturers()).withRel("manufacturers"));
        return resource;
    }
}
