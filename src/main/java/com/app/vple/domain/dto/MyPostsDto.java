package com.app.vple.domain.dto;

import com.app.vple.domain.Post;
import lombok.Data;

@Data
public class MyPostsDto {

    private Long id;

    private String title;

    public MyPostsDto(Post entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
    }
}