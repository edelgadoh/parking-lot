package com.demo.edwin.parkinglot.controller;

import com.demo.edwin.parkinglot.AbstractFunctionalTest;
import com.demo.edwin.parkinglot.entity.VehicleType;
import com.demo.edwin.parkinglot.request.VehicleDTO;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class SpotControllerTest extends AbstractFunctionalTest {


    @Test
    void givenPredefinedSpots_getAvailableSpots() {

        ResponseEntity<Long> responseEntity = restTemplate.getForEntity("/spots/available", Long.class);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(14, responseEntity.getBody());

    }

    @Test
    void givenPredefinedSpots_validateIsNotFull() {

        ResponseEntity<Boolean> responseEntity = restTemplate.getForEntity("/spots/is-full", Boolean.class);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertFalse(responseEntity.getBody());

    }

    @Test
    void givenPredefinedSpots_takeInSomeVehicles_getAvailableSpots() {

        //Given
        for (int i = 0; i < maxVanAvailableSpots; i++) {
            VehicleDTO vehicleDTO = new VehicleDTO();
            vehicleDTO.setCode(RandomStringUtils.random(10));
            vehicleDTO.setType(VehicleType.VAN);
            ResponseEntity<?> secondResponseEntity = restTemplate.postForEntity("/parking-spots", vehicleDTO, null);
            Assertions.assertEquals(HttpStatus.OK, secondResponseEntity.getStatusCode());
        }

        VehicleDTO vehicleDTO = new VehicleDTO();
        vehicleDTO.setCode(RandomStringUtils.random(10));
        vehicleDTO.setType(VehicleType.MOTORCYCLE);
        ResponseEntity<?> secondResponseEntity = restTemplate.postForEntity("/parking-spots", vehicleDTO, null);
        Assertions.assertEquals(HttpStatus.OK, secondResponseEntity.getStatusCode());

        //When
        ResponseEntity<Long> responseEntity = restTemplate.getForEntity("/spots/available", Long.class);

        //Then
        long remainingAvailableSpots = 6;
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(remainingAvailableSpots, responseEntity.getBody());

    }

    @Test
    void givenPredefinedSpots_takeInAllSpots_validateIsFull(){
        for (int i = 0; i < maxMotorcycleAvailableSpots; i++) {
            VehicleDTO vehicleDTO = new VehicleDTO();
            vehicleDTO.setCode(RandomStringUtils.random(10));
            vehicleDTO.setType(VehicleType.MOTORCYCLE);
            ResponseEntity<?> secondResponseEntity = restTemplate.postForEntity("/parking-spots", vehicleDTO, null);
            Assertions.assertEquals(HttpStatus.OK, secondResponseEntity.getStatusCode());
        }

        ResponseEntity<Boolean> responseEntity = restTemplate.getForEntity("/spots/is-full", Boolean.class);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertTrue(responseEntity.getBody());

    }

}