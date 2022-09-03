package com.app.vple.domain.dto;

import com.app.vple.domain.Menu;
import com.app.vple.domain.enums.VeganType;
import lombok.Data;

@Data
public class MenuDto {

    private Long id;

    private String name;

    private String image;

    private String veganType;

    public MenuDto(Menu entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.image = entity.getImage();
        this.veganType = entity.getVeganType();
    }
}
