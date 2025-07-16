package com.example.clinic.Entities.Appointment;

import com.example.clinic.Entities.User.Doctor;
import com.example.clinic.Entities.User.Patient;

public class Appointment {
    private Doctor doctor;
    private Patient patient;
    private String date;
    private boolean concluded;
    private String medicalReview;
    private String status; //active ou cancelled or paid

    public Appointment(Doctor doctor, Patient patient, String date, boolean concluded, String medicalReview, String status) {
        this.doctor = doctor;
        this.patient = patient;
        this.date = date;
        this.concluded = concluded;
        this.medicalReview = medicalReview;
        this.status = status;
    }

    //pra sempre criar com consulta com status "active"
    public Appointment(Doctor doctor, Patient patient, String date, boolean concluded, String medicalReview) {
        this(doctor, patient, date, concluded, medicalReview, "active");
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

    public String getStatus() {return status;}

    public void setStatus(String status) {this.status = status;}

    @Override
    public String toString() {
        String doctorName = (doctor != null) ? doctor.getName() : "Desconhecido";
        return date + " - Dr. " + doctorName + " [" + status + "]";
    }

}
