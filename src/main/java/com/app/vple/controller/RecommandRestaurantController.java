package com.app.vple.controller;

import com.app.vple.domain.dto.RecommandRestaurantListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.vple.domain.dto.RecommandRestaurantDetailDto;
import com.app.vple.service.RecommandRestaurantService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recommand/restaurant")
public class RecommandRestaurantController {

    private final RecommandRestaurantService recommandRestaurantService;

    private final int PAGE_SIZE = 20;

    @GetMapping
    public ResponseEntity<?> recommandRestaurantList(
            @PageableDefault(size = PAGE_SIZE, sort = "rating", direction = Sort.Direction.DESC)Pageable pageable) {
        try {
            Page<RecommandRestaurantListDto> recommandRestaurants = recommandRestaurantService.findRecommandRestaurnatList(pageable);
            return new ResponseEntity<>(recommandRestaurants, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> recommandRestaurantDetails(@PathVariable Long id) {
        try {
            RecommandRestaurantDetailDto recommandRestaurant = recommandRestaurantService.findRecommandRestaurantDetails(id);
            return new ResponseEntity<>(recommandRestaurant, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}