package com.app.vple.controller;

import com.app.vple.domain.dto.RecommandTourSpotDto;
import com.app.vple.service.GeoCodingService;
import com.app.vple.service.model.Geocoding.Results;
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

    @GetMapping("/details")
    public ResponseEntity<?> recommandTourSpotDetails(@RequestParam("keyword") String keyword,
                                                      @RequestParam("latitude") String latitude,
                                                      @RequestParam("longitude") String longitude) {
        try {
            RecommandTourSpotDetailDto tourspot = recommandTourSpotService.findRecommandTourSpotDetails(keyword, latitude, longitude);
            return new ResponseEntity<>(tourspot, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<?> findTourSpotList(@RequestParam String city, @RequestParam String district, @RequestParam(defaultValue = "1") String pageNo) {
        try {
            List<RecommandTourSpotDto> results = recommandTourSpotService.findTourSpotListByCityAndDistrict(city, district, pageNo);
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("tour api 호출에 실패했습니다.", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> findTourSpotList(@RequestParam String city, @RequestParam(defaultValue = "1") String pageNo) {
        try {
            List<RecommandTourSpotDto> results = recommandTourSpotService.findTourSpotListByCity(city, pageNo);
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("tour api 호출에 실패했습니다.", HttpStatus.BAD_REQUEST);
        }
    }

}