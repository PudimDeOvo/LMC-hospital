package com.example.clinic.homeSystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PatientHomeController {

    @FXML
    private void handleScheduleAppointment(ActionEvent event) {
        switchScene(event, "/com/example/clinic/PatientHomeScene/patientappointments-view.fxml");
    }

    @FXML
    private void handleCancelAppointment(ActionEvent event) {
        switchScene(event, "/com/example/clinic/PatientHomeScene/cancelappointment.fxml");
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
