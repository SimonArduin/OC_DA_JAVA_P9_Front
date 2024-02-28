package com.medilabo.front.controller;

import com.medilabo.front.util.HeadersUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    private HeadersUtil headersUtil;

    @Value("${gateway.url}")
    private String GATEWAY_URL;

    private static final Logger logger = LoggerFactory.getLogger(NoteController.class);

    public class LoginInfo {
        String username;
        String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    @GetMapping("login")
    public String login(Model model) {
        return "login";
    }

    // adds the credentials provided by the user to HttpHeader
    @PostMapping("login")
    public String loginPost(LoginInfo loginInfo) {
        logger.info("login request");
        String username = loginInfo.getUsername();
        String password = loginInfo.getPassword();
        HeadersUtil.login(username,password);
        return "redirect:home";
    }

    // removes credentials from HttpHeader
    @GetMapping("logout")
    public String logout() {
        logger.info("logout request");
        HeadersUtil.logout();
        return "redirect:login";
    }

    @GetMapping("home")
    public String home() {
        return "redirect:patient/home";
    }

}
