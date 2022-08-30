package com.app.vple.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.vple.domain.RecommandRestaurant;
import com.app.vple.domain.User;

public interface RecommandRestaurantRepository extends JpaRepository<RecommandRestaurant, Long> {

    RecommandRestaurant findById(User user);
}
