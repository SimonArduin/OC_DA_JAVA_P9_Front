package com.medilabo.front.util;

import com.medilabo.front.dto.LoginInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.Base64;

@Component
public class HeadersUtil {

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
