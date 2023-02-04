package com.demo.edwin.parkinglot.exception;

public class VehicleNotFoundException extends Exception {
    public VehicleNotFoundException(String code) {
        super("Vehicle code not found: " + code);
    }
}
