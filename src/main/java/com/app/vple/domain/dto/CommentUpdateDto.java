package com.app.vple.domain.dto;

import com.app.vple.domain.Comment;
import com.app.vple.domain.Post;
import com.app.vple.domain.User;
import lombok.Data;

@Data
public class CommentUpdateDto {

    private String content;

    private Long postId;

    public Comment toEntity(User user, Post post) {
        return Comment.builder()
                .commentWriter(user)
                .nickname(user.getNickname())
                .post(post)
                .content(content)
                .build();
    }
}
