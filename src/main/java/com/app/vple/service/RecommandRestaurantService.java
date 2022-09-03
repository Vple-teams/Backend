package com.app.vple.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.app.vple.domain.RecommandRestaurant;
import com.app.vple.domain.dto.MapRestaurantListDto;
import com.app.vple.domain.dto.RecommandRestaurantListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.app.vple.domain.dto.RecommandRestaurantDetailDto;
import com.app.vple.repository.RecommandRestaurantRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class RecommandRestaurantService {

    private final RecommandRestaurantRepository recommandRestaurantRepository;

    public Page<RecommandRestaurantListDto> findRecommandRestaurnatList(Pageable pageable) {
        Page<RecommandRestaurant> recommandRestaurants = recommandRestaurantRepository.findAll(pageable);

        return recommandRestaurants.map(RecommandRestaurantListDto::new);
    }

    public RecommandRestaurantDetailDto findRecommandRestaurantDetails(Long id) {
        RecommandRestaurant recommandRestaurant = recommandRestaurantRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("The restaurant does not exist.")
        );

        return new RecommandRestaurantDetailDto(recommandRestaurant);
    }

    public List<MapRestaurantListDto> findMapRestaurant() {
        List<RecommandRestaurant> recommandRestaurants = recommandRestaurantRepository.findAll();

        return recommandRestaurants.stream().map(
                MapRestaurantListDto::new
        ).collect(Collectors.toList());
    }

    public List<MapRestaurantListDto> searchMapRestaurnat(String keyword) {
        List<RecommandRestaurant> recommandRestaurants = recommandRestaurantRepository.findByNameContaining(keyword);
        recommandRestaurants.addAll(recommandRestaurantRepository.findByAddressContaining(keyword));

        if (recommandRestaurants.isEmpty()) {
            throw new NoSuchElementException("해당 식당이 존재하지 않습니다.");
        }

        return recommandRestaurants.stream().map(
                MapRestaurantListDto::new
        ).collect(Collectors.toList());
    }
}
