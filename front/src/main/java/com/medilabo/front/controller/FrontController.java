package com.medilabo.front.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Controller
public class FrontController {

    private final String gatewayUrl = "http://localhost:8010/";
    private final String patientSuffix = "patient/";
    private final WebClient webClient;

    public FrontController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(gatewayUrl).build();
    }

    @GetMapping("patient/add")
    public String patientAddForm() {
        return "patient/add";
    }

    @PostMapping("patient/add")
    public String validatePatientAdd(@RequestBody Map patient) {
        System.out.println(patient);
        webClient.post().uri(patientSuffix + "add")
                .retrieve()
                .bodyToMono(Map.class)
                .block();
        return "redirect:patient/add";
    }
    @GetMapping("patient/get/{id}")
    public String patientGet(@PathVariable Integer id, Model model) {
        Map patient = webClient.get().uri(patientSuffix + "get?id=" + id)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
        model.addAttribute("patient", patient);
        return "patient/get";
    }

    @GetMapping("patient/update/{id}")
    public String patientUpdate() {
        return "patient/update";
    }

}
