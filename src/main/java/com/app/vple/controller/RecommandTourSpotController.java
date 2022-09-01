package com.app.vple.controller;

import com.app.vple.domain.dto.RecommandTourSpotDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import com.app.vple.domain.dto.RecommandTourSpotDetailDto;
import com.app.vple.service.RecommandTourSpotService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recommand/tourspot")
public class RecommandTourSpotController {

    private final RecommandTourSpotService recommandTourSpotService;

    @GetMapping("/{id}")
    public ResponseEntity<?> recommandTourSpotDetails(@PathVariable Long id) {
        try {
            RecommandTourSpotDetailDto recommandTourSpot = recommandTourSpotService.findRecommandTourSpotDetails(id);
            return new ResponseEntity<>(recommandTourSpot, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<?> findTourSpotList(@RequestParam String city, @RequestParam String district) {
        try {
            List<RecommandTourSpotDto> results = recommandTourSpotService.findTourSpotListByCityAndDistrict(city, district);
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }
}