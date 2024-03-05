package com.medilabo.front.controller;

import com.medilabo.front.domain.Note;
import com.medilabo.front.service.NoteService;
import com.medilabo.front.util.HeadersUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Controller
@RequestMapping("/note")
public class NoteController {

    @Value("${note.url}")
    private String NOTE_URL;
    private final NoteService noteService;
    private final HeadersUtil headersUtil;

    @Autowired
    public NoteController(NoteService noteService, HeadersUtil headersUtil) {
        this.noteService = noteService;
        this.headersUtil = headersUtil;
    }

    public String redirectToLogin() {
        return "redirect:../login";
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
     * @param session
     * @return Redirects to /add
     */
    @PostMapping(value = "add", consumes = "application/x-www-form-urlencoded")
    public String validateNoteAdd(Note note, HttpSession session) {
        HttpHeaders headers = headersUtil.getHeaders(session);
        if (headers.isEmpty()) {
            return redirectToLogin();
        }
        noteService.add(note, headers);
        return "redirect:add";
    }

    /**
     * This method calls either noteGetById or NoteGetByPatientId according to the provided arguments.
     * @param id
     * @param patientId
     * @param model
     * @param session
     * @return A String corresponding to a thymeleaf template
     */
    @GetMapping("get")
    public String noteGet(String id, Integer patientId, Model model, HttpSession session) {
        HttpHeaders headers = headersUtil.getHeaders(session);
        if (headers.isEmpty()) {
            return redirectToLogin();
        }
        if (id != null)
            return noteGetById(id, model, headers);
        if (patientId != null)
            return noteGetByPatientId(patientId, model, headers);
        return "error";
    }

    /**
     * This method displays informations on a specific note.
     * @param id
     * @param model
     * @param headers
     * @return A String corresponding to a thymeleaf template
     */
    public String noteGetById(String id, Model model, HttpHeaders headers) {
        Note note = noteService.getById(id, headers);
        model.addAttribute("note", note);
        return "note/getById";
    }

    /**
     * This methods displays all notes with a specific patientId.
     * @param patientId
     * @param model
     * @param headers
     * @return A String corresponding to a thymeleaf template
     */
    public String noteGetByPatientId(Integer patientId, Model model, HttpHeaders headers) {
        List<Note> noteList = noteService.getByPatientId(patientId, headers);
        model.addAttribute("noteList", noteList);
        return "note/getByPatientId";
    }

}
