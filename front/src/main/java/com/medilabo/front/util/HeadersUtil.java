package com.medilabo.front.util;

import com.medilabo.front.dto.LoginInfoDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.Base64;

@Component
public class HeadersUtil {

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

    public HttpHeaders login(LoginInfoDto loginInfoDto) {

        // encode login info
        String username = loginInfoDto.getUsername();
        String password = loginInfoDto.getPassword();
        String auth = username + ":" + password;
        byte[] encodedAuth = Base64.getEncoder().encode(
                auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + new String(encodedAuth);

        // create headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", authHeader);

        return headers;
    }
}
