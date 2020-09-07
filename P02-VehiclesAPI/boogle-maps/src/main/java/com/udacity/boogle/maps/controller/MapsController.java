package com.udacity.boogle.maps.controller;

import com.udacity.boogle.maps.model.Address;
import com.udacity.boogle.maps.model.Coordinate;
import com.udacity.boogle.maps.service.MapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/maps")
public class MapsController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final MapService mapService;

    public MapsController(MapService mapService) {
        this.mapService = mapService;
    }

    @GetMapping
    public Address get(@RequestParam double lat, @RequestParam double lon) {
        Coordinate coordinate = new Coordinate(lat, lon);
        logger.info(coordinate.toString());
        return mapService.getAddress(coordinate);
    }
}
