package com.app.vple.repository;

import com.app.vple.domain.RecommandTourSpot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.app.vple.domain.User;

import java.util.List;

public interface RecommandTourSpotRepository extends JpaRepository<RecommandTourSpot, Long> {

    RecommandTourSpot findById(User user);

    Page<RecommandTourSpot> findAll(Pageable pageable);

    boolean existsByNameAndLongitudeAndLatitude(String name, String longitude, String latitude);

    RecommandTourSpot getByNameAndLongitudeAndLatitude(String name, String lon, String lat);

}
