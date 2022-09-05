package com.app.vple.domain.dto;

import com.app.vple.domain.RecommandTourSpot;
import lombok.Data;

@Data
public class RecommandTourSpotListDto {

    private Long id;

    private String name;

    private String image;

    public RecommandTourSpotListDto(RecommandTourSpot entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.image = entity.getImage();
    }
}
