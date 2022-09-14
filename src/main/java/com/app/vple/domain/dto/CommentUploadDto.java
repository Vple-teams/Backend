package com.app.vple.domain.dto;

import com.app.vple.domain.Comment;
import com.app.vple.domain.Post;
import com.app.vple.domain.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class CommentUploadDto {

    @NotBlank(message = "내용이 필요합니다.")
    @Size(min = 2, max = 200, message = "댓글 내용은 최소 2자부터 200자까지 가능합니다.")
    @ApiModelProperty(example = "댓글 내용")
    private String content;

    @ApiModelProperty(example = "1")
    private Long postId;

    public Comment toEntity(User user, Post post) {
        return Comment.builder()
                .content(content)
                .post(post)
                .nickname(user.getNickname())
                .commentWriter(user)
                .build();
    }
}
