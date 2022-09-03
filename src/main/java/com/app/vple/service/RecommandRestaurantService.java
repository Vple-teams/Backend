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
}
