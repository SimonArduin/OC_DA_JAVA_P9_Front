package com.medilabo.front.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PredictionService extends BasicService {

    @Value("${prediction.url}")
    private String PREDICTION_URL;

    public String getById(Integer id, String auth) {
        String prediction = getRestTemplate(auth).getForEntity(PREDICTION_URL + "get?id=" + id, String.class).getBody();
        return prediction;
    }

}
