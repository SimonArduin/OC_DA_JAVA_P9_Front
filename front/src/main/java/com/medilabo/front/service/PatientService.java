package com.medilabo.front.service;

import com.medilabo.front.domain.Patient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class PatientService extends BasicService {

    @Value("${patient.url}")
    private String PATIENT_URL;

    public void add(Patient patient, String auth) {
        getRestTemplate(auth, patient.toJson()).postForObject(PATIENT_URL +"add", null, String .class);
    }

    public Patient get(Integer id, String auth) {
        Patient patient = getRestTemplate(auth).getForEntity(PATIENT_URL + "get?id=" + id, Patient.class).getBody();
        return patient;
    }

    public List<Patient> getAll(String auth) {
        List<Patient> patientList = getRestTemplate(auth).getForEntity(PATIENT_URL + "getall", List.class).getBody();
        return patientList;
    }

    public void update(Integer id, Patient patient, String auth) {
        patient.setIdPatient(id);
        getRestTemplate(auth, patient.toJson()).put(PATIENT_URL + "update/" + id, null);
    }

}
