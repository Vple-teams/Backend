package com.app.vple.service;

import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.app.vple.domain.RecommandRestaurant;
import com.app.vple.domain.dto.RecommandRestaurantDetailDto;
import com.app.vple.repository.RecommandRestaurantRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class RecommandRestaurantService {

    private final RecommandRestaurantRepository recommandRestaurantRepository;

    public RecommandRestaurantDetailDto findRecommandRestaurantDetails(Long id) {
        RecommandRestaurant recommandRestaurant = recommandRestaurantRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("The restaurant does not exist.")
        );

        return new RecommandRestaurantDetailDto(recommandRestaurant);
    }
}
