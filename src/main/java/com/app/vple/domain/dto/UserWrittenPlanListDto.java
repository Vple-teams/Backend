package com.app.vple.domain.dto;

import com.app.vple.domain.Plan;
import lombok.Data;

@Data
public class UserWrittenPlanListDto {

    private Long id;

    private String title;

    private String image;

    public UserWrittenPlanListDto(Plan entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.image = "";
    }
}
