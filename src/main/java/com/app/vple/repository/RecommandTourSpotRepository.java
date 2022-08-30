package com.app.vple.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.vple.domain.RecommandTourSpot;
import com.app.vple.domain.User;

public interface RecommandTourSpotRepository extends JpaRepository<RecommandTourSpot, Long> {

    RecommandTourSpot findById(User user);
}
