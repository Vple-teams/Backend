package com.app.vple.repository;

import com.app.vple.domain.CheckDuplicatedPlanLike;
import com.app.vple.domain.Plan;
import com.app.vple.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CheckDuplicatedPlanLikeRepository extends JpaRepository<CheckDuplicatedPlanLike, Long> {

    Optional<CheckDuplicatedPlanLike> findByUserAndPlan(User user, Plan plan);
}
