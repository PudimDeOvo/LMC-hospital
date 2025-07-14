package com.example.clinic.HomeSystem.PatientHome;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import com.example.clinic.SceneManager;
import com.example.clinic.Session.PatientSession;
import com.example.clinic.Entities.User.Patient;

public class PatientHomeController {

    @FXML
    private Label patientNameLabel;

    @FXML Label patientPlanLabel;

    @FXML
    public void initialize(){
        Patient patient = PatientSession.getCurrentPatient();

        String fullName = patient.getName().substring(0, 1).toUpperCase()
                + PatientSession.getCurrentPatient().getName().substring(1);
        patientNameLabel.setText(fullName);

        String insurancePlan = patient.getHealthPlan().toString().replace("_", " ").toUpperCase();
        patientPlanLabel.setText(insurancePlan);

    }
    @FXML
    private void handleAppointments(ActionEvent event) {
        System.out.println("Appointments button clicked");
        SceneManager.switchScene(event, "/com/example/clinic/PatientHomeScene/appointmentHome/appointmenthome-view.fxml");
    }

    @FXML
    private void handleEditProfile(ActionEvent event) {
        System.out.println("Edit profile clicked");
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        System.out.println("Logout clicked");
        SceneManager.switchScene(event, "/com/example/clinic/LoginScene/PatientLoginView/login-view.fxml");
    }
}
