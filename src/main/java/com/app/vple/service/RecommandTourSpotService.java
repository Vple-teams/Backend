package com.app.vple.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.app.vple.domain.AreaCode;
import com.app.vple.domain.dto.RecommandTourSpotDto;
import com.app.vple.repository.AreaCodeRepository;
import com.app.vple.service.model.Geocoding.Root;
import com.app.vple.service.model.TourSpot.TourSpotResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.shaded.json.JSONArray;
import com.nimbusds.jose.shaded.json.JSONObject;
import com.nimbusds.jose.shaded.json.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
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

    private final AreaCodeRepository areaCodeRepository;

    private final String PREFIX_URL = "http://apis.data.go.kr/B551011/KorService/areaBasedList?serviceKey=";

    @Value("${encodingKey}")
    private String serviceKey;

    public RecommandTourSpotDetailDto findRecommandTourSpotDetails(Long id) {
        RecommandTourSpot recommandTourSpot = recommandTourSpotRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("The restaurant does not exist.")
        );

        return new RecommandTourSpotDetailDto(recommandTourSpot);
    }

    public List<RecommandTourSpotDto> findTourSpotListByCityAndDistrict(String city, String district) throws IOException {
        AreaCode areaCodeInfo = areaCodeRepository.getByCityAndDistrict(city, district);
        Long areaCode = areaCodeInfo.getCityCode();
        Long sigunguCode = areaCodeInfo.getDistrictCode();

        String URL = PREFIX_URL + serviceKey + "&numOfRows=20&pageNo=1&MobileOS=ETC&MobileApp=VPLE&_type=json&listYN=Y&arrange=C&contentTypeId=15&areaCode=" + areaCode + "&sigunguCode=" + sigunguCode;
        URL url = new URL(URL);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }

        rd.close();
        conn.disconnect();

        ObjectMapper objectMapper = new ObjectMapper();
        TourSpotResponseDto response = objectMapper.readValue(sb.toString(), TourSpotResponseDto.class);

        return response.getResponse().getBody().getItems().getItem().stream()
                .map(RecommandTourSpotDto::new)
                .collect(Collectors.toList());
    }
}