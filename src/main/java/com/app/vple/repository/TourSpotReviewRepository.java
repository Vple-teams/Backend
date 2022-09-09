package com.app.vple.repository;

import com.app.vple.domain.TourSpotReview;
import com.app.vple.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourSpotReviewRepository extends JpaRepository<TourSpotReview, Long> {

    TourSpotReview findFirstByReviewerOrderByCreatedDateDesc(User user);
}
