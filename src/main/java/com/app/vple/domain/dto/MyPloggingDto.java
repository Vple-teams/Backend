package com.app.vple.domain.dto;

import com.app.vple.domain.Plogging;
import lombok.Data;

@Data
public class MyPloggingDto {

    private Long id;

    private String title;

    public MyPloggingDto(Plogging entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
    }
}
