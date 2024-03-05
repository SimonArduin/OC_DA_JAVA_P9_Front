package com.medilabo.front.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpHeaders;

public class BasicController {

    public HttpHeaders getHeaders(HttpSession session) {
        HttpHeaders defaultHeaders = new HttpHeaders();
        try {
            HttpHeaders headers = (HttpHeaders) session.getAttribute("headers");
            if(headers==null || headers.isEmpty()) {
                return defaultHeaders;
            }
            return headers;
        }
        catch (Exception e) {
            return defaultHeaders;
        }
    }

    public String redirectToLogin() {
        return "redirect:../login";
    }

}
