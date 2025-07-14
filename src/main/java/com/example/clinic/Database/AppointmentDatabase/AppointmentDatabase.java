package com.example.clinic.Database.AppointmentDatabase;

import com.example.clinic.Database.userDatabase.DoctorDatabase;
import com.example.clinic.Database.userDatabase.PatientDatabase;
import com.example.clinic.Entities.Appointment.Appointment;
import com.example.clinic.Entities.User.Doctor;
import com.example.clinic.Entities.User.Patient;

import java.io.*;
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

    public List<Appointment> getAppointmentsByDate(String FILE_PATH, boolean searchForDoctor, String target, String date){
        List<Appointment> appointments = getAppointments(FILE_PATH, searchForDoctor, target);
        List<Appointment> filteredAppointments = new ArrayList<>();

        for (Appointment appointment : appointments){
            if (appointment.getDate().equals(date)){
                filteredAppointments.add(appointment);
            }
        }
        return filteredAppointments;
    }

    public void addAppointment(String filePath, Appointment appointment) {
        try (FileWriter fw = new FileWriter(filePath, true)) {
            String line = appointment.getDoctor().getUsername() + ","
                    + appointment.getPatient().getUsername() + ","
                    + appointment.getDate() + ","
                    + appointment.isConcluded() + ","
                    + appointment.getMedicalReview() + ","
                    + appointment.getStatus() + "\n";

            fw.write(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cancelAppointment(String filePath, Appointment toCancel) {
        List<Appointment> all = getAppointmentsNew(filePath, false, null);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write("doctor,patient,date,concluded,medicalReview,status\n");

            for (Appointment app : all) {
                if (app.getDoctor().getUsername().equals(toCancel.getDoctor().getUsername()) &&
                        app.getDate().equals(toCancel.getDate()) &&
                        app.getPatient().getUsername().equals(toCancel.getPatient().getUsername())) {
                    app.setStatus("cancelled");
                }

                bw.write(app.getDoctor().getUsername() + "," +
                        app.getPatient().getUsername() + "," +
                        app.getDate() + "," +
                        app.isConcluded() + "," +
                        app.getMedicalReview() + "," +
                        app.getStatus() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        public List<Appointment> getAppointmentsNew(String filePath, boolean concludedOnly, String username) {
            List<Appointment> list = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line = br.readLine(); // skip header
                while ((line = br.readLine()) != null) {
                    String[] tokens = line.split(",");
                    String doctorUsername = tokens[0].trim();
                    String patientUsername = tokens[1].trim();
                    String date = tokens[2].trim();
                    boolean concluded = Boolean.parseBoolean(tokens[3].trim());
                    String medicalReview = tokens[4].trim();
                    String status = tokens.length > 5 ? tokens[5].trim() : "active";

                    if (concludedOnly && !concluded) continue;

                    if (username == null || patientUsername.equals(username)) {
                        Doctor doctor = DoctorDatabase.getInstance().getDoctor(doctorUsername);
                        Patient patient = PatientDatabase.getInstance().getPatient(patientUsername);
                        Appointment app = new Appointment(doctor, patient, date, concluded, medicalReview, status);
                        list.add(app);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return list;
        }

    }
