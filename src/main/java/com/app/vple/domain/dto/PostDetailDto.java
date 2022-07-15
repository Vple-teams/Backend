package com.app.vple.domain.dto;

import com.app.vple.domain.Post;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class PostDetailDto {

    private String title;

    private String html;

    private String nickname;

    private Integer likeCount;

    private Integer commentCount;

    private boolean isReviewPost;

    private List<CommentDto> comments;

    private LocalDateTime createdDate;

    private Integer views;

    public PostDetailDto(Post entity) {
        this.commentCount = entity.getCommentCount();
        this.title = entity.getTitle();
        this.html = entity.getHtml();
        this.nickname = entity.getNickname();
        this.likeCount = entity.getLikesCount();
        this.isReviewPost = entity.isReviewPost();
        this.comments = entity.getComments().stream().map(
                CommentDto::new
        ).collect(Collectors.toList());
        this.createdDate = entity.getCreatedDate();
        this.views = entity.getViews();
    }
}
