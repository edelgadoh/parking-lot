package com.demo.edwin.parkinglot.controller;

import com.demo.edwin.parkinglot.entity.VehicleType;
import com.demo.edwin.parkinglot.exception.ParkingSpotAlreadyExistException;
import com.demo.edwin.parkinglot.exception.SpotNotAvailableException;
import com.demo.edwin.parkinglot.exception.VehicleNotFoundException;
import com.demo.edwin.parkinglot.exception.VehicleNotFoundParkingSpotException;
import com.demo.edwin.parkinglot.request.VehicleDTO;
import com.demo.edwin.parkinglot.service.ParkingSpotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("parking-spots")
@Slf4j
public class ParkingSpotController {

    @Autowired
    ParkingSpotService parkingSpotService;

    @PostMapping
    ResponseEntity<Void> takeInVehicle(@RequestBody VehicleDTO vehicleDTO) throws ParkingSpotAlreadyExistException, SpotNotAvailableException {
        log.info("Take in vehicle {}", vehicleDTO);
        parkingSpotService.takeInVehicle(vehicleDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/vehicle-code/{code}")
    ResponseEntity<Void> removeVehicle(@PathVariable String code) throws VehicleNotFoundException, VehicleNotFoundParkingSpotException {
        log.info("Remove vehicle with code {}", code);
        parkingSpotService.removeVehicle(code);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/vehicle-type/{type}")
    ResponseEntity<Long> findParkingSpots(@PathVariable VehicleType type) {
        log.info("Find parking spots used for vehicle type {}", type);
        return ResponseEntity.ok(parkingSpotService.findSpots(type));
    }

}
