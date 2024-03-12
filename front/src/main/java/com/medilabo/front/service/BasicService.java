package com.medilabo.front.service;

import org.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public abstract class BasicService {

    /**
     * This method creates a RestTemplate,
     * with headers containing the given authorization credentials,
     * and body containing the given json object.
     * @param authorization
     * @param jsonObject
     * @return A RestTemplate
     */
    public RestTemplate getRestTemplate(String authorization, JSONObject jsonObject) {
        return new RestTemplateBuilder()
                .interceptors( (request, body, execution) -> {
                    request.getHeaders().add("Authorization", authorization);
                    if(jsonObject!=null) {
                        body = jsonObject.toString().getBytes();
                        request.getHeaders().setContentLength(body.length);
                        request.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                    }
                    return execution.execute(request, body);
                }).build();
    }

    public RestTemplate getRestTemplate(String authorization) {
        return getRestTemplate(authorization, null);
    }
}
