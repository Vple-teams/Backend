package com.app.vple.domain.dto;

import com.app.vple.domain.Plan;
import lombok.Data;

@Data
public class MyPlansDto {

    private Long id;

    private String title;

    public MyPlansDto(Plan entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
    }
}
