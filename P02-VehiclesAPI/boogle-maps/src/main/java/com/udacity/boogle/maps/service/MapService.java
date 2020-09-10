package com.udacity.boogle.maps.service;

import com.udacity.boogle.maps.model.Address;
import com.udacity.boogle.maps.model.Coordinate;
import com.udacity.boogle.maps.repository.MockAddressService;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MapService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    // todo: maybe replace this with database
    private final Map<Coordinate, Address> coordinateAddressMap = new HashMap<>();

    public Address getAddress(Coordinate coordinate) {
        Address mockAddress = MockAddressService.getRandom();
        coordinateAddressMap.put(coordinate, mockAddress);
        logger.info(coordinateAddressMap.get(coordinate).toString());
        return coordinateAddressMap.get(coordinate);
    }
}
