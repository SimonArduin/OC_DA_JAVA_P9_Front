package com.medilabo.front.controller;

import com.medilabo.front.domain.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@RequestMapping("/patient")
public class PatientController {

    @Value("${patient.url}")
    private String PATIENT_URL;

    private final LoginController loginController;
    private final WebClient webClient;
    private final HttpHeaders headers;

    @Autowired
    public PatientController(WebClient.Builder webClientBuilder, LoginController loginController) {
        this.loginController = loginController;
        this.webClient = webClientBuilder.baseUrl(PATIENT_URL).build();
        this.headers = loginController.getHeaders();
    }

    // when an API call made by the controller returns 401 - unauthorized, redirect to login
    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleUnauthorizedException(HttpClientErrorException.Unauthorized exception) {
        return "redirect:../login";
    }

    @GetMapping("add")
    public String patientAddForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "patient/add";
    }

    @PostMapping(value = "add", consumes = "application/x-www-form-urlencoded")
    public String validatePatientAdd(Patient patient) {
        HttpEntity request = new HttpEntity<>(patient.toJson().toString(), headers);
        new RestTemplate().postForObject(PATIENT_URL + "add", request, String.class);
        return "redirect:add";
    }

    @GetMapping("get")
    public String patientGet(Integer id, Model model) {
        HttpEntity request = new HttpEntity<>(null, headers);
        Patient patient = new RestTemplate().exchange(PATIENT_URL + "get?id=" + id, HttpMethod.GET, request, Patient.class).getBody();
        model.addAttribute("patient", patient);
        return "patient/get";
    }

    @GetMapping("home")
    public String patientHome(Model model) {
        HttpEntity request = new HttpEntity<>(null, headers);
        List<Patient> patientList = new RestTemplate().exchange(PATIENT_URL + "getall", HttpMethod.GET, request, List.class).getBody();
        model.addAttribute("patientList", patientList);
        return "patient/home";
    }

    @GetMapping("update/{id}")
    public String patientUpdate(@PathVariable Integer id, Model model) {
        HttpEntity request = new HttpEntity<>(null, headers);
        Patient patient = new RestTemplate().exchange(PATIENT_URL + "get?id=" + id, HttpMethod.GET, request, Patient.class).getBody();
        patient.setIdPatient(id);
        model.addAttribute("patient", patient);
        return "patient/update";
    }

    @PostMapping(value = "update/{id}", consumes = "application/x-www-form-urlencoded")
    public String validatePatientUpdate(@PathVariable Integer id, Patient patient) {
        patient.setIdPatient(id);
        HttpEntity<String> request = new HttpEntity<>(patient.toJson().toString(), headers);
        new RestTemplate().put(PATIENT_URL + "update/" + id, request);
        return "redirect:../get?id=" + id;
    }

}
