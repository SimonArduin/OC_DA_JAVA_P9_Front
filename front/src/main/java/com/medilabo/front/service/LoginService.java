package com.medilabo.front.service;

import com.medilabo.front.dto.LoginInfoDto;
import com.medilabo.front.util.HeadersUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private HeadersUtil headersUtil;

    public void login(LoginInfoDto loginInfoDto) {
        String username = loginInfoDto.getUsername();
        String password = loginInfoDto.getPassword();
        HeadersUtil.login(username,password);
    }

    public void logout() {
        HeadersUtil.logout();
    }

}
