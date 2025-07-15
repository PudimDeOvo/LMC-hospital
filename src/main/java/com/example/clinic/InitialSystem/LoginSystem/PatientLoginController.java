package com.example.clinic.InitialSystem.LoginSystem;

import com.example.clinic.Database.userDatabase.PatientDatabase;
import com.example.clinic.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import com.example.clinic.Entities.User.Patient;
import com.example.clinic.Session.PatientSession;

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
            Patient loggedPatient = PatientDatabase.getInstance().getPatient(username);
            PatientSession.setCurrentPatient(loggedPatient);
            SceneManager.switchScene(e, "/com/example/clinic/PatientHomeScene/Home/patienthome-view.fxml");
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
