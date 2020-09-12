package com.udacity.boogle.maps.service;

public class NoMoreAddressException extends RuntimeException {

    public NoMoreAddressException() {
        this("No more mock addresses can be generated.");
    }

    public NoMoreAddressException(String message) {
        super(message);
    }
}
