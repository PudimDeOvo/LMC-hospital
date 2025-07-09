package com.example.clinic.registerSystem;

import com.example.clinic.Database.PatientDatabase;
import com.example.clinic.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class PatientSignUpController extends SignUpController{
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField ageField;
    @FXML
    private TextField healthPlanField;

    @FXML
    @Override
    protected void handleSignUpButtonAction(ActionEvent e){
        String username = usernameField.getText();
        String password = passwordField.getText();
        String name = nameField.getText();
        String healthPlan = healthPlanField.getText();
        String ageInText = ageField.getText();

        if (isSignUpValid(username, password, name, healthPlan, ageInText)){
            String[] pacientData = {username, password, name, ageInText, healthPlan};
            PatientDatabase.getInstance().addNewPacient(pacientData);
            System.out.println("Usuário cadastrado: " + username);

            showAlert(Alert.AlertType.INFORMATION, "Success", "User " + name + " successfully created!");

            PatientDatabase.getInstance().getCredentials().put(username, password);
            SceneManager.switchScene(e, "/com/example/clinic/LoginScene/login-view.fxml");
        }
    }

    private boolean isSignUpValid(String username, String password, String name, String healthPlan, String age){
        if (username.isEmpty() || password.isEmpty() || name.isEmpty() || ageField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Fill out all camps.");
            return false;
        }

        int ageAsInt;
        try {
            ageAsInt = Integer.parseInt(ageField.getText());
            if (ageAsInt <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException ex) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid age.");
            return false;
        }

        if (PatientDatabase.getInstance().getCredentials().containsKey(username)){
            showAlert(Alert.AlertType.ERROR, "Error", "Username already taken.");
            return false;
        }

        return true;
    }

    @FXML
    @Override
    protected void switchToWelcome(ActionEvent e){
        SceneManager.switchScene(e, "/com/example/clinic/WelcomeScene/welcome-view.fxml");
    }

    @FXML
    @Override
    protected void switchToLogin(ActionEvent e){
        SceneManager.switchScene(e, "/com/example/clinic/LoginScene/login-view.fxml");
    }
}
