package com.example.clinic.HomeSystem.PatientHome;

import com.example.clinic.Database.AppointmentDatabase.AppointmentDatabase;
import com.example.clinic.Database.userDatabase.DoctorDatabase;
import com.example.clinic.Entities.Appointment.Appointment;
import com.example.clinic.Entities.User.Doctor;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import com.example.clinic.Session.PatientSession;

import java.io.*;
import java.util.*;

public class ReviewDoctorController {

    @FXML private ComboBox<String> doctorComboBox;
    @FXML private ComboBox<Integer> starsComboBox;
    @FXML private Label feedbackLabel;
    @FXML private TextArea reviewText;

    @FXML
    public void initialize() {
        List<Doctor> doctors = DoctorDatabase.getInstance().getAllDoctors();
        List<String> doctorNames = new ArrayList<>();
        for (Doctor d : doctors) {
            doctorNames.add(d.getName());
        }
        doctorComboBox.setItems(FXCollections.observableArrayList(doctorNames));

        starsComboBox.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5));
    }

    @FXML
    private void handleSubmit(ActionEvent event) {
        String selectedDoctorName = doctorComboBox.getValue();
        Integer selectedStars = starsComboBox.getValue();
        String comment = reviewText.getText();

        feedbackLabel.setText("");
        feedbackLabel.setStyle("-fx-text-fill: #d9534f;");

        if (selectedDoctorName == null || selectedDoctorName.isEmpty()) {
            feedbackLabel.setText("Please select a doctor.");
            return;
        }
        if (selectedStars == null) {
            feedbackLabel.setText("Please rate with stars.");
            return;
        }
        if (comment == null || comment.trim().isEmpty()) {
            feedbackLabel.setText("Please enter a comment.");
            return;
        }

        String patientUsername = PatientSession.getCurrentPatient().getUsername();
        String appointmentsFile = "src/main/database/AppointmentDatabase.csv";
        String doctorsFile = "src/main/database/DoctorDatabase.csv";

        List<Appointment> appointments = AppointmentDatabase.getInstance()
                .getAppointments(appointmentsFile, false, patientUsername);

        boolean found = false;
        for (Appointment app : appointments) {
            if (app.getDoctor().getName().equalsIgnoreCase(selectedDoctorName)
                    && app.isConcluded()
                    && app.getMedicalReview().equalsIgnoreCase("none")) {
                app.setMedicalReview(comment);
                found = true;
                break;
            }
        }

        if (found) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(appointmentsFile))) {
                bw.write("doctor,patient,date,concluded,medicalReview,status\n");
                for (Appointment app : appointments) {
                    bw.write(app.getDoctor().getUsername() + ","
                            + app.getPatient().getUsername() + ","
                            + app.getDate() + ","
                            + app.isConcluded() + ","
                            + app.getMedicalReview() + ","
                            + app.getStatus() + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No concluded appointment found for review.");
        }
        feedbackLabel.setText("Review submitted successfully!");
        feedbackLabel.setStyle("-fx-text-fill: #28a745;");

        updateDoctorStars(doctorsFile, selectedDoctorName, selectedStars);

        goHome(event);
    }

    private void updateDoctorStars(String filePath, String doctorName, int newStars) {
        List<String> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String header = br.readLine();
            lines.add(header);

            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                String username = data[0].trim();
                String password = data[1].trim();
                String name = data[2].trim();
                String specialty = data[3].trim();
                int stars = Integer.parseInt(data[4].trim());

                if (name.equalsIgnoreCase(doctorName)) {
                    // (old + new) / 2 dps vejo como faz uma media melhor
                    stars = (stars + newStars) / 2;
                }

                lines.add(username + "," + password + "," + name + "," + specialty + "," + stars);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (String l : lines) {
                bw.write(l + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBack(ActionEvent event) {
        goHome(event);
    }

    private void goHome(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/clinic/PatientHomeScene/home/patienthome.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}