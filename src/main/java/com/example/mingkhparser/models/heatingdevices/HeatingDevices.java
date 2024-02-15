package com.example.mingkhparser.models.heatingdevices;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@ToString
public class HeatingDevices {
    private Integer physicalDeterioration;
    private Set<HeatingDevicesType> heatingDevicesTypes;
}
