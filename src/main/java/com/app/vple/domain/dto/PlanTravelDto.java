package com.app.vple.domain.dto;

import com.app.vple.domain.PlanTravel;
import lombok.Data;

@Data
public class PlanTravelDto {

    private Long id;

    private String name;

    private String address;

    private int day;

    public PlanTravelDto(PlanTravel entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.address = entity.getAddress();
        this.day = entity.getDay();
    }
}
