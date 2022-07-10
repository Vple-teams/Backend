package com.app.vple.domain.dto;

import com.app.vple.domain.Post;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostListDto {

    private Long id;

    private String title;

    private String nickname;

    private LocalDateTime createdDate;

    private Integer commentCount;

    private Integer likeCount;

    private boolean isReviewPost;

    private Integer views;

    public PostListDto(Post entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.nickname = entity.getNickname();
        this.likeCount = entity.getLikesCount();
        this.createdDate = entity.getCreatedDate();
        this.commentCount = entity.getCommentCount();
        this.isReviewPost = entity.isReviewPost();
        this.views = entity.getViews();
    }
}
