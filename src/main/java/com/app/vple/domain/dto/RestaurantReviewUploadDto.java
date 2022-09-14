package com.app.vple.domain.dto;

import com.app.vple.domain.RecommandRestaurant;
import com.app.vple.domain.RestaurantReview;
import com.app.vple.domain.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class RestaurantReviewUploadDto {

    @ApiModelProperty(example = "식당 후기 제목")
    private String title;

    @ApiModelProperty(example = "식당 후기 내용 html")
    private String content;

    @ApiModelProperty(example = "[\"음식이 맛있어요\", \"재료가 신선해요\", \"친절해요\"]")
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
