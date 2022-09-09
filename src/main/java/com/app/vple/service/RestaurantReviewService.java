package com.app.vple.service;

import com.app.vple.domain.RecommandRestaurant;
import com.app.vple.domain.RestaurantReview;
import com.app.vple.domain.RestaurantReviewTag;
import com.app.vple.domain.User;
import com.app.vple.domain.dto.RestaurantReviewUploadDto;
import com.app.vple.repository.RecommandRestaurantRepository;
import com.app.vple.repository.RestaurantReviewRepository;
import com.app.vple.repository.RestaurantReviewTagRepository;
import com.app.vple.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class RestaurantReviewService {

    private final RecommandRestaurantRepository recommandRestaurantRepository;

    private final RestaurantReviewRepository restaurantReviewRepository;

    private final UserRepository userRepository;

    private final RestaurantReviewTagRepository restaurantReviewTagRepository;

    @Transactional
    public void addReview(Long id, RestaurantReviewUploadDto reviewUploadDto) {
        User me = userRepository.getById(id);

        RecommandRestaurant restaurant = recommandRestaurantRepository.findById(reviewUploadDto.getRestaurantId())
                .orElseThrow( () -> new NoSuchElementException("restaurant X"));

        restaurantReviewRepository.save(reviewUploadDto.toEntity(me, restaurant));
        RestaurantReview recentReview = restaurantReviewRepository.findFirstByReviewerOrderByCreatedDateDesc(me);

        for (String tag : reviewUploadDto.getReviewtags()) {
            restaurantReviewTagRepository.save(new RestaurantReviewTag(recentReview, tag));
        }
    }
}
