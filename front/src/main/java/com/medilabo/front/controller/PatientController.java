package com.medilabo.front.controller;

import com.medilabo.front.domain.Note;
import com.medilabo.front.domain.Patient;
import com.medilabo.front.service.NoteService;
import com.medilabo.front.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/patient")
public class PatientController {
    @Value("${patient.url}")
    private String PATIENT_URL;

    @Value("${note.url}")
    private String NOTE_URL;

    private final WebClient webClient;
    private final PatientService patientService;
    private final NoteService noteService;

    @Autowired
    public PatientController(WebClient.Builder webClientBuilder, PatientService patientService, NoteService noteService) {
        this.webClient = webClientBuilder.baseUrl(PATIENT_URL).build();
        this.patientService = patientService;
        this.noteService = noteService;
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
        patientService.add(patient);
        return "redirect:add";
    }

    @GetMapping("get")
    public String patientGet(Integer id, Model model) {
        Patient patient = patientService.get(id);
        List<Note> noteList = new ArrayList<>();
        try {
            noteList = noteService.getByPatId(id);
        }
        catch (Exception exception) {}
        model.addAttribute("patient", patient);
        model.addAttribute("noteList", noteList);
        return "patient/get";
    }

    @GetMapping("home")
    public String patientHome(Model model) {
        List<Patient> patientList = patientService.getAll();
        model.addAttribute("patientList", patientList);
        return "patient/home";
    }

    @GetMapping("update/{id}")
    public String patientUpdate(@PathVariable Integer id, Model model) {
        Patient patient = patientService.get(id);
        model.addAttribute("patient", patient);
        return "patient/update";
    }

    @PostMapping(value = "update/{id}", consumes = "application/x-www-form-urlencoded")
    public String validatePatientUpdate(@PathVariable Integer id, Patient patient) {
        patientService.update(id, patient);
        return "redirect:../get?id=" + id;
    }

}
