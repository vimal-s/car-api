package com.udacity.boogle.maps.repository;

import com.udacity.boogle.maps.model.Address;
import com.udacity.boogle.maps.model.Coordinate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findByCoordinate(Coordinate coordinate);
}
