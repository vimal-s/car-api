package com.udacity.vehicles.service;

public class CarNotFoundException extends RuntimeException {

    public CarNotFoundException() {
        this("Car not found in the database");
    }

    public CarNotFoundException(String message) {
        super(message);
    }
}
