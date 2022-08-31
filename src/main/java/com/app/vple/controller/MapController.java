package com.app.vple.controller;

import com.app.vple.domain.dto.MapRestaurantListDto;
import com.app.vple.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/map")
public class MapController {

    private final RestaurantService restaurantService;

    @GetMapping("/restaurant")
    public ResponseEntity<?> MapRestaurantList() {
        try {
            List<MapRestaurantListDto> restaurants = restaurantService.findMapRestaurant();
            return new ResponseEntity<>(restaurants, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
