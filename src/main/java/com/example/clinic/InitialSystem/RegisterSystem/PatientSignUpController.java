package com.example.clinic.InitialSystem.RegisterSystem;

import com.example.clinic.Database.userDatabase.DoctorDatabase;
import com.example.clinic.Database.userDatabase.PatientDatabase;
import com.example.clinic.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
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
    private ComboBox<String> healthPlanComboBox;


    @FXML
    private void initialize(){
        healthPlanComboBox.getItems().addAll(
                "Basic Plus",
                "Premium Health",
                "Executive Total"
        );
    }


    @FXML
    @Override
    protected void handleSignUpButtonAction(ActionEvent e){
        String username = usernameField.getText();
        String password = passwordField.getText();
        String name = nameField.getText();
        String ageInText = ageField.getText();
        String healthPlan = healthPlanComboBox.getValue();

        if (isSignUpValid()){
            String[] pacientData = {username, password, name, ageInText, healthPlan};
            PatientDatabase.getInstance().addNewPacient(pacientData);
            System.out.println("Usuário cadastrado: " + username);

            showAlert(Alert.AlertType.INFORMATION, "Success", "User " + name + " successfully created!");

            PatientDatabase.getInstance().getCredentials().put(username, password);
            SceneManager.switchScene(e, "/com/example/clinic/LoginScene/PatientLoginView/login-view.fxml");
        }
    }


    private boolean isSignUpValid(){
        if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty() || nameField.getText().isEmpty() ||
                ageField.getText().isEmpty() || healthPlanComboBox.getValue().isEmpty()) {
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

        if (DoctorDatabase.getInstance().getCredentials().containsKey(usernameField.getText())
                || PatientDatabase.getInstance().getCredentials().containsKey(usernameField.getText())){
            showAlert(Alert.AlertType.ERROR, "Error", "Username already taken.");
            return false;
        }

        return true;
    }


    @FXML
    @Override
    protected void switchToLogin(ActionEvent e){
        SceneManager.switchScene(e, "/com/example/clinic/LoginScene/PatientLoginView/login-view.fxml");
    }
}
