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

    private HttpHeaders headers = null;

    @Autowired
    public PatientController(PatientService patientService, PredictionService predictionService, NoteService noteService) {
        super();
        this.patientService = patientService;
        this.predictionService = predictionService;
        this.noteService = noteService;
    }

    /**
     * This method displays the addPatient form.
     *
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
     *
     * @param patient
     * @param session
     * @return Redirects to /home
     */
    @PostMapping(value = "add", consumes = "application/x-www-form-urlencoded")
    public String validatePatientAdd(Patient patient, HttpSession session) {
        String auth = getAuthentication(session);
            patientService.add(patient, auth);
            return "redirect:home";
    }

    /**
     * This method displays a specific patient's informations.
     * If the user has access to the patient's notes, they are displayed.
     *
     * @param id
     * @param model
     * @param session
     * @return A String corresponding to a thymeleaf template
     */
    @GetMapping("get")
    public String patientGet(Integer id, Model model, HttpSession session) {
        String auth =  getAuthentication(session);

        // get patient
            Patient patient = patientService.get(id, auth);
            model.addAttribute("patient", patient);

        // get notes if authorized
        List<Note> noteList = new ArrayList<>();
        Boolean noteAuthorized = false;
        try {
            noteList = noteService.getByPatientId(id, auth);
            noteAuthorized = true;
        }
        catch (HttpClientErrorException.Forbidden exception) {}
        model.addAttribute("noteList", noteList);
        model.addAttribute("noteAuthorized", noteAuthorized);

        // get prediction if authorized
        String prediction = null;
        Boolean predictionAuthorized = false;
        try {
            prediction = predictionService.getById(id, auth);
            predictionAuthorized = true;
        }
        catch (HttpClientErrorException.Forbidden exception) {}
        model.addAttribute("prediction", prediction);
        model.addAttribute("predictionAuthorized", predictionAuthorized);

        return "patient/get";
    }

    /**
     * This method returns a list of all patients
     *
     * @param model
     * @param session
     * @return A String corresponding to a thymeleaf template
     */
    @GetMapping("home")
    public String home(Model model, HttpSession session) {
        String auth = getAuthentication(session);
            List<Patient> patientList = patientService.getAll(auth);
            model.addAttribute("patientList", patientList);
            return "patient/home";
    }

    /**
     * This method displays the update form for a specific patient.
     *
     * @param id
     * @param model
     * @param session
     * @return A String corresponding to a thymeleaf template
     */
    @GetMapping("update/{id}")
    public String patientUpdate(@PathVariable Integer id, Model model, HttpSession session) {
        String auth =  getAuthentication(session);
            Patient patient = patientService.get(id, auth);
            model.addAttribute("patient", patient);
            return "patient/update";
    }

    /**
     * This method consumes the update form.
     *
     * @param id
     * @param patient
     * @param session
     * @return A String corresponding to a thymeleaf template
     */
    @PostMapping(value = "update/{id}", consumes = "application/x-www-form-urlencoded")
    public String validatePatientUpdate(@PathVariable Integer id, Patient patient, HttpSession session) {
        String auth =  getAuthentication(session);
            patientService.update(id, patient, auth);
            return "redirect:../get?id=" + id;
    }

}
