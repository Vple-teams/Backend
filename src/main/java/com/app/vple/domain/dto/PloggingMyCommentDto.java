package com.app.vple.domain.dto;

import com.app.vple.domain.Comment;
import com.app.vple.domain.PloggingComment;
import lombok.Data;

@Data
public class  PloggingMyCommentDto {
    private Long id;

    private String content;

    public PloggingMyCommentDto(PloggingComment entity) {
        this.id = entity.getId();
        this.content = entity.getContent();
    }
}
