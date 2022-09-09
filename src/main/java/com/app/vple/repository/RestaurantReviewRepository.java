package com.app.vple.repository;

import com.app.vple.domain.RestaurantReview;
import com.app.vple.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantReviewRepository extends JpaRepository<RestaurantReview, Long> {

    RestaurantReview findFirstByReviewerOrderByCreatedDateDesc(User user);
}
