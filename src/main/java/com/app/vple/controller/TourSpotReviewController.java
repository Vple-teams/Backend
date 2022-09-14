package com.app.vple.controller;

import com.app.vple.domain.dto.TourSpotReviewUploadDto;
import com.app.vple.service.TourSpotReviewService;
import com.app.vple.service.model.SessionLoginUser;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class TourSpotReviewController {

    private final TourSpotReviewService tourSpotReviewService;

    private final HttpSession httpSession;

    @ApiOperation(value = "관광지 후기 추가하기")
    @PostMapping("/auth/review/tourspot")
    public ResponseEntity<?> tourSpotReviewAdd(@RequestBody TourSpotReviewUploadDto review) {
        try {
            SessionLoginUser loginUser = (SessionLoginUser) httpSession.getAttribute("user");
            tourSpotReviewService.addReview(loginUser.getId(), review);
            return new ResponseEntity<>("review add success", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
