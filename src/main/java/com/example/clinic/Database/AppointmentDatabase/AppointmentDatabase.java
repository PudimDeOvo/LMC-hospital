package com.example.clinic.Database.AppointmentDatabase;

import com.example.clinic.Database.userDatabase.DoctorDatabase;
import com.example.clinic.Database.userDatabase.PatientDatabase;
import com.example.clinic.entities.appointment.Appointment;
import com.example.clinic.entities.user.Doctor;
import com.example.clinic.entities.user.Patient;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDatabase {

    private static AppointmentDatabase instance;

    public static AppointmentDatabase getInstance(){
        if (instance == null) {
            instance = new AppointmentDatabase();
        }
        return instance;
    }

    public List<Appointment> getAppointments(String FILE_PATH, boolean searchForDoctor, String target){
        List<Appointment> appointments = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))){
            String line;

            br.readLine();

            while((line = br.readLine()) != null){
                String[] data = line.split(",");

                if(data.length >= 5){
                    String doctorUsername = data[0].trim();
                    String patientUsername = data[1].trim();
                    String date = data[2].trim();
                    String concluded = data[3].trim();
                    String MedicalReview = data[4].trim();

                    Doctor doctor = DoctorDatabase.getInstance().getDoctor(doctorUsername);
                    Patient patient = PatientDatabase.getInstance().getPatient(patientUsername);

                    if (searchForDoctor){
                        if(doctorUsername.equalsIgnoreCase(target)){
                            appointments.add(new Appointment(doctor, patient, date, Boolean.parseBoolean(concluded), MedicalReview));
                        }
                    } else {
                        if(patientUsername.equalsIgnoreCase(target)){
                            appointments.add(new Appointment(doctor, patient, date, Boolean.parseBoolean(concluded), MedicalReview));
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        }

        return appointments;
    }
}
