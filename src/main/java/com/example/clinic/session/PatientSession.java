package com.example.clinic.session;
import com.example.clinic.entities.user.Patient;

public class PatientSession {
    private static Patient currentPatient;

    public static void setCurrentPatient(Patient patient){
        currentPatient = patient;
    }

    public static Patient getCurrentPatient(){
        return currentPatient;
    }
}
