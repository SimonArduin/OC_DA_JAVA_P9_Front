package com.medilabo.front.controller;

import com.medilabo.front.domain.Note;
import com.medilabo.front.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Controller
@RequestMapping("/note")
public class NoteController {

    @Value("${note.url}")
    private String NOTE_URL;
    private final WebClient webClient;
    private final NoteService noteService;

    @Autowired
    public NoteController(WebClient.Builder webClientBuilder, NoteService noteService) {
        this.webClient = webClientBuilder.baseUrl(NOTE_URL).build();
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
     *
     * @return Redirects to /home
     */
    @GetMapping("home")
    public String home() {
        return "redirect:../home";
    }

    /**
     * This method displays the addNote form, with the specified patientId.
     * @param model
     * @param patientId
     * @return A String corresponding to a thymeleaf template
     */
    @GetMapping("add")
    public String noteAddForm(Model model, Integer patientId) {
        Note note = new Note();
        note.setPatientId(patientId);
        model.addAttribute("note", note);
        return "note/add";
    }

    /**
     * This method consumes the addNote form.
     * @param note
     * @return Redirects to /add
     */
    @PostMapping(value = "add", consumes = "application/x-www-form-urlencoded")
    public String validateNoteAdd(Note note) {
        noteService.add(note);
        return "redirect:add";
    }

    /**
     * This method calls either noteGetById or NoteGetByPatientId according to the provided arguments.
     * @param id
     * @param patientId
     * @param model
     * @return A String corresponding to a thymeleaf template
     */
    @GetMapping("get")
    public String noteGet(String id, Integer patientId, Model model) {
        if (id != null)
            return noteGetById(id, model);
        if (patientId != null)
            return noteGetByPatientId(patientId, model);
        return "error";
    }

    /**
     * This method displays informations on a specific note.
     * @param id
     * @param model
     * @return A String corresponding to a thymeleaf template
     */
    public String noteGetById(String id, Model model) {
        Note note = noteService.getById(id);
        model.addAttribute("note", note);
        return "note/getById";
    }

    /**
     * This methods displays all notes with a specific patientId.
     * @param patientId
     * @param model
     * @return A String corresponding to a thymeleaf template
     */
    public String noteGetByPatientId(Integer patientId, Model model) {
        List<Note> noteList = noteService.getByPatientId(patientId);
        model.addAttribute("noteList", noteList);
        return "note/getByPatientId";
    }

}
