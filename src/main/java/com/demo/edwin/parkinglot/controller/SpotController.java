package com.demo.edwin.parkinglot.controller;

import com.demo.edwin.parkinglot.entity.Spot;
import com.demo.edwin.parkinglot.request.SpotDTO;
import com.demo.edwin.parkinglot.service.SpotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.lang.Boolean.TRUE;

@RestController
@RequestMapping("spots")
@Slf4j
public class SpotController {

    @Autowired
    SpotService spotService;

    @GetMapping("/available")
    ResponseEntity<Long> getAvailableSpots() {
        return ResponseEntity.ok(spotService.getAllSpots(TRUE));
    }

    @GetMapping("/is-full")
    ResponseEntity<Boolean> isParkingLotFull() {
        return ResponseEntity.ok(spotService.getAllSpots(TRUE).equals(0L));
    }

    @PostMapping
    ResponseEntity<Void> saveSpot(@RequestBody SpotDTO spotDTO) {
        log.info("Save spot {}", spotDTO);
        spotService.saveSpot(spotDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/list")
    ResponseEntity<List<Spot>> getSpots() {
        return ResponseEntity.ok(spotService.getAllSpots());
    }


}
