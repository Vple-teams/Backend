package com.app.vple.controller;

import com.app.vple.domain.dto.MapRestaurantListDto;
import com.app.vple.domain.dto.MapTourSpotListDto;
import com.app.vple.service.RecommandRestaurantService;
import com.app.vple.service.RecommandTourSpotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/map")
public class MapController {

    private final RecommandRestaurantService restaurantService;

    private final RecommandTourSpotService tourSpotService;

    @GetMapping("/restaurant")
    public ResponseEntity<?> MapRestaurantList() {
        try {
            List<MapRestaurantListDto> restaurants = restaurantService.findMapRestaurant();
            return new ResponseEntity<>(restaurants, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> MapSearch(@RequestParam(name = "keyword") String keyword) {
        try {
            List<MapRestaurantListDto> restaurants = restaurantService.searchMapRestaurnat(keyword);
            return new ResponseEntity<>(restaurants, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
