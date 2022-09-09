package com.app.vple.service;

import com.app.vple.domain.RecommandTourSpot;
import com.app.vple.domain.TourSpotReivewTag;
import com.app.vple.domain.TourSpotReview;
import com.app.vple.domain.User;
import com.app.vple.domain.dto.TourSpotReviewUploadDto;
import com.app.vple.repository.RecommandTourSpotRepository;
import com.app.vple.repository.TourSpotReviewRepository;
import com.app.vple.repository.TourSpotReviewTagRepository;
import com.app.vple.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TourSpotReviewService {

    private final RecommandTourSpotRepository recommandTourSpotRepository;

    private final TourSpotReviewRepository tourSpotReviewRepository;

    private final TourSpotReviewTagRepository tourSpotReviewTagRepository;

    private final UserRepository userRepository;

    @Transactional
    public void addReview(Long id, TourSpotReviewUploadDto reivewUploadDto) {
        User me = userRepository.getById(id);

        RecommandTourSpot tourSpot = recommandTourSpotRepository.findById(reivewUploadDto.getTourspotId())
                .orElseThrow( () -> new NoSuchElementException("tour spot X"));

        tourSpotReviewRepository.save(reivewUploadDto.toEntity(me, tourSpot));
        TourSpotReview recentReview = tourSpotReviewRepository.findFirstByReviewerOrderByCreatedDateDesc(me);

        for (String tag : reivewUploadDto.getReviewtags()) {
            tourSpotReviewTagRepository.save(new TourSpotReivewTag(recentReview, tag));
        }
    }

}
