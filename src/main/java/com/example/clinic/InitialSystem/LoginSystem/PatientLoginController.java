package com.example.clinic.InitialSystem.LoginSystem;

import com.example.clinic.Database.userDatabase.PatientDatabase;
import com.example.clinic.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import com.example.clinic.Entities.User.Patient;
import com.example.clinic.Session.PatientSession;

public class PatientLoginController extends LoginController{
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML private Label feedbackLabel;

    @FXML
    @Override
    protected void handleLoginButtonAction(ActionEvent e){
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (PatientDatabase.getInstance().checkCredentials(username, password)){
            System.out.println("Login bem-sucedido");
            Patient loggedPatient = PatientDatabase.getInstance().getPatient(username);
            PatientSession.setCurrentPatient(loggedPatient);
            SceneManager.switchScene(e, "/com/example/clinic/PatientHomeScene/Home/patienthome-view.fxml");
        } else {
            feedbackLabel.setText("Username or password is incorrect");
            feedbackLabel.setStyle("-fx-text-fill: #d9534f;");
            System.out.println("Credenciais inválidas");
        }
    }

    @FXML
    @Override
    protected void switchToSignUp(ActionEvent e){
        SceneManager.switchScene(e, "/com/example/clinic/SignUpScene/PatientSignUpView/signup-view.fxml");
    }
}
