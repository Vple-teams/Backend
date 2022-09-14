package com.app.vple.domain.dto;

import com.app.vple.domain.Comment;
import com.app.vple.domain.Post;
import com.app.vple.domain.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CommentUpdateDto {

    @ApiModelProperty(example = "댓글 수정한 새로운 내용")
    private String content;

    @ApiModelProperty(example = "1")
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
