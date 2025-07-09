package com.example.clinic.registerSystem;

import com.example.clinic.Database.PatientDatabase;
import com.example.clinic.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public abstract class SignUpController {

    protected abstract void handleSignUpButtonAction(ActionEvent e);
    protected abstract void switchToLogin(ActionEvent e);
    protected abstract void switchToWelcome(ActionEvent e);

    protected void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
