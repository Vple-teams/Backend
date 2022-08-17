package com.app.vple.repository;

import com.app.vple.domain.Plan;
import com.app.vple.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanRepository extends JpaRepository<Plan, Long> {

    List<Plan> findPlanByUser(User user);
}
