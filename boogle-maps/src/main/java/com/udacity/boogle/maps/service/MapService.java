package com.udacity.boogle.maps.service;

import com.udacity.boogle.maps.domain.address.Address;
import com.udacity.boogle.maps.domain.Coordinate;
import com.udacity.boogle.maps.domain.address.AddressRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MapService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AddressRepository repository;

    public MapService(AddressRepository repository) {
        this.repository = repository;
    }

    public Address getAddress(Coordinate coordinate) {
        return repository
                .findByCoordinate(coordinate)
                .orElseGet(() -> {
                    Address mockAddress = MockAddressService.getRandom();
                    mockAddress.setCoordinate(coordinate);
                    return repository.save(mockAddress);
                });
    }
}
