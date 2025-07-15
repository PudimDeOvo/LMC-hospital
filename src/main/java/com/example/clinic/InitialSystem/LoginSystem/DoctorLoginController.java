package com.example.clinic.InitialSystem.LoginSystem;

import com.example.clinic.Database.userDatabase.DoctorDatabase;
import com.example.clinic.SceneManager;
import com.example.clinic.Entities.User.Doctor;
import com.example.clinic.Session.DoctorSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class DoctorLoginController extends LoginController{
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

        System.out.println(username + password);

        if (DoctorDatabase.getInstance().checkCredentials(username, password)){
            System.out.println("Login bem-sucedido");
            Doctor loggedDoctor = DoctorDatabase.getInstance().getDoctor(username);
            DoctorSession.getInstance().setLoggedDoctor(loggedDoctor);
            SceneManager.switchScene(e, "/com/example/clinic/DoctorHomeScene/Home/doctorhome-view.fxml");
        } else {
            feedbackLabel.setText("Username or password is incorrect");
            feedbackLabel.setStyle("-fx-text-fill: #d9534f;");
            System.out.println("Credenciais inválidas");
        }
    }



    @FXML
    @Override
    protected void switchToSignUp(ActionEvent e){
        SceneManager.switchScene(e, "/com/example/clinic/SignUpScene/DoctorSignUpView/signup-view.fxml");
    }
}
