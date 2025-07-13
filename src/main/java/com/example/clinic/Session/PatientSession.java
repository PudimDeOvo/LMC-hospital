package com.example.clinic.Session;
import com.example.clinic.Entities.User.Patient;

public class PatientSession {
    private static Patient currentPatient;

    public static void setCurrentPatient(Patient patient){
        currentPatient = patient;
    }

    public static Patient getCurrentPatient(){
        return currentPatient;
    }
}