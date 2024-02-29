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

    // when an API call returns 401 - unauthorized, redirect to login
    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleUnauthorizedException(HttpClientErrorException.Unauthorized exception) {
        return "redirect:../login";
    }

    @GetMapping("home")
    public String home() {
        return "redirect:../patient/home";
    }

    // display a form to add a note with specified patId
    @GetMapping("add")
    public String noteAddForm(Model model, Integer patId) {
        Note note = new Note();
        note.setPatId(patId);
        model.addAttribute("note", note);
        return "note/add";
    }

    @PostMapping(value = "add", consumes = "application/x-www-form-urlencoded")
    public String validateNoteAdd(Note note) {
        noteService.add(note);
        return "redirect:add";
    }

    @GetMapping("get")
    public String noteGet(String id, Integer patId, Model model) {
        if (id != null)
            return noteGetById(id, model);
        if (patId != null)
            return noteGetByPatId(patId, model);
        return "error";
    }

    public String noteGetById(String id, Model model) {
        Note note = noteService.getById(id);
        model.addAttribute("note", note);
        return "note/getById";
    }

    public String noteGetByPatId(Integer patId, Model model) {
        List<Note> noteList = noteService.getByPatId(patId);
        model.addAttribute("noteList", noteList);
        return "note/getByPatId";
    }

}
