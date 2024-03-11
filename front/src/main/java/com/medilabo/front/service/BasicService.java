package com.medilabo.front.service;

import org.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public abstract class BasicService {

    public RestTemplate getRestTemplate(String authorization) {
        return getRestTemplate(authorization, null);
    }

    public RestTemplate getRestTemplate(String authorization, JSONObject arg) {
        return new RestTemplateBuilder()
                .interceptors( (request, body, execution) -> {
                    request.getHeaders().add("Authorization", authorization);
                    if(arg!=null) {
                        body = arg.toString().getBytes();
                        request.getHeaders().setContentLength(body.length);
                        request.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                    }
                    return execution.execute(request, body);
                }).build();
    }
}
