package com.medilabo.front.util;

import com.medilabo.front.dto.LoginInfoDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.charset.Charset;
import java.util.Base64;
import java.util.List;

@Component
public class HeadersUtil {

    /**
     * This method extracts the authorization attribute from the headers of a session.
     * @param session
     * @return an authorization token in the form of a String
     * @throws HttpClientErrorException
     */
    public String getAuthentication(HttpSession session) throws HttpClientErrorException {

        try {
            HttpHeaders headers = (HttpHeaders) session.getAttribute("headers");
            if(headers!=null && !headers.isEmpty()) {
                List<String> authorizationValues = headers.getValuesAsList("Authorization");
                if(!authorizationValues.isEmpty()) {
                    return authorizationValues.get(0);
                }
            }
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        }
        catch (Exception e) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * This method encodes a username and a password into a single string,
     * then creates a HttpHeaders with a Authorization attribute of this string.
     * @param loginInfoDto
     * @return HttpHeaders
     */
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
