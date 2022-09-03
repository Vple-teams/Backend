package com.app.vple.controller;

import com.app.vple.domain.dto.RecommandTourSpotListDto;
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

import com.app.vple.domain.dto.RecommandTourSpotDetailDto;
import com.app.vple.service.RecommandTourSpotService;

import lombok.RequiredArgsConstructor;

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
}