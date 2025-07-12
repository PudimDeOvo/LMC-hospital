package com.example.clinic.initialSystem.sessionSystem;
import com.example.clinic.entities.user.Patient;

public class Session {
    private static Patient currentPatient;

    public static void setCurrentPatient(Patient patient){
        currentPatient = patient;
    }

    public static Patient getCurrentPatient(){
        return currentPatient;
    }
}
