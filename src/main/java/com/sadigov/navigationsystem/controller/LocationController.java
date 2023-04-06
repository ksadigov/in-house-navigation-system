package com.sadigov.navigationsystem.controller;

import com.sadigov.navigationsystem.dto.LocationDto;
import com.sadigov.navigationsystem.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/location")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;

    @GetMapping("/{mobileStationId}")
    public ResponseEntity<LocationDto> getLocation(@PathVariable UUID mobileStationId) {
        return new ResponseEntity<>(locationService.getMobileStationLocation(mobileStationId), HttpStatus.OK);
    }
}

