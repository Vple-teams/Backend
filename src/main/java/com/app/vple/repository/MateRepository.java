package com.app.vple.repository;

import com.app.vple.domain.Mate;
import com.app.vple.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MateRepository extends JpaRepository<Mate, Long> {

    Optional<Mate> findByUser(User user);

    boolean existsByUser(User user);

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM mates WHERE ST_Distance_Sphere(POINT(:longitude, :latitude), POINT(longitude, latitude)) <= 500 AND user_user_id != :id"
    )
    List<Mate> findAllNearByMe(@Param("longitude") Double longitude, @Param("latitude") Double latitude, @Param("id") Long id);
}
