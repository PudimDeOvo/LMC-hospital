package com.example.clinic.Session;

import com.example.clinic.Entities.User.Doctor;

public class DoctorSession {
    private static DoctorSession instance;
    private Doctor loggedDoctor;

    private DoctorSession() {}

    public static DoctorSession getInstance(){
        if (instance == null) {
            instance = new DoctorSession();
        }
        return instance;
    }

    public Doctor getLoggedDoctor() {
        return loggedDoctor;
    }

    public void setLoggedDoctor(Doctor loggedDoctor) {
        this.loggedDoctor = loggedDoctor;
    }
}
