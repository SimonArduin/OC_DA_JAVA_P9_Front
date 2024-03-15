package com.medilabo.front.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

public abstract class BasicController {

    /**
     * This method handles exceptions that can be thrown when calling the other microservices.
     * On an UNAUTHORIZED exception, the user is redirected to the login page.
     * On a FORBIDDEN exception, the user is shown the "forbidden" page.
     * All other exceptions display the basic "error" page.
     * @param exception A HttpClientErrorException thrown by the application
     * @return A String corresponding to a Thymeleaf template
     */
    @ExceptionHandler(HttpClientErrorException.class)
    public String ExceptionHandler(HttpClientErrorException exception) {
        if(exception.getStatusCode() == HttpStatus.UNAUTHORIZED)
                return "redirect:../login";
        if(exception.getStatusCode() == HttpStatus.FORBIDDEN)
            return "forbidden";
        return "error";
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
