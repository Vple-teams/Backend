package com.app.vple.domain.dto;

import com.app.vple.domain.Comment;
import com.app.vple.domain.Post;
import lombok.Data;

@Data
public class MyCommentsDto {

    private Long id;

    private String content;

    public MyCommentsDto(Comment entity) {
        this.id = entity.getId();
        this.content = entity.getContent();
    }
}
