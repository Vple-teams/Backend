package com.app.vple.domain.dto;

import com.app.vple.domain.RecommandRestaurant;
import com.app.vple.domain.RestaurantReview;
import com.app.vple.domain.User;
import lombok.Data;

import java.util.List;

@Data
public class RestaurantReviewUploadDto {

    private String title;

    private String content;

    private List<String> reviewtags;

    private Long restaurantId;

    public RestaurantReview toEntity(User user, RecommandRestaurant restaurant) {
        return RestaurantReview.builder()
                .nickname(user.getNickname())
                .title(title)
                .reviewer(user)
                .restaurant(restaurant)
                .content(content)
                .build();
    }
}
