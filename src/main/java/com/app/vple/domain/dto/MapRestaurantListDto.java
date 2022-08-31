package com.app.vple.domain.dto;

import com.app.vple.domain.Restaurant;
import lombok.Data;

@Data
public class MapRestaurantListDto {

    private Long id;

    private String name;

    private Float latitude;

    private Float longitude;

    public MapRestaurantListDto(Restaurant entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.latitude = entity.getLatitude();
        this.longitude = entity.getLongitude();
    }
}
