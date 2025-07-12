package com.example.clinic.homeSystem;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PatientCancelAppointmentController {

    @FXML private ListView<String> appointmentsList;

    @FXML
    public void initialize() {
        appointmentsList.setItems(FXCollections.observableArrayList(
                "12/07/2025 - 14:30 - Dr. JYP",
                "18/07/2025 - 10:00 - Dra. Ryujin da Silva"
        ));
        //teste hihi
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        String selected = appointmentsList.getSelectionModel().getSelectedItem();
        if (selected != null) {
            System.out.println("Cancelando: " + selected);
            // ai remove ne
            appointmentsList.getItems().remove(selected);
        }
    }

    @FXML
    private void handleBack(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/clinic/PatientHomeScene/patienthome.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
