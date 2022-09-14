package com.app.vple.controller;

import com.app.vple.domain.dto.RecommandTourSpotDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @ApiOperation(value = "추천 관광지 상세보기, 디비에 저장되어 있지 않아서 값 필요")
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

    @ApiOperation(value = "시 & 구 / 시 & 동으로 해당 지역 관광지 보기")
    @GetMapping
    public ResponseEntity<?> findTourSpotList(@RequestParam String city, @RequestParam String district, @RequestParam(defaultValue = "1") String pageNo) {
        try {
            List<RecommandTourSpotDto> results = recommandTourSpotService.findTourSpotListByCityAndDistrict(city, district, pageNo);
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("tour api 호출에 실패했습니다.", HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "해당 시의 관광지 모두 보기")
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