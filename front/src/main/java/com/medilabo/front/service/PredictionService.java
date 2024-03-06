package com.medilabo.front.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PredictionService {

    @Value("${prediction.url}")
    private String PREDICTION_URL;

    public String getById(Integer id, HttpHeaders headers) {
        HttpEntity request = new HttpEntity<>(null, headers);
        String prediction = new RestTemplate().exchange(PREDICTION_URL + "get?id=" + id, HttpMethod.GET, request, String.class).getBody();
        return prediction;
    }

}
