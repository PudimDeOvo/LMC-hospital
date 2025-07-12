package com.example.clinic.homeSystem;

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
import com.example.clinic.entities.user.Doctor;
import com.example.clinic.entities.appointment.Appointment;
import com.example.clinic.Database.AppointmentDatabase.AppointmentDatabase;
import com.example.clinic.Database.userDatabase.DoctorDatabase;
import com.example.clinic.entities.user.Patient;
import com.example.clinic.initialSystem.sessionSystem.Session;

public class PatientAppointmentsController {

    @FXML private ComboBox<String> doctorComboBox;
    @FXML private DatePicker datePicker;
    @FXML private TextField timeField;

    @FXML
    public void initialize() {
        List<Doctor> allDoctors = DoctorDatabase.getInstance().getAllDoctors();

        List<String> doctorNames = new ArrayList<>();
        for (Doctor doctor : allDoctors) {
            doctorNames.add(doctor.getName() + " (" + doctor.getUsername() + ")");
        }

        doctorComboBox.setItems(FXCollections.observableArrayList(doctorNames));
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
        Patient patient = Session.getCurrentPatient();

        if (doctor == null || patient == null) {
            System.out.println("Doctor or patient not found!");
            return;
        }

        String dateTime = date + " " + time;

        Appointment newAppointment = new Appointment(
                doctor,
                patient,
                dateTime,
                false,
                "none"
        );

        AppointmentDatabase.getInstance().addAppointment(
                "src/main/database/AppointmentDatabase.csv",
                newAppointment
        );

        System.out.println("Appointment saved: "
                + doctorUsername + ","
                + patient.getUsername() + ","
                + dateTime);

        goHome(event);
    }

    @FXML
    private void handleBack(ActionEvent event) {
        goHome(event);
    }

    private void goHome(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/clinic/PatientHomeScene/patienthome.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
