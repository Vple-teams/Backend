package com.app.vple.controller;

import com.app.vple.domain.dto.PlanTravelAddDto;
import com.app.vple.domain.dto.PlanTravelDetailDto;
import com.app.vple.service.PlanTravelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/plan_travel")
public class PlanTravelController {

    private final PlanTravelService planTravelService;

    @GetMapping("/{id}")
    public ResponseEntity<?> planTravelDetails(@PathVariable Long id) {
        try {
            PlanTravelDetailDto planTravel = planTravelService.findPlanTravelDetails(id);
            return new ResponseEntity<>(planTravel, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<?> planTravelAdd(@Validated @RequestBody PlanTravelAddDto planTravelAddDto) {
        try {
            String planTravel = planTravelService.addPlanTravel(planTravelAddDto);
            return new ResponseEntity<>(planTravel + " 등록완료", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> planTravelRemove(@PathVariable Long id) {
        try {
            String planTravel = planTravelService.removePlanTravel(id);
            return new ResponseEntity<>(planTravel + " 삭제완료", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
