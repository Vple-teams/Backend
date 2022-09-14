package com.app.vple.domain.dto;

import com.app.vple.domain.RecommandTourSpot;
import com.app.vple.domain.TourSpotReview;
import com.app.vple.domain.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class TourSpotReviewUploadDto {

    @ApiModelProperty(example = "관광지 후기 제목")
    private String title;

    @ApiModelProperty(example = "관광지 후기 내용 html")
    private String content;

    @ApiModelProperty(example = "[\"볼 거리가 많아요\", \"깨끗해요\", \"친절해요\"]")
    private List<String> reviewtags;

    @ApiModelProperty(example = "1")
    private Long tourspotId;

    public TourSpotReview toEntity(User user, RecommandTourSpot tourSpot) {
        return TourSpotReview.builder()
                .nickname(user.getNickname())
                .title(title)
                .reviewer(user)
                .tourSpot(tourSpot)
                .content(content)
                .build();
    }
}
