package com.app.vple.domain.dto;

import com.app.vple.domain.Mate;
import lombok.Data;

@Data
public class MateInfoDto {

    private String nickname;

    public MateInfoDto(Mate entity) {
        this.nickname = entity.getNickname();
    }
}
