package com.example.clinic.HomeSystem.PatientHome;

import com.example.clinic.Database.AppointmentDatabase.AppointmentDatabase;
import com.example.clinic.Database.userDatabase.DoctorDatabase;
import com.example.clinic.Entities.Appointment.Appointment;
import com.example.clinic.Entities.User.Doctor;
import com.example.clinic.Session.PatientSession;
import com.example.clinic.Entities.Review.Review;
import com.example.clinic.Database.ReviewDatabase.ReviewDatabase;
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
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReviewDoctorController {

    @FXML private ComboBox<String> doctorComboBox;
    @FXML private Label feedbackLabel;
    @FXML private TextArea reviewText;
    @FXML private HBox starsBox;

    private int selectedStars = 0;

    @FXML
    public void initialize() {
        List<Doctor> doctors = DoctorDatabase.getInstance().getAllDoctors();
        List<String> doctorNames = new ArrayList<>();
        for (Doctor d : doctors) {
            doctorNames.add(d.getName());
        }
        doctorComboBox.setItems(FXCollections.observableArrayList(doctorNames));

        setupStarRating();
    }

    private void setupStarRating() {
        starsBox.getChildren().clear();
        for (int i = 1; i <= 5; i++) {
            final int starValue = i;
            Label star = new Label("☆");
            star.setStyle("-fx-font-size: 30px; -fx-cursor: hand;");
            star.setOnMouseClicked(e -> {
                selectedStars = starValue;
                updateStarDisplay();
            });
            starsBox.getChildren().add(star);
        }
    }

    private void updateStarDisplay() {
        for (int i = 0; i < 5; i++) {
            Label star = (Label) starsBox.getChildren().get(i);
            star.setText(i < selectedStars ? "★" : "☆");
        }
    }

    @FXML
    private void handleSubmit(ActionEvent event) {
        String selectedDoctorName = doctorComboBox.getValue();
        String comment = reviewText.getText();

        feedbackLabel.setText("");
        feedbackLabel.setStyle("-fx-text-fill: #d9534f;");

        if (selectedDoctorName == null || selectedDoctorName.isEmpty()) {
            feedbackLabel.setText("Please select a doctor.");
            return;
        }
        if (selectedStars == 0) {
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
                    bw.write(app.getDoctor().getUsername() + "," +
                            app.getPatient().getUsername() + "," +
                            app.getDate() + "," +
                            app.isConcluded() + "," +
                            app.getMedicalReview() + "," +
                            app.getStatus() + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
                feedbackLabel.setText("Error saving review.");
                feedbackLabel.setStyle("-fx-text-fill: #d9534f;");
                return;
            }
        } else {
            feedbackLabel.setText("No concluded appointment found to review.");
            feedbackLabel.setStyle("-fx-text-fill: #d9534f;");
            return;
        }

        updateDoctorStars(doctorsFile, selectedDoctorName, selectedStars);

        feedbackLabel.setText("Review submitted successfully!");
        feedbackLabel.setStyle("-fx-text-fill: #28a745;");

        Doctor doctor = DoctorDatabase.getInstance().getDoctorByName(selectedDoctorName);
        if (doctor == null) {
            feedbackLabel.setText("Doctor not found.");
            return;
        }

        ReviewDatabase.getInstance().addReview(
                new Review(
                        doctor.getUsername(),
                        comment,
                        java.time.LocalDate.now().toString()
                )
        );

        feedbackLabel.setText("Review submitted successfully!");
        feedbackLabel.setStyle("-fx-text-fill: #28a745;");

        goHome(event);
    }

    private void updateDoctorStars(String filePath, String doctorName, int newStars) {
        List<String> lines = new ArrayList<>();

        try (var br = new java.io.BufferedReader(new java.io.FileReader(filePath))) {
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
                    stars = (stars + newStars) / 2;  // simple average
                }

                lines.add(username + "," + password + "," + name + "," + specialty + "," + stars);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (var bw = new java.io.BufferedWriter(new java.io.FileWriter(filePath))) {
            for (String l : lines) {
                bw.write(l);
                bw.newLine();
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
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/clinic/PatientHomeScene/appointmentHome/appointmenthome-view.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
