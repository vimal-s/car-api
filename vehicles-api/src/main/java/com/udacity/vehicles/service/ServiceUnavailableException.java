package com.udacity.vehicles.service;

public class ServiceUnavailableException extends RuntimeException {

    public ServiceUnavailableException() {
        this("Error connecting. pricing-service may be down");
    }

    public ServiceUnavailableException(String message) {
        super(message);
    }
}
