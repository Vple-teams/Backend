package com.app.vple.domain.dto;

import com.app.vple.domain.Post;
import com.app.vple.domain.RecommandRestaurant;
import com.app.vple.domain.RecommandTourSpot;
import com.app.vple.domain.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class PostUploadDto {

    @NotBlank(message = "제목이 필요합니다.")
    @Size(min = 2, max = 20, message = "제목의 길이는 최소 2부터 최대 20까지입니다.")
    private String title;

    @NotNull(message = "본문이 비어있습니다.")
    @Size(min = 2)
    private String html;

    @NotNull(message = "게시글의 카테고리가 필요합니다.")
    private boolean isReviewPost;

    private List<String> hashtag;

    private Long restaurantId;

    private Long tourspotId;

    public Post toEntity(User user, RecommandRestaurant restaurant, RecommandTourSpot tourSpot) {

        if (restaurant != null)
            return Post.builder()
                    .title(title)
                    .html(html)
                    .likesCount(0)
                    .restaurant(restaurant)
                    .views(0)
                    .isReviewPost(isReviewPost)
                    .nickname(user.getNickname())
                    .postWriter(user)
                    .build();

        else
            return Post.builder()
                    .title(title)
                    .html(html)
                    .likesCount(0)
                    .tourSpot(tourSpot)
                    .views(0)
                    .isReviewPost(isReviewPost)
                    .nickname(user.getNickname())
                    .postWriter(user)
                    .build();
    }
}
