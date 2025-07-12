package com.example.clinic.InitialSystem.LoginSystem;

import com.example.clinic.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public abstract class LoginController {

    @FXML
    protected void switchToWelcome(ActionEvent e){
        SceneManager.switchScene(e, "/com/example/clinic/WelcomeScene/welcome-view.fxml");
    }

    protected abstract void handleLoginButtonAction(ActionEvent e);
    protected abstract void switchToSignUp(ActionEvent e);
}