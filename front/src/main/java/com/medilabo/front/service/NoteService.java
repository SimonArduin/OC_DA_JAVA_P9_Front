package com.medilabo.front.service;

import com.medilabo.front.domain.Note;
import com.medilabo.front.util.HeadersUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class NoteService {

    @Value("${note.url}")
    private String NOTE_URL;

    private final HeadersUtil headersUtil;
    private final HttpHeaders headers;

    @Autowired
    public NoteService(HeadersUtil headersUtil) {
        this.headersUtil = headersUtil;
        this.headers = headersUtil.getHeaders();
    }

    public void add(Note note) {
        HttpEntity request = new HttpEntity<>(note.toJson().toString(), headers);
        new RestTemplate().postForObject(NOTE_URL + "add", request, String.class);
    }

    public Note getById(String id) {
        HttpEntity request = new HttpEntity<>(null, headers);
        Note note = new RestTemplate().exchange(NOTE_URL + "getbyid?id=" + id, HttpMethod.GET, request, Note.class).getBody();
        return note;
    }

    public List<Note> getByPatId(Integer patId) {
        HttpEntity request = new HttpEntity<>(null, headers);
        List<Note> noteList = new RestTemplate().exchange(NOTE_URL + "getbypatid?patId=" + patId, HttpMethod.GET, request, List.class).getBody();
        return noteList;
    }
}
