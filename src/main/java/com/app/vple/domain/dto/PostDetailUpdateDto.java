package com.app.vple.domain.dto;


import com.app.vple.domain.HashTag;
import com.app.vple.domain.Post;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 게시글 수정시 기존에 작성했던 내용 불러오기
 */
@Data
public class PostDetailUpdateDto {

    private String title;

    private String html;

    private boolean isReviewPost;

    private List<String> hashTags;


    public PostDetailUpdateDto(Post entity) {
        this.title = entity.getTitle();
        this.html = entity.getHtml();
        this.isReviewPost = entity.isReviewPost();
        this.hashTags = entity.getHashTags().stream().map(
                HashTag::getHashTag
        ).collect(Collectors.toList());
    }
}
