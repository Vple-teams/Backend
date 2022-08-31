package com.app.vple.domain.dto;

import com.app.vple.domain.TourSpot;
import lombok.Data;

@Data
public class MapTourSpotListDto {

    private Long id;

    private String name;

    private Float latitude;

    private Float longitude;

    public MapTourSpotListDto(TourSpot entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.latitude = entity.getLatitude();
        this.longitude = entity.getLongitude();
    }
}
