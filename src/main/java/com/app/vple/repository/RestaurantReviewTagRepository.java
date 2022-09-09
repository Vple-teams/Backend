package com.app.vple.repository;

import com.app.vple.domain.RestaurantReviewTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantReviewTagRepository extends JpaRepository<RestaurantReviewTag, Long> {
}
