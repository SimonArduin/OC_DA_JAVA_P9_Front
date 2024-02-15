package com.medilabo.front.domain;

import org.json.JSONObject;

public class Patient {

    private int idPatient;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String gender;
    public enum Gender {
        MALE, FEMALE, OTHER
    }
    private String address;
    private String phoneNumber;

    public Patient(String firstName, String lastName, String birthDate, String gender, String address, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public Patient() {}

    public int getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(int idPatient) {
        this.idPatient = idPatient;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public JSONObject toJson() {
        JSONObject patientJson = new JSONObject();
        patientJson.put("idPatient", this.idPatient);
        patientJson.put("firstName", this.firstName);
        patientJson.put("lastName", this.lastName);
        patientJson.put("birthDate", this.birthDate);
        patientJson.put("gender", this.gender.toString());
        patientJson.put("address", this.address);
        patientJson.put("phoneNumber", this.phoneNumber);

        return patientJson;
    }
}