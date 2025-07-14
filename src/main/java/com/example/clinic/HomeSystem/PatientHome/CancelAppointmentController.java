package com.example.clinic.HomeSystem.PatientHome;

import com.example.clinic.Entities.Appointment.Appointment;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.*;
import com.example.clinic.Entities.User.Patient;
import com.example.clinic.Session.PatientSession;
import com.example.clinic.Database.AppointmentDatabase.AppointmentDatabase;

import java.io.IOException;

public class CancelAppointmentController {

    @FXML private ListView<Appointment> appointmentsList;

    @FXML
    public void initialize() {
        System.out.println("initialize() called");
        Patient currentPatient = PatientSession.getCurrentPatient();
        if (currentPatient == null) return;

        List<Appointment> appointments = AppointmentDatabase.getInstance()
                .getAppointmentsNew("src/main/database/AppointmentDatabase.csv", false, currentPatient.getUsername());

        appointments.removeIf(app -> "cancelled".equalsIgnoreCase(app.getStatus()));

        appointmentsList.setItems(FXCollections.observableArrayList(appointments));

    }


    @FXML
    private void handleCancelAppointment(ActionEvent event) {
        Appointment selected = appointmentsList.getSelectionModel().getSelectedItem();
        if (selected == null) {
            System.out.println("No appointment selected!");
            return;
        }

        AppointmentDatabase.getInstance().cancelAppointment(
                "src/main/database/AppointmentDatabase.csv",
                selected
        );

        System.out.println("Cancelled appointment: " + selected.getDate() + " - " + selected.getDoctor().getName());

        initialize();

        appointmentsList.refresh();
    }


    @FXML
    private void handleBack(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/clinic/PatientHomeScene/home/patienthome.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
