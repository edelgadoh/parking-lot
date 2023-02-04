package com.demo.edwin.parkinglot.request;

import com.demo.edwin.parkinglot.entity.VehicleType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class VehicleDTO {
    private String code;
    private VehicleType type;
}
