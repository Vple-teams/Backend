package com.app.vple.service;

import com.app.vple.domain.Restaurant;
import com.app.vple.domain.dto.MapRestaurantListDto;
import com.app.vple.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public List<MapRestaurantListDto> findMapRestaurant() {
        List<Restaurant> restaurants = restaurantRepository.findAll();

        return restaurants.stream().map(
                MapRestaurantListDto::new
        ).collect(Collectors.toList());
    }
}
