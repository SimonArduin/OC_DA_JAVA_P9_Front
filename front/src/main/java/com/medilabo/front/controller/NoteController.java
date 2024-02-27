package com.medilabo.front.controller;

import com.medilabo.front.domain.Note;
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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Controller
@RequestMapping("/note")
public class NoteController {

    @Value("${note.url}")
    private String NOTE_URL;

    private final LoginController loginController;
    private final WebClient webClient;
    private final HttpHeaders headers;

    @Autowired
    public NoteController(WebClient.Builder webClientBuilder, LoginController loginController) {
        this.loginController = loginController;
        this.webClient = webClientBuilder.baseUrl(NOTE_URL).build();
        this.headers = loginController.getHeaders();
    }

    // when an API call made by the controller returns 401 - unauthorized, redirect to login
    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleUnauthorizedException(HttpClientErrorException.Unauthorized exception) {
        return "redirect:../login";
    }

    @GetMapping("add")
    public String noteAddForm(Model model) {
        model.addAttribute("note", new Note());
        return "note/add";
    }

    @PostMapping(value = "add", consumes = "application/x-www-form-urlencoded")
    public String validateNoteAdd(Note note) {
        HttpEntity request = new HttpEntity<>(note.toJson().toString(), headers);
        new RestTemplate().postForObject(NOTE_URL + "add", request, String.class);
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
        HttpEntity request = new HttpEntity<>(null, headers);
        Note note = new RestTemplate().exchange(NOTE_URL + "getbyid?id=" + id, HttpMethod.GET, request, Note.class).getBody();
        model.addAttribute("note", note);
        return "note/getById";
    }

    public String noteGetByPatId(Integer patId, Model model) {
        HttpEntity request = new HttpEntity<>(null, headers);
        List<Note> noteList = new RestTemplate().exchange(NOTE_URL + "getbypatid?patId=" + patId, HttpMethod.GET, request, List.class).getBody();
        model.addAttribute("noteList", noteList);
        return "note/getByPatId";
    }

}
