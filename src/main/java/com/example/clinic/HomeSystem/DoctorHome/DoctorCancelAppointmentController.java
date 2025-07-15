package com.example.clinic.HomeSystem.DoctorHome;

import com.example.clinic.Database.AppointmentDatabase.AppointmentDatabase;
import com.example.clinic.Entities.Appointment.Appointment;
import com.example.clinic.Entities.User.Doctor;
import com.example.clinic.SceneManager;
import com.example.clinic.Session.DoctorSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class DoctorCancelAppointmentController implements Initializable {
    @FXML private Button backButton;
    @FXML private Button cancelButton;
    @FXML private ScrollPane scrollPane;
    @FXML private VBox appointmentsContainer;
    @FXML private HBox selectedAppointment = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupScrollPane();
        loadAppointmentsNotConcluded();
    }

    private void loadAppointmentsNotConcluded() {
        String doctorUsername = DoctorSession.getInstance().getLoggedDoctor().getUsername();
        List<Appointment> appointments = AppointmentDatabase.getInstance().getAppointments("src/main/database/AppointmentDatabase.csv", true, doctorUsername);

        for(Appointment appointm : appointments){
            if(!appointm.isConcluded() && !"cancelled".equalsIgnoreCase(appointm.getStatus())
            ){
              addAppointment(
                      appointm.getPatient().getName(),
                      appointm.getPatient().getAge(),
                      appointm.getDate(),
                      appointm.getPatient().getUsername()
              );
            }
        }
    }

    private void setupScrollPane() {
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    public void addAppointment(String patientName, int patientAge, String appointmentDate, String patientUsername) {
        HBox appointmentCard = createAppointmentCard(patientName, patientAge, appointmentDate, patientUsername);
        appointmentsContainer.getChildren().add(appointmentCard);
    }

    private HBox createAppointmentCard(String patientName, int patientAge, String appointmentDate, String patientUsername) {
        Button cancelLocalButton = new Button("Cancel");
        HBox appointmentCard = new HBox();
        appointmentCard.getStyleClass().add("appointment-card");
        appointmentCard.setSpacing(15);
        appointmentCard.setUserData(patientUsername);

        // Patient Info
        HBox patientInfo = new HBox();
        patientInfo.getStyleClass().add("patient-info");

        // Patient Details
        VBox patientDetails = new VBox();
        patientDetails.getStyleClass().add("patient-details");

        Label patientNameLabel = new Label(patientName);
        patientNameLabel.getStyleClass().add("patient-name");

        Label patientAgeLabel = new Label("Age " + patientAge + " years");
        patientAgeLabel.getStyleClass().add("patient-age");

        patientDetails.getChildren().addAll(patientNameLabel, patientAgeLabel);
        patientInfo.getChildren().addAll(patientDetails);

        // Appointment Right
        HBox appointmentRight = new HBox();
        appointmentRight.getStyleClass().add("appointment-right");

        Label appointmentDateLabel = new Label("Date: " + appointmentDate);
        appointmentDateLabel.getStyleClass().add("appointment-date");

        cancelLocalButton.getStyleClass().add("cancel-button");
        cancelLocalButton.setOnAction(e -> {
            e.consume(); // Prevent card selection
            cancelAppointment(appointmentCard, patientName, appointmentDate);
        });

        appointmentRight.getChildren().addAll(appointmentDateLabel, cancelLocalButton);

        appointmentCard.getChildren().addAll(patientInfo, appointmentRight);

        // Add click handler for selection
        appointmentCard.setOnMouseClicked(e -> selectAppointment(appointmentCard));

        return appointmentCard;
    }

    private void selectAppointment(HBox appointmentCard) {
        // Remove previous selection
        if (selectedAppointment != null) {
            selectedAppointment.getStyleClass().remove("selected");
        }

        // Add current selection
        appointmentCard.getStyleClass().add("selected");
        selectedAppointment = appointmentCard;

        // Enable cancel button
        cancelButton.setDisable(false);
    }
    @FXML
    private void cancelSelectedAppointment(ActionEvent event) {
        if (selectedAppointment != null) {
            String patientName = ((Label) ((VBox) selectedAppointment.getChildren().get(0)).getChildren().get(0)).getText();
            String appointmentDate = ((Label) selectedAppointment.getChildren().get(1)).getText().replace("Date: ", "");

            cancelAppointment(selectedAppointment, patientName, appointmentDate);

            selectedAppointment = null;
            cancelButton.setDisable(true);
        }
    }

    private void cancelAppointment(HBox appointmentCard, String patientName, String appointmentDate) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel Appointment");
        alert.setHeaderText("Are you sure you want to cancel the appointment?");
        alert.setContentText("Patient: " + patientName + "\n" + "Date: " + appointmentDate);

        String patientUsername = (String) appointmentCard.getUserData();

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            List<Appointment> appointmentsByDate = AppointmentDatabase.getInstance().getAppointmentsByDate("src/main/database/AppointmentDatabase.csv", false, patientUsername, appointmentDate);

            if (!appointmentsByDate.isEmpty()) {
                Appointment appointment = appointmentsByDate.get(0);
                appointment.setStatus("cancelled");
                appointment.setConcluded(true);
                AppointmentDatabase.getInstance().updateAppointment(appointment);
            }

            System.out.println("Appointment cancelled");
            appointmentsContainer.getChildren().remove(appointmentCard);
            if(appointmentsContainer.getChildren().isEmpty()) {
                showEmptyState();
            }
        }
    }

    private void showEmptyState() {
        VBox emptyState = new VBox();
        emptyState.getStyleClass().add("empty-state");

        Label emptyIcon = new Label("📅");
        emptyIcon.getStyleClass().add("empty-icon");

        Label emptyText = new Label("No appointments to cancel");
        emptyText.getStyleClass().add("empty-text");

        Label emptySubtext = new Label("All appointments have been cancelled");
        emptySubtext.getStyleClass().add("empty-subtext");

        emptyState.getChildren().addAll(emptyIcon, emptyText, emptySubtext);
        appointmentsContainer.getChildren().add(emptyState);
    }

    public void switchToBack(ActionEvent event) {
        System.out.println("Switching to my appointments");
        SceneManager.switchScene(event, "/com/example/clinic/DoctorHomeScene/Appointments/OngoingAppointment/doctorappointments-view.fxml");
    }
}
