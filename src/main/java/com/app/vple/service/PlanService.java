package com.app.vple.service;

import com.app.vple.domain.Plan;
import com.app.vple.domain.User;
import com.app.vple.domain.dto.MyPlansDto;
import com.app.vple.domain.dto.PlanCreateDto;
import com.app.vple.domain.dto.PlanDetailDto;
import com.app.vple.domain.dto.PlanUpdateDto;
import com.app.vple.repository.PlanRepository;
import com.app.vple.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PlanService {

    private final PlanRepository planRepository;

    private final UserRepository userRepository;

    public List<MyPlansDto> findPlan(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new NoSuchElementException("해당 사용자가 존재하지 않습니다.")
        );

        List<Plan> planByUser = planRepository.findPlanByUser(user);

        if(planByUser.isEmpty())
            throw new ArrayIndexOutOfBoundsException("생성한 플랜이 없습니다.");

        return planByUser.stream().map(
                MyPlansDto::new
        ).collect(Collectors.toList());
    }

    public PlanDetailDto findPlanDetails(Long planId) {
        Plan plan = planRepository.findById(planId).orElseThrow(
                () -> new NoSuchElementException("해당 플랜이 존재하지 않습니다.")
        );

        return new PlanDetailDto(plan);
    }

    public Plan addPlan(PlanCreateDto planCreateDto, String email) {
        try {
            User user = userRepository.findByEmail(email).orElseThrow(
                    () -> new NoSuchElementException("해당 사용자가 존재하지 않습니다."));
            Plan plan = planRepository.save(planCreateDto.toEntity(user));
            return plan;
        } catch (Exception e) {
            throw new IllegalStateException("형식이 잘못되었습니다.");
        }
    }

    public String removePlan(Long id) {
        Plan plan = planRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("해당 플랜이 존재하지 않습니다.")
        );

        planRepository.delete(plan);

        return plan.getTitle();
    }

    public String modifyPlan(Long id, PlanUpdateDto planUpdateDto) {
        Plan plan = planRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("해당 플랜이 존재하지 않습니다.")
        );

        plan.updatePlan(planUpdateDto);
        planRepository.save(plan);

        return plan.getTitle();
    }
}
