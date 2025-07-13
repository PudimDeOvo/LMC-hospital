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

public class DoctorSignUpController extends SignUpController{

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField nameField;
    @FXML
    private ComboBox<String> SpecialtyComboBox;


    @FXML
    private void initialize(){
        SpecialtyComboBox.getItems().addAll(
                "Cardiology",
                "Dermatology",
                "Endocrinology",
                "Gastroenterology",
                "Geriatrics",
                "Gynecology and Obstetrics",
                "Neurology",
                "Oncology",
                "Orthopedics",
                "Pediatrics",
                "Psychiatry",
                "General Surgery",
                "Urology",
                "Ophthalmology",
                "Otorhinolaryngology",
                "Radiology",
                "Anesthesiology"
        );
    }

    @FXML
    @Override
    protected void handleSignUpButtonAction(ActionEvent e){
        String username = usernameField.getText();
        String password = passwordField.getText();
        String name = nameField.getText();
        String specialty = SpecialtyComboBox.getValue();

        if (isSignUpValid()){
            String[] doctorData = {username, password, name, specialty};
            DoctorDatabase.getInstance().addNewDoctor(doctorData);
            System.out.println("Usuário cadastrado: " + username);

            showAlert(Alert.AlertType.INFORMATION, "Success", "User " + name + " successfully created!");

            DoctorDatabase.getInstance().getCredentials().put(username, password);
            SceneManager.switchScene(e, "/com/example/clinic/LoginScene/DoctorLoginView/login-view.fxml");
        }
    }


    private boolean isSignUpValid(){
        if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty() || nameField.getText().isEmpty()
                || SpecialtyComboBox.getValue().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Fill out all camps.");
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
        SceneManager.switchScene(e, "/com/example/clinic/LoginScene/DoctorLoginView/login-view.fxml");
    }
}
