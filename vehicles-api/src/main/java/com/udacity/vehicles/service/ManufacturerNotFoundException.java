package com.udacity.vehicles.service;

public class ManufacturerNotFoundException extends RuntimeException {

    public ManufacturerNotFoundException() {
        this("Incorrect manufacturer code. Refer the list of available manufacturers");
    }

    public ManufacturerNotFoundException(String message) {
        super(message);
    }
}
