package com.medilabo.front.service;

import com.medilabo.front.domain.Patient;
import com.medilabo.front.util.HeadersUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class PatientService {

    @Value("${patient.url}")
    private String PATIENT_URL;
    private final HeadersUtil headersUtil;

    @Autowired
    public PatientService(HeadersUtil headersUtil) {
        this.headersUtil = headersUtil;
    }

    public void add(Patient patient) {
        HttpEntity request = new HttpEntity<>(patient.toJson().toString(), headersUtil.getHeaders());
        new RestTemplate().postForObject(PATIENT_URL +"add", request, String .class);
    }

    public Patient get(Integer id) {
        HttpEntity request = new HttpEntity<>(null, headersUtil.getHeaders());
        Patient patient = new RestTemplate().exchange(PATIENT_URL + "get?id=" + id, HttpMethod.GET, request, Patient.class).getBody();
        return patient;
    }

    public List<Patient> getAll() {
        HttpEntity request = new HttpEntity<>(null, headersUtil.getHeaders());
        List<Patient> patientList = new RestTemplate().exchange(PATIENT_URL + "getall", HttpMethod.GET, request, List.class).getBody();
        return patientList;
    }

    public void update(Integer id, Patient patient) {
        patient.setIdPatient(id);
        HttpEntity<String> request = new HttpEntity<>(patient.toJson().toString(), headersUtil.getHeaders());
        new RestTemplate().put(PATIENT_URL + "update/" + id, request);
    }

}
