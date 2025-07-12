package com.example.clinic.homeSystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import com.example.clinic.session.PatientSession;

import java.io.IOException;

public class PatientHomeController {

    @FXML
    private Label patientNameLabel;

    @FXML
    public void initialize(){
        String fullName = PatientSession.getCurrentPatient().getName().substring(0, 1).toUpperCase()
                            + PatientSession.getCurrentPatient().getName().substring(1);
        patientNameLabel.setText(fullName);

    }
    //botei uppercase pq tava me incomodando é so tirar dps

    @FXML
    private void handleScheduleAppointment(ActionEvent event) {
        switchScene(event, "/com/example/clinic/PatientHomeScene/appointmentsview/patientappointments-view.fxml");
    }

    @FXML
    private void handleCancelAppointment(ActionEvent event) {
        switchScene(event, "/com/example/clinic/PatientHomeScene/cancelappointment/PatientCancelAppointments.fxml");
    }

    @FXML
    private void handleReviewDoctor(ActionEvent event) {
        switchScene(event, "/com/example/clinic/PatientHomeScene/reviewdoctor/reviewdoctor.fxml");
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
