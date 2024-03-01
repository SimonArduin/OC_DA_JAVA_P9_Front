package com.medilabo.front.domain;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Objects;

public class Note {

    private String id;
    private Integer patientId;
    private String patient;
    private String note;

    public Note() {}

    public Note(Integer patientId, String patient, String note) {
        this.patientId = patientId;
        this.patient = patient;
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note1 = (Note) o;
        return Objects.equals(patientId, note1.patientId) && Objects.equals(patient, note1.patient) && Objects.equals(note, note1.note);
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

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
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
