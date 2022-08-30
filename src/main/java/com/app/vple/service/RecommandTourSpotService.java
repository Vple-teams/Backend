package com.app.vple.service;

import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.app.vple.domain.RecommandTourSpot;
import com.app.vple.domain.dto.RecommandTourSpotDetailDto;
import com.app.vple.repository.RecommandTourSpotRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class RecommandTourSpotService {

    private final RecommandTourSpotRepository recommandTourSpotRepository;

    public RecommandTourSpotDetailDto findRecommandTourSpotDetails(Long id) {
        RecommandTourSpot recommandTourSpot = recommandTourSpotRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("The restaurant does not exist.")
        );

        return new RecommandTourSpotDetailDto(recommandTourSpot);
    }
}
