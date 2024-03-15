package com.medilabo.front.controller;

import com.medilabo.front.domain.Note;
import com.medilabo.front.service.NoteService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/note")
public class NoteController extends BasicController {

    @Value("${note.url}")
    private String NOTE_URL;
    private final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        super();
        this.noteService = noteService;
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
     * @return Redirects to /home
     */
    @PostMapping(value = "add", consumes = "application/x-www-form-urlencoded")
    public String validateNoteAdd(Note note, HttpSession session) {
        String auth =  getAuthentication(session);
        noteService.add(note, auth);
        return "redirect:../patient/get?id=" + note.getPatientId();
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
        String auth =  getAuthentication(session);
        if (id != null)
            return noteGetById(id, model, auth);
        if (patientId != null)
            return noteGetByPatientId(patientId, model, auth);
        return "error";
    }

    /**
     * This method displays informations on a specific note.
     * @param id
     * @param model
     * @param auth
     * @return A String corresponding to a thymeleaf template
     */
    public String noteGetById(String id, Model model, String auth) {
        Note note = noteService.getById(id, auth);
        model.addAttribute("note", note);
        return "note/getById";
    }

    /**
     * This methods displays all notes with a specific patientId.
     * @param patientId
     * @param model
     * @param auth
     * @return A String corresponding to a thymeleaf template
     */
    public String noteGetByPatientId(Integer patientId, Model model, String auth) {
        List<Note> noteList = noteService.getByPatientId(patientId, auth);
        model.addAttribute("noteList", noteList);
        return "note/getByPatientId";
    }

}
