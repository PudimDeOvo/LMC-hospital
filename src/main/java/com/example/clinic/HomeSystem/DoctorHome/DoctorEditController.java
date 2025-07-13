package com.example.clinic.HomeSystem.DoctorHome;

import com.example.clinic.Database.UserDatabase.DoctorDatabase;
import com.example.clinic.Entities.User.Doctor;
import com.example.clinic.SceneManagerPack.SceneManager;
import com.example.clinic.Session.DoctorSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class DoctorEditController {
    @FXML private Button backButton;
    @FXML private Button saveButton;
    @FXML private TextField newDoctorNameField;
    @FXML private TextField newDoctorUsernameField;
    @FXML private TextField newDoctorPasswordField;

    @FXML
    private void saveChanges(ActionEvent event) {
        Doctor oldDoctorData = DoctorSession.getInstance().getLoggedDoctor();
        String[] newDoctorData = { newDoctorUsernameField.getText(), newDoctorPasswordField.getText(), newDoctorNameField.getText() };
        DoctorDatabase.getInstance().updateDoctor(oldDoctorData, newDoctorData);

        System.out.println("Changes saved successfully.");
    }

    @FXML
    private void switchToBack(ActionEvent event) {
        SceneManager.switchScene(event, "/com/example/clinic/DoctorHomeScene/Home/doctorhome-view.fxml");
    }
}
