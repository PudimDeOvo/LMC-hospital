package com.example.clinic.Session;

import com.example.clinic.Entities.Appointment.Appointment;

public class AppointmentSession {
    private static AppointmentSession instance;
    private Appointment appointment;

    private AppointmentSession() {}

    public static AppointmentSession getInstance() {
        if (instance == null) {
            instance = new AppointmentSession();
        }
        return instance;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }
}
