package com.medilabo.front.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

public abstract class BasicController {

    public String redirectToLogin() {
        return "redirect:../login";
    }

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

}
