package com.app.vple.domain.dto;

import com.app.vple.domain.Plogging;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PloggingListDto {

    private Long id;

    private String title;

    private String nickname;

    private LocalDateTime createdDate;

    private Integer ploggingCommentCount;

    private Integer views;

    public PloggingListDto(Plogging entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.nickname = entity.getNickname();
        this.createdDate = entity.getCreatedDate();
        this.ploggingCommentCount = entity.getPloggingCommentCount();
        this.views = entity.getViews();
    }
}
