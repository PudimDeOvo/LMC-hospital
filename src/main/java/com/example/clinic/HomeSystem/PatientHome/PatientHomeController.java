package com.example.clinic.HomeSystem.PatientHome;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import com.example.clinic.SceneManager;

public class PatientHomeController {

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
