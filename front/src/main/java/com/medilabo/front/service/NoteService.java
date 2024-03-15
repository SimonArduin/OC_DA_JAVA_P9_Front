package com.medilabo.front.service;

import com.medilabo.front.domain.Note;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService extends BasicService {

    @Value("${note.url}")
    private String NOTE_URL;

    public void add(Note note, String auth) {
        getRestTemplate(auth, note.toJson()).postForObject(NOTE_URL + "add", null, String.class);
    }

    public Note getById(String id, String auth) {
        Note note = getRestTemplate(auth).getForEntity(NOTE_URL + "getbyid?id=" + id, Note.class).getBody();
        return note;
    }

    public List<Note> getByPatientId(Integer patientId, String auth) {
        List<Note> noteList = getRestTemplate(auth).getForEntity(NOTE_URL + "getbypatientid?patientId=" + patientId, List.class).getBody();
        return noteList;
    }
}
