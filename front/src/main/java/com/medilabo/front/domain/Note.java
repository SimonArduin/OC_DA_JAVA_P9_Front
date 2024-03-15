package com.medilabo.front.domain;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

import java.util.Objects;

public class Note {

    private String id;
    private Integer patientId;
    private String note;

    public Note() {}

    public Note(Integer patientId, String note) {
        this.patientId = patientId;
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note1 = (Note) o;
        return Objects.equals(patientId, note1.patientId) && Objects.equals(note, note1.note);
    }

    public JSONObject toJson() {
        JSONObject noteJson = new JSONObject();
        noteJson.put("id", this.id);
        noteJson.put("patientId", this.patientId);
        noteJson.put("note", this.note);

        return noteJson;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
