package com.example.clinic.homeSystem;

import com.example.clinic.entities.user.Patient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import com.example.clinic.initialSystem.sessionSystem.Session;

import java.io.IOException;

public class PatientHomeController {

    @FXML
    private Label patientNameLabel;

    @FXML
    private Label welcomeLabel;

    @FXML
    public void initialize(){
        String fullName = Session.getCurrentPatient().getName();
        String username = Session.getCurrentPatient().getUsername();
        patientNameLabel.setText(fullName);
        welcomeLabel.setText("Welcome, " + username);
    }

    @FXML
    private void handleScheduleAppointment(ActionEvent event) {
        switchScene(event, "/com/example/clinic/PatientHomeScene/patientappointments-view.fxml");
    }

    @FXML
    private void handleCancelAppointment(ActionEvent event) {
        switchScene(event, "/com/example/clinic/PatientHomeScene/PatientCancelAppointments.fxml");
    }

    @FXML
    private void handleReviewDoctor(ActionEvent event) {
        switchScene(event, "/com/example/clinic/PatientHomeScene/reviewdoctor.fxml");
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        switchScene(event, "/com/example/clinic/LoginScene/PatientLoginView/login-view.fxml");
    }

    private void switchScene(ActionEvent event, String fxmlPath) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
