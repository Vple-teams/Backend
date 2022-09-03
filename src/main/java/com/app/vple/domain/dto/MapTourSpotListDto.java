package com.app.vple.domain.dto;

import com.app.vple.domain.RecommandTourSpot;
import lombok.Data;

@Data
public class MapTourSpotListDto {

    private Long id;

    private String name;

    private Float latitude;

    private Float longitude;

    public MapTourSpotListDto(RecommandTourSpot entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.latitude = entity.getLatitude();
        this.longitude = entity.getLongitude();
    }
}
