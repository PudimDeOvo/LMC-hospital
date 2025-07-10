package com.example.clinic.homeSystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class DoctorHomeController {
    @FXML
    protected TextField nameField;

    @FXML
    protected TextField specialtyField;

    @FXML
    protected void switchToMyAppointments(ActionEvent event) {
        String FILE_PATH = "/com/example/clinic/DoctorHomeScene/appointments/doctorappointments-view.fxml";

        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(FILE_PATH)));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);

            stage.show();
        } catch (IOException e) {
            System.err.println("Erro ao carregar a tela de consultas do médico (doctorappointments-view.fxml):");
            e.printStackTrace();
        }
    }
}
