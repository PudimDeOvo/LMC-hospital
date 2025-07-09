package com.example.clinic.registerSystem;

import com.example.clinic.Database.DoctorDatabase;
import com.example.clinic.Database.PatientDatabase;
import com.example.clinic.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class DoctorSignUpController extends SignUpController{

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField specialtyField;
    @FXML
    private TextField verifiedHealthPlanField;

    @FXML
    @Override
    protected void handleSignUpButtonAction(ActionEvent e){
        String username = usernameField.getText();
        String password = passwordField.getText();
        String name = nameField.getText();
        String verifiedHealthPlan = verifiedHealthPlanField.getText();
        String specialty = specialtyField.getText();

        if (isSignUpValid(username, password, name, verifiedHealthPlan, specialty)){
            String[] pacientData = {username, password, name, specialty, verifiedHealthPlan};
            PatientDatabase.getInstance().addNewPacient(pacientData);
            System.out.println("Usuário cadastrado: " + username);

            showAlert(Alert.AlertType.INFORMATION, "Success", "User " + name + " successfully created!");

            DoctorDatabase.getInstance().getCredentials().put(username, password);
            SceneManager.switchScene(e, "/com/example/clinic/LoginScene/DoctorLoginView/login-view.fxml");
        }
    }


    private boolean isSignUpValid(String username, String password, String name, String specialty, String verifiedHealthPlanField){
        if (username.isEmpty() || password.isEmpty() || name.isEmpty() || specialty.isEmpty() || verifiedHealthPlanField.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Fill out all camps.");
            return false;
        }

        if (DoctorDatabase.getInstance().getCredentials().containsKey(username) || PatientDatabase.getInstance().getCredentials().containsKey(username)){
            showAlert(Alert.AlertType.ERROR, "Error", "Username already taken.");
            return false;
        }

        return true;
    }

    @FXML
    @Override
    protected void switchToLogin(ActionEvent e){
        SceneManager.switchScene(e, "/com/example/clinic/LoginScene/DoctorLoginView/login-view.fxml");
    }
}
