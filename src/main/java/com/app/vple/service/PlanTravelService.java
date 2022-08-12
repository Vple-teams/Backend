package com.app.vple.service;

import com.app.vple.domain.Plan;
import com.app.vple.domain.PlanTravel;
import com.app.vple.domain.dto.PlanTravelAddDto;
import com.app.vple.domain.dto.PlanTravelDetailDto;
import com.app.vple.repository.PlanRepository;
import com.app.vple.repository.PlanTravelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class PlanTravelService {

    private final PlanTravelRepository planTravelRepository;

    private final PlanRepository planRepository;

    public PlanTravelDetailDto findPlanTravelDetails(Long planTravelId) {
        PlanTravel planTravel = planTravelRepository.findById(planTravelId).orElseThrow(
                () -> new NoSuchElementException("해당 여행지가 없습니다.")
        );

        return new PlanTravelDetailDto(planTravel);
    }

    public String addPlanTravel(PlanTravelAddDto addPlanTravel) {
        Long planId = addPlanTravel.getPlanId();

        Plan plan = planRepository.findById(planId).orElseThrow(
                () -> new NoSuchElementException("해당 플랜이 없습니다.")
        );

        planTravelRepository.save(addPlanTravel.toEntity(plan));

        return addPlanTravel.getName();
    }

    public String removePlanTravel(Long id) {
        PlanTravel planTravel = planTravelRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("해당 여행지가 없습니다.")
        );

        planTravelRepository.delete(planTravel);

        return planTravel.getName();
    }
}
