package com.example.clinic.HomeSystem.PatientHome;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import com.example.clinic.Entities.User.Doctor;
import com.example.clinic.Entities.Appointment.Appointment;
import com.example.clinic.Database.AppointmentDatabase.AppointmentDatabase;
import com.example.clinic.Database.userDatabase.DoctorDatabase;
import com.example.clinic.Entities.User.Patient;
import com.example.clinic.Session.PatientSession;
import com.example.clinic.Entities.MedicalSpecialty;
import com.example.clinic.Entities.HealthInsurancePlan;
import com.example.clinic.Entities.Review.Review;
import com.example.clinic.Database.ReviewDatabase.ReviewDatabase;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class CreateAppointmentsController {

    @FXML private VBox doctorsListVBox;
    @FXML private ComboBox<MedicalSpecialty> specialtyFilterComboBox;
    @FXML private ComboBox<String> starsFilterComboBox;
    @FXML private DatePicker datePicker;
    @FXML private ComboBox<String> timeComboBox;
    @FXML private Label feedbackLabel;
    @FXML private Button clearFiltersButton;

    private List<Doctor> allDoctors;
    private Patient currentPatient;

    @FXML
    public void initialize() {
        currentPatient = PatientSession.getCurrentPatient();
        if (currentPatient == null) {
            System.out.println("No patient session found.");
            return;
        }

        HealthInsurancePlan plan = currentPatient.getHealthPlan();
        if (plan == null) {
            System.out.println("No insurance plan found for patient.");
            return;
        }

        allDoctors = DoctorDatabase.getInstance().getAllDoctors();

        specialtyFilterComboBox.setItems(FXCollections.observableArrayList(plan.getCoveredSpecialties()));
        specialtyFilterComboBox.setOnAction(e -> filterAndDisplayDoctors());

        starsFilterComboBox.setItems(FXCollections.observableArrayList("0","1","2","3","4","5"));
        starsFilterComboBox.setValue("0");
        starsFilterComboBox.setOnAction(e -> filterAndDisplayDoctors());

        clearFiltersButton.setOnAction(e -> {
            specialtyFilterComboBox.setValue(null);
            starsFilterComboBox.setValue("0");
            filterAndDisplayDoctors();
        });

        populateTimeCombo();

        filterAndDisplayDoctors();
    }

    private void populateTimeCombo() {
        ObservableList<String> times = FXCollections.observableArrayList();
        for (int h = 7; h <= 17; h++) {
            times.add(String.format("%02d:00", h));
            if (h != 17) times.add(String.format("%02d:30", h));
        }
        timeComboBox.setItems(times);
    }


    private void filterAndDisplayDoctors() {
        MedicalSpecialty selectedSpecialty = specialtyFilterComboBox.getValue();

        int minStars;
        try {
            minStars = Integer.parseInt(starsFilterComboBox.getValue());
        } catch (NumberFormatException | NullPointerException e) {
            minStars = 0;
        }
        final int finalMinStars = minStars;

        List<Doctor> filtered = allDoctors.stream()
                .filter(doc -> selectedSpecialty == null || doc.getSpecialty().equals(selectedSpecialty))
                .filter(doc -> {
                    double avgStars = getAverageStarsForDoctor(doc.getUsername());
                    return finalMinStars == 0 || avgStars >= finalMinStars;
                })
                .collect(Collectors.toList());

        displayDoctors(filtered);
    }

    private double getAverageStarsForDoctor(String doctorUsername) {
        Doctor doc = DoctorDatabase.getInstance().getDoctorByName(doctorUsername);
        if (doc != null) {
            return doc.getStars();
        }
        return 0;
    }


    private void displayDoctors(List<Doctor> doctors) {
        doctorsListVBox.getChildren().clear();

        for (Doctor doc : doctors) {
            HBox doctorCard = createDoctorCard(doc);
            doctorsListVBox.getChildren().add(doctorCard);
        }
    }

    private HBox createDoctorCard(Doctor doctor) {
        HBox card = new HBox(15);
        card.getStyleClass().add("doctor-card");

        VBox infoBox = new VBox(5);
        Text name = new Text(doctor.getName());
        name.getStyleClass().add("doctor-name");
        Text specialty = new Text(doctor.getSpecialty().toString());
        specialty.getStyleClass().add("doctor-specialty");

        // ★ (U+2605)
        double avgStars = getAverageStarsForDoctor(doctor.getUsername());
        Text starsText = new Text("★".repeat((int)Math.round(avgStars)));
        starsText.getStyleClass().add("doctor-stars");

        boolean isOnWaitingList = checkIfPatientOnWaitingList(doctor);
        Label waitingLabel = null;
        if (isOnWaitingList) {
            waitingLabel = new Label("On waiting list");
            waitingLabel.getStyleClass().add("waiting-list-label");
        }

        infoBox.getChildren().addAll(name, specialty, starsText);
        if (waitingLabel != null) infoBox.getChildren().add(waitingLabel);

        // Book button
        Button bookBtn = new Button("Book");
        bookBtn.getStyleClass().add("book-button");
        bookBtn.setOnAction(e -> handleBookDoctor(doctor));

        // mostra as reviews
        Button reviewBtn = new Button("See review");
        reviewBtn.getStyleClass().add("review-button");
        reviewBtn.setOnAction(e -> showRecentReviewsPopup(doctor.getUsername()));

        card.getChildren().addAll(infoBox, bookBtn, reviewBtn);
        return card;
    }

    private void showRecentReviewsPopup(String doctorUsername) {
        List<Review> reviews = ReviewDatabase.getInstance().getReviewForDoctor(doctorUsername);

        reviews.sort((r1, r2) -> r2.getDate().compareTo(r1.getDate()));

        List<Review> recentReviews = reviews.stream().limit(3).collect(Collectors.toList());

        StringBuilder sb = new StringBuilder();
        if (recentReviews.isEmpty()) {
            sb.append("No reviews available for this doctor yet.");
        } else {
            for (Review review : recentReviews) {
                sb.append(review.getDate()).append(" - ").append(review.getComment()).append("\n\n");
            }
        }

        TextArea textArea = new TextArea(sb.toString());
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setPrefWidth(400);
        textArea.setPrefHeight(200);

        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Recent Reviews");
        dialog.getDialogPane().setContent(textArea);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

        dialog.showAndWait();
    }


    private boolean checkIfPatientOnWaitingList(Doctor doctor) {
        List<Appointment> apps = AppointmentDatabase.getInstance()
                .getAppointments("src/main/database/AppointmentDatabase.csv", false, currentPatient.getUsername());
        return apps.stream()
                .anyMatch(app -> app.getDoctor().getUsername().equals(doctor.getUsername())
                        && "waiting".equalsIgnoreCase(app.getStatus())
                        && !app.isConcluded());
    }

    private void handleBookDoctor(Doctor doctor) {
        feedbackLabel.setText("");
        feedbackLabel.setStyle("-fx-text-fill: #d9534f;");

        LocalDate datePicked = datePicker.getValue();
        String time = timeComboBox.getValue();

        if (datePicked == null) {
            feedbackLabel.setText("Please select a date.");
            return;
        }
        if (time == null) {
            feedbackLabel.setText("Please select a time.");
            return;
        }

        String dateTime = datePicked.toString() + " " + time;

        List<Appointment> existingAppointments = AppointmentDatabase.getInstance()
                .getAppointments("src/main/database/AppointmentDatabase.csv", true, doctor.getUsername());

        long activeCount = existingAppointments.stream()
                .filter(app -> !"cancelled".equalsIgnoreCase(app.getStatus()))
                .filter(app -> !app.isConcluded())
                .count();

        Appointment newAppointment;
        if (activeCount >= 3) {
            newAppointment = new Appointment(doctor, currentPatient, dateTime, false, "none", "waiting");
            AppointmentDatabase.getInstance().addAppointment("src/main/database/AppointmentDatabase.csv", newAppointment);
            showConfirmation("This doctor is fully booked. You have been added to the waiting list.");
        } else {
            newAppointment = new Appointment(doctor, currentPatient, dateTime, false, "none", "active");
            AppointmentDatabase.getInstance().addAppointment("src/main/database/AppointmentDatabase.csv", newAppointment);
            showConfirmation("Appointment booked successfully!");
        }
        goHome(null);
    }

    private void showConfirmation(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation message");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleBack(ActionEvent event) {
        goHome(event);
    }

    private void goHome(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/clinic/PatientHomeScene/appointmentHome/appointmenthome-view.fxml"));
            Stage stage;
            if (event != null) {
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            } else {
                stage = (Stage) doctorsListVBox.getScene().getWindow();
            }
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
