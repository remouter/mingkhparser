package com.example.mingkhparser.models.windows;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@ToString
public class Windows {
    private Double physicalDeterioration;
    private Set<WindowsType> windowsTypes;
}
