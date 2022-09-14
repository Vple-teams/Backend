package com.app.vple.domain.dto;

import com.app.vple.domain.Post;
import com.app.vple.domain.RecommandRestaurant;
import com.app.vple.domain.RecommandTourSpot;
import com.app.vple.domain.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class PostUploadDto {

    @NotBlank(message = "제목이 필요합니다.")
    @Size(min = 2, max = 20, message = "제목의 길이는 최소 2부터 최대 20까지입니다.")
    @ApiModelProperty(example = "게시글 제목")
    private String title;

    @ApiModelProperty(example = "게시글 내용")
    @NotNull(message = "본문이 비어있습니다.")
    @Size(min = 2)
    private String html;

    @ApiModelProperty(example = "게시글 카테고리 0: 일반, 1: 후기")
    @NotNull(message = "게시글의 카테고리가 필요합니다.")
    private boolean isReviewPost;

    @ApiModelProperty(example = "게시글 해시태그 리스트로 받음")
    private List<String> hashtag;

    public Post toEntity(User user) {
        return Post.builder()
                .title(title)
                .html(html)
                .likesCount(0)
                .views(0)
                .isReviewPost(isReviewPost)
                .nickname(user.getNickname())
                .postWriter(user)
                .build();


    }
}
