package com.app.vple.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.app.vple.domain.RecommandTourSpot;
import com.app.vple.domain.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.app.vple.repository.RecommandTourSpotRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class RecommandTourSpotService {

    private final RecommandTourSpotRepository recommandTourSpotRepository;

    public Page<RecommandTourSpotListDto> findRecommandTourSpotList(Pageable pageable) {
        Page<RecommandTourSpot> recommandTourSpots = recommandTourSpotRepository.findAll(pageable);

        return recommandTourSpots.map(RecommandTourSpotListDto::new);
    }

    public RecommandTourSpotDetailDto findRecommandTourSpotDetails(Long id) {
        RecommandTourSpot recommandTourSpot = recommandTourSpotRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("The restaurant does not exist.")
        );

        return new RecommandTourSpotDetailDto(recommandTourSpot);
    }

    public List<MapTourSpotListDto> findMapTourSpot() {
        List<RecommandTourSpot> recommandTourSpots = recommandTourSpotRepository.findAll();

        return recommandTourSpots.stream().map(
                MapTourSpotListDto::new
        ).collect(Collectors.toList());
    }

    public List<MapTourSpotListDto> searchMapTourSpot(String keyword) {
        List<RecommandTourSpot> recommandTourSpots = recommandTourSpotRepository.findByNameContaining(keyword);
        recommandTourSpots.addAll(recommandTourSpotRepository.findByAddressContaining(keyword));

        if (recommandTourSpots.isEmpty()) {
            throw new NoSuchElementException("해당 식당이 존재하지 않습니다.");
        }

        return recommandTourSpots.stream().map(
                MapTourSpotListDto::new
        ).collect(Collectors.toList());
    }
}
