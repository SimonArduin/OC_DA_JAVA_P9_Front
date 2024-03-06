package com.medilabo.front.service;

import com.medilabo.front.domain.Patient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class PatientService {

    @Value("${patient.url}")
    private String PATIENT_URL;

    public void add(Patient patient, HttpHeaders headers) {
        HttpEntity request = new HttpEntity<>(patient.toJson().toString(), headers);
        new RestTemplate().postForObject(PATIENT_URL +"add", request, String .class);
    }

    public Patient get(Integer id, HttpHeaders headers) {
        HttpEntity request = new HttpEntity<>(null, headers);
        Patient patient = new RestTemplate().exchange(PATIENT_URL + "get?id=" + id, HttpMethod.GET, request, Patient.class).getBody();
        return patient;
    }

    public List<Patient> getAll(HttpHeaders headers) {
        HttpEntity request = new HttpEntity<>(null, headers);
        List<Patient> patientList = new RestTemplate().exchange(PATIENT_URL + "getall", HttpMethod.GET, request, List.class).getBody();
        return patientList;
    }

    public void update(Integer id, Patient patient, HttpHeaders headers) {
        patient.setIdPatient(id);
        HttpEntity<String> request = new HttpEntity<>(patient.toJson().toString(), headers);
        new RestTemplate().put(PATIENT_URL + "update/" + id, request);
    }

}
