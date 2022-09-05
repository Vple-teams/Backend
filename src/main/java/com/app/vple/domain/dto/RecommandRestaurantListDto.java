package com.app.vple.domain.dto;

import com.app.vple.domain.RecommandRestaurant;
import lombok.Data;

@Data
public class RecommandRestaurantListDto {

    private Long id;

    private String name;

    private String image;

    public RecommandRestaurantListDto(RecommandRestaurant entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.image = entity.getImage();
    }
}
