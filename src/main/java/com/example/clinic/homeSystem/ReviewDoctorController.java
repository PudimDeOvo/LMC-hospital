package com.example.clinic.homeSystem;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ReviewDoctorController {

    @FXML private ComboBox<String> doctorComboBox;
    @FXML private TextArea reviewText;

    @FXML
    public void initialize() {
        doctorComboBox.setItems(FXCollections.observableArrayList(
                "Dr. JYP", "Dra. Ryujin da Silva", "Dra. Mina"
        ));
    }

    @FXML
    private void handleSubmit(ActionEvent event) {
        String doctor = doctorComboBox.getValue();
        String comment = reviewText.getText();
        System.out.println("Avaliação para " + doctor + ": " + comment);

        // salvar ne

        goHome(event);
    }

    @FXML
    private void handleBack(ActionEvent event) {
        goHome(event);
    }

    private void goHome(ActionEvent event) {
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
