package com.medilabo.front.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.Charset;
import java.util.Base64;

@Controller
public class LoginController {

    @Value("${gateway.url}")
    private String GATEWAY_URL;

    private static final Logger logger = LoggerFactory.getLogger(NoteController.class);

    private final HttpHeaders headers;

    @Autowired
    public LoginController() {
        this.headers = new HttpHeaders();
        this.headers.setContentType(MediaType.APPLICATION_JSON);
    }
    private String createAuthHeader(String username, String password) {
        String auth = username + ":" + password;
        byte[] encodedAuth = Base64.getEncoder().encode(
                auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + new String(encodedAuth);
        return authHeader;
    }

    class LoginInfo {
        public String username;
        public String password;

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
        model.addAttribute("loginInfo", new LoginInfo());
        return "login";
    }

    @PostMapping(value = "login", consumes = "application/x-www-form-urlencoded")
    public String loginPost(LoginInfo loginInfo) {
        logger.info("login request");
        String username = loginInfo.getUsername();
        String password = loginInfo.getPassword();
        this.headers.set("Authorization", createAuthHeader(username,password));
        return "redirect:home";
    }

    @GetMapping("logout")
    public String logout() {
        logger.info("logout request");
        this.headers.set("Authorization", null);
        return "redirect:login";
    }

    @GetMapping("home")
    public String home() {
        return "redirect:patient/home";
    }

    public HttpHeaders getHeaders() {
        return headers;
    }
}
