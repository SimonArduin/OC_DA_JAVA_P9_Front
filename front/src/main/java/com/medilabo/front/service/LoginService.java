package com.medilabo.front.service;

import com.medilabo.front.dto.LoginInfoDto;
import com.medilabo.front.util.HeadersUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;

@Service
public class LoginService {

    @Autowired
    private HeadersUtil headersUtil;

    public void login(LoginInfoDto loginInfoDto, HttpSession session) {
        HttpHeaders headers = headersUtil.login(loginInfoDto);
        session.setAttribute("headers", headers);
    }

    public void logout(HttpSession session) {
        session.removeAttribute("headers");
    }

}
