package com.demo.edwin.parkinglot.exception;

public class SpotNotFoundException extends Exception {

    public SpotNotFoundException(String code) {
        super("Spot code not found: " + code);
    }
}
