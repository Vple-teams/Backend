package com.app.vple.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.app.vple.domain.AreaCode;
import com.app.vple.domain.dto.RecommandTourSpotDto;
import com.app.vple.repository.AreaCodeRepository;
import com.app.vple.service.model.TourSpot.TourSpotResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    private final GeoCodingService geoCodingService;

    private final String PREFIX_URL = "http://apis.data.go.kr/B551011/KorService/areaBasedList?serviceKey=";

    private final String PREFIX_URL2 = "http://apis.data.go.kr/B551011/KorService/searchKeyword?serviceKey=";

    private final String INORDER_URL = "&numOfRows=10&pageNo=1&MobileOS=ETC&MobileApp=VPLE&_type=json&listYN=Y&arrange=C&areaCode=";

    @Value("${encodingKey}")
    private String serviceKey;

    public RecommandTourSpotDetailDto findRecommandTourSpotDetails(String keyword, String latitude, String longitude) throws IOException {
        boolean existTourSpot = recommandTourSpotRepository.existsByNameAndLongitudeAndLatitude(keyword, longitude, latitude);

        if (!existTourSpot) {
            List<String> here = geoCodingService.findHere(Double.parseDouble(longitude), Double.parseDouble(latitude));
            String city = here.get(0); // 경기도, 서울특별시, 세종특별자치시
            String district = here.get(1); // 성남시 분당구, 강남구, ""

            String[] splitData = new String[0];
            if (!district.equals(""))
                splitData = district.split(" ");
            AreaCode result = null;
            if (city.equals("서울특별시")) {
                result = areaCodeRepository.getByCityAndDistrict("서울시", district);
            }
            else if (city.equals("제주특별자치도")) {
                result = areaCodeRepository.getByCityAndDistrict("제주도", district);
            }
            else if (splitData.length > 1) {
                result = areaCodeRepository.getByCityAndDistrict(city, splitData[0]);
            }
            else if (splitData.length == 1) {
                result = areaCodeRepository.getByCityAndDistrict(city, district);
            }
            else if (district.equals("")) {
                result = areaCodeRepository.getByCityContaining(city);
            }

            if (result == null) {
                throw new NoSuchElementException("Area info does not exist");
            }

            Long areaCode = result.getCityCode();
            Long sigunguCode = result.getDistrictCode();
            RecommandTourSpotDto tourSpot = searchByKeyword(keyword, areaCode, sigunguCode);

            RecommandTourSpot recommandTourSpot = RecommandTourSpot.builder()
                    .name(keyword)
                    .address(tourSpot.getAddr1())
                    .image(tourSpot.getFirstimage())
                    .latitude(latitude)
                    .longitude(longitude)
                    .reviews(new ArrayList<>())
                    .reviewCount(0)
                    .city(result.getCity())
                    .district(result.getDistrict())
                    .rating(0.0f)
                    .profile("")
                    .build();

            recommandTourSpotRepository.save(recommandTourSpot);
        }

        RecommandTourSpot tourSpot = recommandTourSpotRepository.getByNameAndLongitudeAndLatitude(keyword, longitude, latitude);
        return new RecommandTourSpotDetailDto(tourSpot);
    }

    public List<RecommandTourSpotDto> findTourSpotListByCityAndDistrict(String city, String district) throws IOException {
        AreaCode areaCodeInfo = areaCodeRepository.getByCityAndDistrict(city, district);
        Long areaCode = areaCodeInfo.getCityCode();
        Long sigunguCode = areaCodeInfo.getDistrictCode();

        String URL = PREFIX_URL + serviceKey + INORDER_URL + areaCode + "&contentTypeId=12&sigunguCode=" + sigunguCode;
        return callTourApi(URL);
    }

    public List<RecommandTourSpotDto> findTourSpotListByCity(String city) throws IOException {
        List<AreaCode> areaCodes = areaCodeRepository.findByCity(city);

        Long areaCode = areaCodes.get(0).getCityCode();

        String URL = PREFIX_URL + serviceKey + INORDER_URL + areaCode + "&contentTypeId=12";
        return callTourApi(URL);
    }

    private List<RecommandTourSpotDto> callTourApi(String URL) throws IOException {
        java.net.URL url = new URL(URL);

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

    private RecommandTourSpotDto searchByKeyword(String keyword, Long areaCode, Long sigunguCode) throws IOException {
        String URL = PREFIX_URL2 + serviceKey
                +"&numOfRows=10&pageNo=1&MobileOS=ETC&MobileApp=VPLE&_type=json&listYN=Y&arrange=C&areaCode="+ areaCode+"&sigunguCode="+ sigunguCode +"&keyword="
                + URLEncoder.encode(keyword, StandardCharsets.UTF_8);

        List<RecommandTourSpotDto> results = callTourApi(URL);
        if (results.isEmpty()) {
            throw new NoSuchElementException("result X");
        }

        return results.get(0);
    }
}