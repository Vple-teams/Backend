package com.app.vple.controller;

import com.app.vple.domain.dto.RestaurantReviewUploadDto;
import com.app.vple.service.RestaurantReviewService;
import com.app.vple.service.model.SessionLoginUser;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
public class RestaurantReviewController {

    private final RestaurantReviewService restaurantReviewService;

    private final HttpSession httpSession;

    @ApiOperation(value = "식당 후기 추가하기")
    @PostMapping("/auth/review/restaurant")
    public ResponseEntity<?> RestaurantReviewAdd(@RequestBody RestaurantReviewUploadDto review) {
        try {
            SessionLoginUser loginUser = (SessionLoginUser) httpSession.getAttribute("user");
            restaurantReviewService.addReview(loginUser.getId(), review);
            return new ResponseEntity<>("review add success", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
