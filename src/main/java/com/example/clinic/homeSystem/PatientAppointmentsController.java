package com.example.clinic.homeSystem;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.*;
import java.io.*;
import com.example.clinic.entities.user.Doctor;
import com.example.clinic.entities.appointment.Appointment;
import com.example.clinic.Database.AppointmentDatabase.AppointmentDatabase;
import com.example.clinic.Database.userDatabase.DoctorDatabase;
import com.example.clinic.entities.user.Patient;



public class PatientAppointmentsController {

    @FXML private ComboBox<String> doctorComboBox;
    @FXML private DatePicker datePicker;
    @FXML private TextField timeField;

    @FXML
    public void initialize() {
        List<String> doctorNames = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("src/main/database/DoctorDatabase.csv"))) {
            br.readLine(); // skip header

            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 5) {
                    String username = data[0].trim();
                    String name = data[2].trim();
                    String displayName = "Dr. " + name + "(" + username + ")";
                    doctorNames.add(displayName);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        doctorComboBox.setItems(FXCollections.observableArrayList(doctorNames));
    }


    @FXML
    private void handleConfirm(ActionEvent event) {
        //logic
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
