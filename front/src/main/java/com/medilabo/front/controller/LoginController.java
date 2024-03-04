package com.medilabo.front.controller;

import com.medilabo.front.dto.LoginInfoDto;
import com.medilabo.front.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class LoginController {

    @Value("${gateway.url}")
    private String GATEWAY_URL;

    @Autowired
    LoginService loginService;

    private static final Logger logger = LoggerFactory.getLogger(NoteController.class);

    /**
     * This method displays the login form.
     * @return A String corresponding to a thymeleaf template
     */
    @GetMapping("login")
    public String login() {
        return "login";
    }

    /**
     * This methods consumes the login form and sends it to HeadersUtil.
     * @param loginInfoDto
     * @return Redirects to /home
     */
    @PostMapping("login")
    public String loginPost(LoginInfoDto loginInfoDto) {
        logger.info("login request");
        loginService.login(loginInfoDto);
        return "redirect:home";
    }

    /**
     * This methods logs out the user.
     * @return Redirects to /login
     */
    @GetMapping("logout")
    public String logout() {
        logger.info("logout request");
            loginService.logout();
        return "redirect:login";
    }

    /**
     *
     * @return Redirects to /patient/home
     */

    @GetMapping("home")
    public String home() {
        return "redirect:patient/home";
    }
}
