package com.demo.edwin.parkinglot.controller;

import com.demo.edwin.parkinglot.request.SpotDTO;
import com.demo.edwin.parkinglot.service.SpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static java.lang.Boolean.TRUE;

@RestController
@RequestMapping("spots")
public class SpotController {

    @Autowired
    SpotService spotService;

    @GetMapping("/available")
    ResponseEntity<Long> getAvailableSpots() {
        return ResponseEntity.ok(spotService.getSpots(TRUE));
    }

    @GetMapping("/is-full")
    ResponseEntity<Boolean> isParkingLotFull() {
        return ResponseEntity.ok(spotService.getSpots(TRUE).equals(0L));
    }

    @PostMapping
    ResponseEntity<Void> saveSpot(@RequestBody SpotDTO spotDTO) {
        spotService.saveSpot(spotDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
