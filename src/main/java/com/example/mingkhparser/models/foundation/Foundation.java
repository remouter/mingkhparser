package com.example.mingkhparser.models.foundation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Foundation {
    private FoundationType foundationType;
    private FoundationMaterial foundationMaterial;
    private Double blindArea;
    private Double physicalDeterioration;
    private Integer lastOverhaulYear;
}
