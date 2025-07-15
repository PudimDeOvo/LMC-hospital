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

                if(data.length >= 6){
                    String doctorUsername = data[0].trim();
                    String patientUsername = data[1].trim();
                    String date = data[2].trim();
                    String concluded = data[3].trim();
                    String MedicalReview = data[4].trim();
                    String status = data[5].trim();

                    Doctor doctor = DoctorDatabase.getInstance().getDoctor(doctorUsername);
                    Patient patient = PatientDatabase.getInstance().getPatient(patientUsername);

                    if (searchForDoctor){
                        if(doctorUsername.equalsIgnoreCase(target)){
                            appointments.add(new Appointment(doctor, patient, date, Boolean.parseBoolean(concluded), MedicalReview, status));
                        }
                    } else {
                        if(patientUsername.equalsIgnoreCase(target)){
                            appointments.add(new Appointment(doctor, patient, date, Boolean.parseBoolean(concluded), MedicalReview, status));
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
        System.out.print(appointments);
        for (Appointment appointment : appointments){
            if (appointment.getDate().equals(date)){
                filteredAppointments.add(appointment);
            }
        }
        return filteredAppointments;
    }

    public void addAppointment(String filePath, Appointment appointment) {
        try (FileWriter fw = new FileWriter(filePath, true)) {
            if(appointment.getDoctor() != null && appointment.getPatient() != null){
                String line = appointment.getDoctor().getUsername() + ","
                        + appointment.getPatient().getUsername() + ","
                        + appointment.getDate() + ","
                        + appointment.isConcluded() + ","
                        + appointment.getMedicalReview() + ","
                        + appointment.getStatus() + "\n";

                fw.write(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cancelAppointment(String filePath, Appointment toCancel) {
        List<Appointment> all = getAppointments(filePath, false, toCancel.getPatient().getUsername());

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write("doctor,patient,date,concluded,medicalReview,status\n");

            for (Appointment app : all) {
                if (app.getDoctor() != null && app.getPatient() != null
                        && app.getDoctor().getUsername().equals(toCancel.getDoctor().getUsername()) &&
                        app.getDate().equals(toCancel.getDate())) {
                    app.setStatus("cancelled");
                }
                if( app.getDoctor() != null && app.getPatient() != null) {
                    bw.write(app.getDoctor().getUsername() + ","
                            + app.getPatient().getUsername() + ","
                            + app.getDate() + ","
                            + app.isConcluded() + ","
                            + app.getMedicalReview() + ","
                            + app.getStatus() + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        walkWaitingList(toCancel.getDoctor().getUsername(), toCancel.getDate());
    }

    public void updateAppointment(Appointment appointment) {
        String filePath = "src/main/database/AppointmentDatabase.csv";
        List<Appointment> allAppointments = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            String header = br.readLine();

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 6) {
                    String doctorUsername = data[0].trim();
                    String patientUsername = data[1].trim();
                    String date = data[2].trim();
                    boolean concluded = Boolean.parseBoolean(data[3].trim());
                    String medicalReview = data[4].trim();
                    String status = data[5].trim();

                    Doctor doctor = DoctorDatabase.getInstance().getDoctor(doctorUsername);
                    Patient patient = PatientDatabase.getInstance().getPatient(patientUsername);

                    if (doctor != null && patient != null &&
                            doctor.getUsername().equals(appointment.getDoctor().getUsername()) &&
                            patient.getUsername().equals(appointment.getPatient().getUsername()) &&
                            date.equals(appointment.getDate())
                    ) {

                        allAppointments.add(appointment);
                    } else if( doctor != null && patient != null) {
                        allAppointments.add(new Appointment(doctor, patient, date, concluded, medicalReview, status));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write("doctor,patient,date,concluded,medicalReview,status\n");
            for (Appointment app : allAppointments) {
                if (app.getDoctor() != null && app.getPatient() != null) {
                    bw.write(app.getDoctor().getUsername() + ","
                            + app.getPatient().getUsername() + ","
                            + app.getDate() + ","
                            + app.isConcluded() + ","
                            + app.getMedicalReview() + ","
                            + app.getStatus() + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void walkWaitingList(String doctorUsername, String date) {
        String filePath = "src/main/database/AppointmentDatabase.csv";
        List<Appointment> allAppointments = new ArrayList<>();
        boolean promoted = false;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String header = br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 6) {
                    String docUser = data[0].trim();
                    String patientUsername = data[1].trim();
                    String appDate = data[2].trim();
                    boolean concluded = Boolean.parseBoolean(data[3].trim());
                    String medicalReview = data[4].trim();
                    String status = data[5].trim();

                    Doctor doctor = DoctorDatabase.getInstance().getDoctor(docUser);
                    Patient patient = PatientDatabase.getInstance().getPatient(patientUsername);

                    Appointment app = new Appointment(doctor, patient, appDate, concluded, medicalReview, status);

                    if (!promoted &&
                            docUser.equals(doctorUsername) &&
                            appDate.equals(date) &&
                            status.equalsIgnoreCase("waiting")) {
                        app.setStatus("active");
                        app.setConcluded(false);
                        promoted = true;
                    }
                    allAppointments.add(app);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write("doctor,patient,date,concluded,medicalReview,status\n");
            for (Appointment app : allAppointments) {
                if (app.getDoctor() != null && app.getPatient() != null) {
                    bw.write(app.getDoctor().getUsername() + ","
                            + app.getPatient().getUsername() + ","
                            + app.getDate() + ","
                            + app.isConcluded() + ","
                            + app.getMedicalReview().replace(",", ";") + ","
                            + app.getStatus() + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void markConcludedAppointmentsAsPaid(String filePath, String patientUsername) {
        List<Appointment> updatedAppointments = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String header = br.readLine(); // skip or store header
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 6) {
                    String doctorUsername = data[0].trim();
                    String user = data[1].trim();
                    String date = data[2].trim();
                    boolean concluded = Boolean.parseBoolean(data[3].trim());
                    String review = data[4].trim();
                    String status = data[5].trim();

                    Doctor doctor = DoctorDatabase.getInstance().getDoctor(doctorUsername);
                    Patient patient = PatientDatabase.getInstance().getPatient(user);

                    Appointment app = new Appointment(doctor, patient, date, concluded, review, status);

                    // Mark as paid if this is the target user and it's concluded but not yet paid
                    if (user.equalsIgnoreCase(patientUsername) &&
                            concluded &&
                            !"paid".equalsIgnoreCase(status)) {
                        app.setStatus("paid");
                    }

                    updatedAppointments.add(app);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }


        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write("doctor,patient,date,concluded,medicalReview,status\n");
            for (Appointment app : updatedAppointments) {
                if(app.getDoctor() != null && app.getPatient() != null) {
                    bw.write(app.getDoctor().getUsername() + "," +
                            app.getPatient().getUsername() + "," +
                            app.getDate() + "," +
                            app.isConcluded() + "," +
                            app.getMedicalReview().replace(",", ";") + "," +
                            app.getStatus() + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
