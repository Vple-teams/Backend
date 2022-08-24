package com.app.vple.domain.dto;

import com.app.vple.domain.PlanTravel;
import lombok.Data;

@Data
public class PlanTravelDetailDto {

    private String name;

    private String address;

    private String longitude;

    private String latitude;

    private String image;

    private int day;

    public PlanTravelDetailDto(PlanTravel entity) {
        this.name = entity.getName();
        this.address = entity.getAddress();
        this.longitude = entity.getLongitude();
        this.latitude = entity.getLatitude();
        this.image = entity.getImage();
        this.day = entity.getDay();
    }
}
