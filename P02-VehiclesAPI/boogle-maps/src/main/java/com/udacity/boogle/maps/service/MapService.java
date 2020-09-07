package com.udacity.boogle.maps.service;

import com.udacity.boogle.maps.model.Address;
import com.udacity.boogle.maps.model.Coordinate;
import com.udacity.boogle.maps.repository.MockAddressRepository;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MapService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Map<Coordinate, Address> coordinateAddressMap = new HashMap<>();

    public Address getAddress(Coordinate coordinate) {
        if (MockAddressRepository.getTotalAddresses() <= coordinateAddressMap.size()) {
            throw new RuntimeException("No more addresses available. Please add more.");
        }

        coordinateAddressMap.forEach(
                (coordinate1, address) -> logger
                        .info(coordinate1.toString() + " -> " + address.toString()));

        if (!coordinateAddressMap.containsKey(coordinate)) {
            Address mockAddress;
            do {
                mockAddress = MockAddressRepository.getRandom();
            } while (coordinateAddressMap.containsValue(mockAddress));
            coordinateAddressMap.put(coordinate, mockAddress);

            logger.info(coordinateAddressMap.get(coordinate).toString());
        }

        return coordinateAddressMap.get(coordinate);
    }
}
