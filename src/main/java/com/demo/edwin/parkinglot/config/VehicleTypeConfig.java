package com.demo.edwin.parkinglot.config;

import com.demo.edwin.parkinglot.entity.VehicleType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "vehicle-type")
@Getter
@Setter
public class VehicleTypeConfig {
    private Map<VehicleType, List<VehicleType>> config;
}
