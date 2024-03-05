package com.medilabo.front.controller;

import com.medilabo.front.domain.Note;
import com.medilabo.front.domain.Patient;
import com.medilabo.front.service.NoteService;
import com.medilabo.front.service.PatientService;
import com.medilabo.front.service.PredictionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/patient")
public class PatientController extends BasicController {
    @Value("${patient.url}")
    private String PATIENT_URL;
    private final PatientService patientService;
    private final PredictionService predictionService;
    private final NoteService noteService;

    @Autowired
    public PatientController(PatientService patientService, PredictionService predictionService, NoteService noteService) {
        this.patientService = patientService;
        this.predictionService = predictionService;
        this.noteService = noteService;
    }

    /**
     * This methods is called when a 401 - unauthorized exception is thrown.
     * It redirects the user to the login page.
     * @param exception
     * @return Redirects to /login
     */
    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleUnauthorizedException(HttpClientErrorException.Unauthorized exception) {
        return "redirect:../login";
    }

    /**
     * This method displays the addPatient form.
     * @param model
     * @return A String corresponding to a thymeleaf template
     */
    @GetMapping("add")
    public String patientAddForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "patient/add";
    }

    /**
     * This method consumes the addPatient form.
     * @param patient
     * @return Redirects to /add
     */
    @PostMapping(value = "add", consumes = "application/x-www-form-urlencoded")
    public String validatePatientAdd(Patient patient, HttpSession session) {
        HttpHeaders headers = getHeaders(session);
        if (headers.isEmpty()) {
            return redirectToLogin();
        }
        patientService.add(patient, headers);
        return "redirect:add";
    }

    /**
     * This method displays a specific patient's informations.
     * If the user has access to the patient's notes, they are displayed.
     *
     * @param id
     * @param model
     * @return A String corresponding to a thymeleaf template
     */
    @GetMapping("get")
    public String patientGet(Integer id, Model model, HttpSession session) {
        HttpHeaders headers = getHeaders(session);
        if (headers.isEmpty()) {
            return redirectToLogin();
        }

        // get patient
        Patient patient = patientService.get(id, headers);
        model.addAttribute("patient", patient);

        // get notes if authorized
        List<Note> noteList = new ArrayList<>();
        Boolean noteAuthorized = false;
        try {
            noteList = noteService.getByPatientId(id, headers);
            noteAuthorized = true;
        }
        catch (Exception exception) {}
        model.addAttribute("noteList", noteList);
        model.addAttribute("noteAuthorized", noteAuthorized);

        // get prediction if authorized
        String prediction = null;
        Boolean predictionAuthorized = false;
        try {
            prediction = predictionService.getById(id, headers);
            predictionAuthorized = true;
        }
        catch (Exception exception) {}
        model.addAttribute("prediction", prediction);
        model.addAttribute("predictionAuthorized", predictionAuthorized);

        return "patient/get";
    }

    /**
     *
     * @return Redirects to /home
     */
    @GetMapping("home")
    public String patientHome(Model model, HttpSession session) {
        HttpHeaders headers = getHeaders(session);
        if (headers.isEmpty()) {
            return redirectToLogin();
        }
        List<Patient> patientList = patientService.getAll(headers);
        model.addAttribute("patientList", patientList);
        return "patient/home";
    }

    /**
     * This method displays the update form for a specific patient.
     * @param id
     * @param model
     * @return A String corresponding to a thymeleaf template
     */
    @GetMapping("update/{id}")
    public String patientUpdate(@PathVariable Integer id, Model model, HttpSession session) {
        HttpHeaders headers = getHeaders(session);
        if (headers.isEmpty()) {
            return redirectToLogin();
        }
        Patient patient = patientService.get(id, headers);
        model.addAttribute("patient", patient);
        return "patient/update";
    }

    /**
     * This method consumes the update form.
     * @param id
     * @param patient
     * @return A String corresponding to a thymeleaf template
     */
    @PostMapping(value = "update/{id}", consumes = "application/x-www-form-urlencoded")
    public String validatePatientUpdate(@PathVariable Integer id, Patient patient, HttpSession session) {
        HttpHeaders headers = getHeaders(session);
        if (headers.isEmpty()) {
            return redirectToLogin();
        }
        patientService.update(id, patient, headers);
        return "redirect:../get?id=" + id;
    }

}
