package com.medilabo.front.controller;

import com.medilabo.front.domain.Patient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/patient")
public class PatientController {
    @Value("${gateway.url}")
    private String GATEWAY_URL;
    private final WebClient webClient;
    private final HttpHeaders headers;

    public PatientController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(GATEWAY_URL).build();
        this.headers = new HttpHeaders();
        this.headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @GetMapping("add")
    public String patientAddForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "patient/add";
    }

    @PostMapping(value = "add", consumes = "application/x-www-form-urlencoded")
    public String validatePatientAdd(Patient patient) {
        HttpEntity<String> request =
                new HttpEntity<>(patient.toJson().toString(), headers);
        new RestTemplate().postForObject(GATEWAY_URL + "add", request, String.class);
        return "redirect:add";
    }

    @GetMapping("get")
    public String patientGet(Integer id, Model model) {
        Patient patient = new RestTemplate().getForEntity(GATEWAY_URL + "get?id=" + id, Patient.class).getBody();
        model.addAttribute("patient", patient);
        return "patient/get";
    }

    @GetMapping("update/{id}")
    public String patientUpdate(@PathVariable Integer id, Model model) {
        Patient patient = new RestTemplate().getForEntity(GATEWAY_URL + "get?id=" + id, Patient.class).getBody();
        patient.setIdPatient(id);
        model.addAttribute("patient", patient);
        return "patient/update";
    }

    @PostMapping(value = "update/{id}", consumes = "application/x-www-form-urlencoded")
    public String validatePatientUpdate(@PathVariable Integer id, Patient patient) {
        patient.setIdPatient(id);
        HttpEntity<String> request =
                new HttpEntity<>(patient.toJson().toString(), headers);
        new RestTemplate().put(GATEWAY_URL + "update/" + id, request);
        return "redirect:../get?id=" + id;
    }

}
