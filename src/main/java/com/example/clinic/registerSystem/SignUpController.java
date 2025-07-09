package com.example.clinic.registerSystem;

import com.example.clinic.Database.PatientDatabase;
import com.example.clinic.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class SignUpController {

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
    protected void handleSignUpButtonAction(ActionEvent e) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String name = nameField.getText();
        String healthPlan = healthPlanField.getText();

        if (username.isEmpty() || password.isEmpty() || name.isEmpty() || ageField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Fill out all camps.");
            return;
        }

        int age;
        try {
            age = Integer.parseInt(ageField.getText());
            if (age <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException ex) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid age.");
            return;
        }

        if (PatientDatabase.getInstance().getCredentials().containsKey(username)){
            showAlert(Alert.AlertType.ERROR, "Error", "Username already taken.");
        }

        String[] pacientData = {username, password, name, Integer.toString(age), healthPlan};
        PatientDatabase.getInstance().addNewPacient(pacientData);
        System.out.println("Usuário cadastrado: " + username);

        showAlert(Alert.AlertType.INFORMATION, "Success", "User " + name + " successfully created!");

        PatientDatabase.getInstance().getCredentials().put(username, password);
        SceneManager.switchScene(e, "/com/example/clinic/LoginScene/login-view.fxml");
    }

    @FXML
    protected void switchToLogin(ActionEvent e){
        SceneManager.switchScene(e, "/com/example/clinic/LoginScene/login-view.fxml");
    }

    @FXML
    protected void switchToWelcome(ActionEvent e){
        SceneManager.switchScene(e, "/com/example/clinic/WelcomeScene/welcome-view.fxml");
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
