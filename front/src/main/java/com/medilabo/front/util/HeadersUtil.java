package com.medilabo.front.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.Base64;

@Component
public class HeadersUtil {
    private static final HttpHeaders headers = new HttpHeaders();

    @Autowired
    public HeadersUtil() {
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    public static void login(String username, String password) {
        String auth = username + ":" + password;
        byte[] encodedAuth = Base64.getEncoder().encode(
                auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + new String(encodedAuth);
        headers.set("Authorization", authHeader);
    }

    public static void logout() {
        headers.set("Authorization", null);
    }

    public HttpHeaders getHeaders() {
        return headers;
    }
}
