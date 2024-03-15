package com.medilabo.front.controller;

import com.medilabo.front.dto.LoginInfoDto;
import com.medilabo.front.service.LoginService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class LoginController {

    @Value("${gateway.url}")
    private String GATEWAY_URL;

    @Autowired
    LoginService loginService;

    private static final Logger logger = LoggerFactory.getLogger(NoteController.class);

    /**
     * This method displays the login form,
     * after checking if another attempt has already been made for this session.
     * @return A String corresponding to a thymeleaf template
     */
    @GetMapping("login")
    public String login(HttpSession session, Model model) {
        Boolean firstAttempt = true;
        Integer loginAttempt = (Integer) session.getAttribute("loginAttempt");
        if(loginAttempt == null) {
            loginAttempt = 0;
            session.setAttribute("loginAttempt", loginAttempt);
        }
        if(loginAttempt>0)
            firstAttempt = false;
        model.addAttribute("firstAttempt", firstAttempt);
        return "login";
    }

    /**
     * This methods consumes the login form and sends it to HeadersUtil.
     * @param loginInfoDto
     * @param session
     * @return Redirects to /home
     */
    @PostMapping("login")
    public String loginPost(LoginInfoDto loginInfoDto, HttpSession session) {
        logger.info("login request");
        loginService.login(loginInfoDto, session);
        return "redirect:home";
    }

    /**
     * This methods logs out the user.
     * @param session
     * @return Redirects to /login
     */
    @GetMapping("logout")
    public String logout(HttpSession session) {
        logger.info("logout request");
        loginService.logout(session);
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
