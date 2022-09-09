package com.app.vple.domain.dto;

import com.app.vple.domain.RecommandTourSpot;
import com.app.vple.domain.TourSpotReview;
import com.app.vple.domain.User;
import lombok.Data;

import java.util.List;

@Data
public class TourSpotReviewUploadDto {

    private String title;

    private String content;

    private List<String> reviewtags;

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
