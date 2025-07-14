package com.example.clinic.HomeSystem.PatientHome;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.*;
import java.io.*;
import com.example.clinic.Entities.User.Doctor;
import com.example.clinic.Entities.Appointment.Appointment;
import com.example.clinic.Database.AppointmentDatabase.AppointmentDatabase;
import com.example.clinic.Database.userDatabase.DoctorDatabase;
import com.example.clinic.Entities.User.Patient;
import com.example.clinic.Session.PatientSession;
import com.example.clinic.Entities.MedicalSpecialty;
import com.example.clinic.Entities.HealthInsurancePlan;

public class CreateAppointmentsController {

    @FXML private ComboBox<String> doctorComboBox;
    @FXML private DatePicker datePicker;
    @FXML private TextField timeField;
    @FXML private ComboBox<MedicalSpecialty> specialtyComboBox;

    @FXML
    public void initialize() {
        List<Doctor> allDoctors = DoctorDatabase.getInstance().getAllDoctors();

        List<String> doctorNames = new ArrayList<>();
        for (Doctor doctor : allDoctors) {
            doctorNames.add(doctor.getName() + " (" + doctor.getUsername() + ")");
        }

        doctorComboBox.setItems(FXCollections.observableArrayList(doctorNames));

        Patient patient = PatientSession.getCurrentPatient();
        if (patient == null) {
            System.out.println("No patient session found.");
            return;
        }

        HealthInsurancePlan plan = patient.getHealthPlan();
        if (plan == null) {
            System.out.println("No insurance plan found for patient.");
            return;
        }

        specialtyComboBox.setItems(FXCollections.observableArrayList(plan.getCoveredSpecialties()));

        specialtyComboBox.setOnAction(event -> {
            MedicalSpecialty selectedSpecialty = specialtyComboBox.getValue();
            if (selectedSpecialty == null) return;

            List<String> filteredDoctors = new ArrayList<>();
            for (Doctor doc : allDoctors) {
                if (doc.getSpecialty().equals(selectedSpecialty)) {
                    filteredDoctors.add(doc.getName() + " (" + doc.getUsername() + ")");
                }
            }
            doctorComboBox.setItems(FXCollections.observableArrayList(filteredDoctors));
        });
    }

    private void showConfirmation(String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("Appointment Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleConfirm(ActionEvent event) {
        String selectedDoctor = doctorComboBox.getValue();
        String date = String.valueOf(datePicker.getValue());
        String time = timeField.getText();

        if (selectedDoctor == null || date == null || time.isBlank()) {
            System.out.println("Please fill in all fields.");
            return;
        }

        int start = selectedDoctor.indexOf('(') + 1;
        int end = selectedDoctor.indexOf(')');
        String doctorUsername = selectedDoctor.substring(start, end);

        Doctor doctor = DoctorDatabase.getInstance().getDoctor(doctorUsername);
        Patient patient = PatientSession.getCurrentPatient();

        if (doctor == null || patient == null) {
            System.out.println("Doctor or patient not found!");
            return;
        }

        List<Appointment> existingAppointments = AppointmentDatabase
                .getInstance()
                .getAppointments("src/main/database/AppointmentDatabase.csv", true, doctor.getUsername());

        long activeCount = existingAppointments.stream()
                .filter(app -> !"cancelled".equalsIgnoreCase(app.getStatus()))
                .filter(app -> !app.isConcluded())
                .count();

        String dateTime = date + " " + time;

        if (activeCount >= 3) {
            Appointment waitingAppointment = new Appointment(
                    doctor,
                    patient,
                    dateTime,
                    false,
                    "none",
                    "waiting"
            );
            AppointmentDatabase.getInstance().addAppointment(
                    "src/main/database/AppointmentDatabase.csv",
                    waitingAppointment
            );
            System.out.println("Doctor full! Added to the waiting list.");
            showConfirmation("This doctor is fully booked.\nYou have been added to the waiting list.");
            goHome(event);
            return;
        }

        Appointment newAppointment = new Appointment(
                doctor,
                patient,
                dateTime,
                false,
                "none",
                "active"
        );

        AppointmentDatabase.getInstance().addAppointment(
                "src/main/database/AppointmentDatabase.csv",
                newAppointment
        );

        System.out.println("Appointment booked!");
        goHome(event);
    }


    @FXML
    private void handleBack(ActionEvent event) {
        goHome(event);
    }

    private void goHome(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/clinic/PatientHomeScene/appointmentHome/appointmenthome-view.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}