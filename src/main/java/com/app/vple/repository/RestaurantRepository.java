package com.app.vple.repository;

import com.app.vple.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    
}
