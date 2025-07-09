package com.example.clinic.welcomeSystem;

import com.example.clinic.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class WelcomeController {

    @FXML
    protected void switchToPatientLogin(ActionEvent e) {
        SceneManager.switchScene(e, "/com/example/clinic/LoginScene/PatientLoginView/login-view.fxml");
    }

    @FXML
    protected void switchToDoctorLogin(ActionEvent e){
        SceneManager.switchScene(e, "/com/example/clinic/LoginScene/DoctorLoginView/login-view.fxml");
    }
}
