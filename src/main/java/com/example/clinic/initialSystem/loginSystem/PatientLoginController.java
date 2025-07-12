package com.example.clinic.initialSystem.loginSystem;

import com.example.clinic.Database.userDatabase.PatientDatabase;
import com.example.clinic.SceneManager;
import com.example.clinic.initialSystem.sessionSystem.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import com.example.clinic.entities.user.Patient;

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
            Patient loggedPatient = PatientDatabase.getInstance().getPatient(username);
            Session.setCurrentPatient(loggedPatient);
            SceneManager.switchScene(e, "/com/example/clinic/PatientHomeScene/patienthome.fxml");
        } else {
            System.out.println("Credenciais inválidas");
        }
    }

    @FXML
    @Override
    protected void switchToSignUp(ActionEvent e){
        SceneManager.switchScene(e, "/com/example/clinic/SignUpScene/PatientSignUpView/signup-view.fxml");
    }
}
