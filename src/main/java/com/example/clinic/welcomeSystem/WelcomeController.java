package com.example.clinic.welcomeSystem;

import com.example.clinic.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class WelcomeController {
    @FXML
    protected void switchToPatientLogin(ActionEvent e) {
        SceneManager.switchScene(e, "/com/example/clinic/LoginScene/login-view.fxml");
    }
}
