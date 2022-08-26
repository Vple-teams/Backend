package com.app.vple.domain.dto;

import com.app.vple.domain.Comment;
import com.app.vple.domain.PloggingComment;
import lombok.Data;

@Data
public class MyPloggingCommentDto {
    private Long id;

    private String content;

    public MyPloggingCommentDto(PloggingComment entity) {
        this.id = entity.getId();
        this.content = entity.getContent();
    }
}
