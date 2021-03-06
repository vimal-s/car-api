package com.udacity.boogle.maps.api;

import com.udacity.boogle.maps.domain.address.Address;
import com.udacity.boogle.maps.domain.Coordinate;
import com.udacity.boogle.maps.service.MapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/maps")
public class MapController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final MapService mapService;

    public MapController(MapService mapService) {
        this.mapService = mapService;
    }

    @GetMapping
    public Address get(@RequestParam double lat, @RequestParam double lon) {
        Coordinate coordinate = new Coordinate(lat, lon);
        return mapService.getAddress(coordinate);
    }
}
