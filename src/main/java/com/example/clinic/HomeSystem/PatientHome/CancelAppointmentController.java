package com.example.clinic.HomeSystem.PatientHome;

import com.example.clinic.Database.AppointmentDatabase.AppointmentDatabase;
import com.example.clinic.Entities.Appointment.Appointment;
import com.example.clinic.Entities.User.Patient;
import com.example.clinic.Session.PatientSession;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CancelAppointmentController {

    @FXML private ListView<String> appointmentsList;

    @FXML
    public void initialize() {
        Patient currentPatient = PatientSession.getCurrentPatient();

        List<Appointment> appointments = AppointmentDatabase.getInstance()
                .getAppointments("src/main/database/AppointmentDatabase.csv", false, currentPatient.getUsername());

        List<String> displayList = new ArrayList<>();
        for (Appointment app : appointments) {
            if (!"cancelled".equalsIgnoreCase(app.getStatus())) {
                displayList.add(app.getDate() + " - " + app.getDoctor().getName());
            }
        }

        appointmentsList.setItems(FXCollections.observableArrayList(displayList));
    }

    @FXML
    private void handleCancelAppointment(ActionEvent event) {
        String selected = appointmentsList.getSelectionModel().getSelectedItem();
        if (selected == null) {
            System.out.println("No appointment selected!");
            return;
        }

        Patient currentPatient = PatientSession.getCurrentPatient();

        String[] parts = selected.split(" - ");
        String dateTime = parts[0].trim();
        String doctorName = parts[1].trim();

        List<Appointment> appointments = AppointmentDatabase.getInstance()
                .getAppointments("src/main/database/AppointmentDatabase.csv", false, currentPatient.getUsername());

        for (Appointment app : appointments) {
            if (app.getDate().equals(dateTime) && app.getDoctor().getName().equals(doctorName)) {
                AppointmentDatabase.getInstance().cancelAppointment(
                        "src/main/database/AppointmentDatabase.csv",
                        app
                );
                System.out.println("Cancelled: " + selected);
                break;
            }
        }

        initialize();
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
