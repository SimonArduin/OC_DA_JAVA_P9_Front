package com.medilabo.front.service;

import com.medilabo.front.dto.LoginInfoDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;

@Service
public class LoginService {

    /**
     * This method adds a header to a session and updates the number of login attempts.
     * The header has authentication information from loginInfoDto,
     * and its contentType is set to json.
     * @param loginInfoDto
     * @param session
     */
    public void login(LoginInfoDto loginInfoDto, HttpSession session) {

        String username = loginInfoDto.getUsername();
        String password = loginInfoDto.getPassword();
        HttpHeaders headers = new HttpHeaders();

        // increase login attempts counter
        Integer loginAttempt = (Integer) session.getAttribute("loginAttempt");
        loginAttempt++;
        session.setAttribute("loginAttempt", loginAttempt);

        // add credentials to headers
        headers.setBasicAuth(username, password);

        // set content type
        headers.setContentType(MediaType.APPLICATION_JSON);

        // add headers to session
        session.setAttribute("headers", headers);
    }

    /**
     * This method removes the "headers" attribute from the session,
     * which deletes the authentication credentials.
     * @param session
     */
    public void logout(HttpSession session) {
        session.removeAttribute("headers");
        session.invalidate();
    }

}
