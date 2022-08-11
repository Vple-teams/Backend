package com.app.vple.domain.dto;

import com.app.vple.domain.Place;
import lombok.Data;

@Data
public class PlaceDto {

    private String name;

    private String introduction;

    private Float rating;

    private String address;

    public PlaceDto(Place entity) {
        this.name = entity.getName();
        this.introduction = entity.getIntroduction();
        this.rating = entity.getRating();
        this.address = entity.getAddress();
    }
}
