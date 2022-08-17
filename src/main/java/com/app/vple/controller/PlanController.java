package com.app.vple.controller;

import com.app.vple.domain.Plan;
import com.app.vple.domain.dto.MyPlansDto;
import com.app.vple.domain.dto.PlanCreateDto;
import com.app.vple.domain.dto.PlanDetailDto;
import com.app.vple.domain.dto.PlanUpdateDto;
import com.app.vple.service.PlanService;
import com.app.vple.service.PlanTravelService;
import com.app.vple.service.model.SessionLoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/plan")
public class PlanController {

    private final HttpSession httpSession;

    private final PlanService planService;

    @GetMapping
    public ResponseEntity<?> planList() {
        try {
            SessionLoginUser loginUser = (SessionLoginUser) httpSession.getAttribute("user");
            List<MyPlansDto> plans = planService.findPlan(loginUser.getEmail());
            return new ResponseEntity<>(plans, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> planDetails(@PathVariable Long id) {
        try {
            PlanDetailDto plan = planService.findPlanDetails(id);
            return new ResponseEntity<>(plan, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<?> planAdd(@Validated @RequestBody PlanCreateDto planCreateDto) {
        try {
            SessionLoginUser loginUser = (SessionLoginUser) httpSession.getAttribute("user");
            String email = loginUser.getEmail();
            Plan plan = planService.addPlan(planCreateDto, email);
            return new ResponseEntity<>(plan, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> planRemove(@PathVariable Long id) {
        try {
            String title = planService.removePlan(id);
            return new ResponseEntity<>(title + "을 삭제했습니다.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> planModify(@PathVariable Long id,
                                        @Validated @RequestBody PlanUpdateDto planUpdateDto) {
        try {
            String title = planService.modifyPlan(id, planUpdateDto);
            return new ResponseEntity<>(title + "을 수정했습니다.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
