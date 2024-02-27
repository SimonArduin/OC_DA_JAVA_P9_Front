package com.medilabo.front.domain;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Objects;

public class Note {

    private String id;
    private Integer patId;
    private String patient;
    private String note;

    public Note() {}

    public Note(Integer patId, String patient, String note) {
        this.patId = patId;
        this.patient = patient;
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note1 = (Note) o;
        return Objects.equals(patId, note1.patId) && Objects.equals(patient, note1.patient) && Objects.equals(note, note1.note);
    }

    public String toJson() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(this);
        } catch (Exception e) {
            e.printStackTrace();
            return "{}";
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPatId() {
        return patId;
    }

    public void setPatId(Integer patId) {
        this.patId = patId;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
