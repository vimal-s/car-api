package com.udacity.boogle.maps.domain.address;

import com.udacity.boogle.maps.domain.Coordinate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findByCoordinate(Coordinate coordinate);
}
