package com.app.vple.domain.dto;

import com.app.vple.domain.Mate;
import lombok.Data;

@Data
public class MateInfoDto {

    private String nickname;

    private Long id;

    private String image;

    public MateInfoDto(Mate entity) {
        this.nickname = entity.getUser().getNickname();
        this.id = entity.getUser().getId();
        this.image = entity.getUser().getImage();
    }
}
