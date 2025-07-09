package com.example.clinic.loginSystem;

import com.example.clinic.Database.PatientDatabase;
import com.example.clinic.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class PatientLoginController extends LoginController{
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    @Override
    protected void handleLoginButtonAction(ActionEvent e){
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (PatientDatabase.getInstance().checkCredentials(username, password)){
            System.out.println("Login bem-sucedido");
        } else {
            System.out.println("Credenciais inválidas");
        }
    }

    @FXML
    @Override
    protected void switchToSignUp(ActionEvent e){
        SceneManager.switchScene(e, "/com/example/clinic/SignUpScene/signup-view.fxml");
    }
}
