package com.app.vple.service;

import com.app.vple.service.model.Geocoding.Root;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
@RequiredArgsConstructor
public class GeoCodingService {

    @Value("${client_id}")
    private String clientId;

    @Value("${client_secret}")
    private String clientSecret;

    private final String URL_PREFIX = "https://naveropenapi.apigw.ntruss.com/map-reversegeocode/v2/gc?";

    private final String URL_POSTFIX = "&orders=legalcode&output=json";

    public List<String> findHere(String longitude, String latitude) throws IOException {

        String result = URL_PREFIX + "coords=" + longitude + "," + latitude + URL_POSTFIX;
        URL url = new URL(result);

        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setRequestMethod("GET");

        httpConn.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
        httpConn.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);

        InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                ? httpConn.getInputStream()
                : httpConn.getErrorStream();

        Scanner s = new Scanner(responseStream).useDelimiter("\\A");
        String responseString = s.hasNext() ? s.next() : "";

        ObjectMapper objectMapper = new ObjectMapper();
        Root response = objectMapper.readValue(responseString, Root.class);

        List<String> address = new ArrayList<>();
        address.add(response.results.get(0).region.area2.name); // 성남시 분당구
        address.add(response.results.get(0).region.area3.name); // 정자동
        return address;
    }
}
