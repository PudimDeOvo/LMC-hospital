package com.example.clinic.entities.appointment;

import com.example.clinic.entities.user.Doctor;
import com.example.clinic.entities.user.Patient;

public class Appointment {
    private Doctor doctor;
    private Patient patient;
    private String date;
    private boolean concluded;
    private String medicalReview;

    public Appointment(Doctor doctor, Patient patient, String date, boolean concluded, String medicalReview) {
        this.doctor = doctor;
        this.patient = patient;
        this.date = date;
        this.concluded = concluded;
        this.medicalReview = medicalReview;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isConcluded() {
        return concluded;
    }

    public void setConcluded(boolean concluded) {
        this.concluded = concluded;
    }

    public String getMedicalReview() {
        return medicalReview;
    }

    public void setMedicalReview(String medicalReview) {
        this.medicalReview = medicalReview;
    }
}
