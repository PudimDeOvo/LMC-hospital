package com.example.clinic.HomeSystem.DoctorHome;

import com.example.clinic.Database.userDatabase.DoctorDatabase;
import com.example.clinic.Entities.User.Doctor;
import com.example.clinic.SceneManager;
import com.example.clinic.Session.DoctorSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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

        if (!oldDoctorData.getUsername().equals(newDoctorUsernameField.getText()) &&
                DoctorDatabase.getInstance().getDoctor(newDoctorUsernameField.getText()) != null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Username já em uso");
            alert.setContentText("Por favor, escolha outro username.");
            alert.showAndWait();
            return;
        }

        String[] newDoctorData = { newDoctorUsernameField.getText(), newDoctorPasswordField.getText(), newDoctorNameField.getText() };
        DoctorDatabase.getInstance().updateDoctor(oldDoctorData, newDoctorData);

        Doctor updatedDoctor = DoctorDatabase.getInstance().getDoctor(newDoctorUsernameField.getText());
        DoctorSession.getInstance().setLoggedDoctor(updatedDoctor);

        System.out.println("Changes saved successfully.");
    }

    @FXML
    private void switchToBack(ActionEvent event) {
        SceneManager.switchScene(event, "/com/example/clinic/DoctorHomeScene/Home/doctorhome-view.fxml");
    }
}
