package com.app.vple.repository;

import com.app.vple.domain.TourSpotReivewTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourSpotReviewTagRepository extends JpaRepository<TourSpotReivewTag, Long> {
}
