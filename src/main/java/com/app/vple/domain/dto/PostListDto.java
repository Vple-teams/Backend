package com.app.vple.domain.dto;

import com.app.vple.domain.Post;
import com.app.vple.domain.PostReview;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class PostListDto {

    private Long id;

    private String title;

    private String nickname;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime createdDate;

    private Integer commentCount;

    private Integer likeCount;

    private boolean isReviewPost;

    private Integer views;

    private List<String> hashtags;

    public PostListDto(Post entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.nickname = entity.getNickname();
        this.likeCount = entity.getLikesCount();
        this.createdDate = entity.getCreatedDate();
        this.commentCount = entity.getCommentCount();
        this.isReviewPost = entity.isReviewPost();
        this.views = entity.getViews();
        if (isReviewPost) {
            this.hashtags = entity.getPostReview().stream().map(
                    PostReview::getHashTag
            ).collect(Collectors.toList());
        }
    }
}
