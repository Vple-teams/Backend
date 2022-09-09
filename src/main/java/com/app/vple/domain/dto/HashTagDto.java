package com.app.vple.domain.dto;

import com.app.vple.domain.RestaurantReview;
import com.app.vple.domain.RestaurantReviewTag;
import com.app.vple.domain.TourSpotReivewTag;
import com.app.vple.domain.TourSpotReview;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class HashTagDto {

    Map<String, Long> hashtags = new HashMap<>();

    public HashTagDto() {}

    public HashTagDto(List<RestaurantReview> entity, boolean flag) {
        for (RestaurantReview review : entity) {
            for (RestaurantReviewTag tag: review.getReviewTags()) {
                if (hashtags.containsKey(tag.getReviewTag())) {
                    Long cnt = hashtags.get(tag.getReviewTag());
                    hashtags.put(tag.getReviewTag(), cnt + 1L);
                }
                else {
                    hashtags.put(tag.getReviewTag(), 1L);
                }
            }
        }
    }

    public HashTagDto(int x, List<TourSpotReview> entity) {
        for (TourSpotReview review : entity) {
            for (TourSpotReivewTag tag: review.getReivewTags()) {
                if (hashtags.containsKey(tag.getReviewTag())) {
                    Long cnt = hashtags.get(tag.getReviewTag());
                    hashtags.put(tag.getReviewTag(), cnt + 1L);
                }
                else {
                    hashtags.put(tag.getReviewTag(), 1L);
                }
            }
        }
    }
}
