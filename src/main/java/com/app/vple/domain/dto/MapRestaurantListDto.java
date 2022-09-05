package com.app.vple.domain.dto;

import com.app.vple.domain.RecommandRestaurant;
import lombok.Data;

@Data
public class MapRestaurantListDto {

    private Long id;

    private String name;

    private String latitude;

    private String longitude;

    public MapRestaurantListDto(RecommandRestaurant entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.latitude = entity.getLatitude();
        this.longitude = entity.getLongitude();
    }
}
