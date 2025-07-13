package com.example.clinic.HomeSystem.PatientHome;

import com.example.clinic.Database.AppointmentDatabase.AppointmentDatabase;
import com.example.clinic.Entities.Appointment.Appointment;
import com.example.clinic.Entities.User.Patient;
import com.example.clinic.SceneManager;
import com.example.clinic.Session.PatientSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AppointmentHomeController implements Initializable {

    @FXML private VBox appointmentsContainer;
    @FXML private Button scheduledBtn;
    @FXML private Button concludedBtn;
    @FXML private Button cancelledBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadScheduledAppointments();
    }

    private void loadScheduledAppointments() {
        Patient patient = PatientSession.getCurrentPatient();
        appointmentsContainer.getChildren().clear();

        List<Appointment> appointments = AppointmentDatabase.getInstance()
                .getAppointments("src/main/database/AppointmentDatabase.csv", false, patient.getUsername());

        List<Appointment> scheduled = appointments.stream()
                .filter(a -> !a.isConcluded() && !a.getStatus().equalsIgnoreCase("cancelled"))
                .collect(Collectors.toList());

        for (Appointment appointment : scheduled) {
            Node card = createAppointmentNode(appointment);
            appointmentsContainer.getChildren().add(card);
        }
    }

    private void loadConcludedAppointments() {
        Patient patient = PatientSession.getCurrentPatient();
        appointmentsContainer.getChildren().clear();

        List<Appointment> appointments = AppointmentDatabase.getInstance()
                .getAppointments("src/main/database/AppointmentDatabase.csv", false, patient.getUsername());

        List<Appointment> concluded = appointments.stream()
                .filter(Appointment::isConcluded)
                .collect(Collectors.toList());

        for (Appointment appointment : concluded) {
            Node card = createAppointmentNode(appointment);
            appointmentsContainer.getChildren().add(card);
        }
    }

    private void loadCancelledAppointments() {
        Patient patient = PatientSession.getCurrentPatient();
        appointmentsContainer.getChildren().clear();

        List<Appointment> appointments = AppointmentDatabase.getInstance()
                .getAppointments("src/main/database/AppointmentDatabase.csv", false, patient.getUsername());

        List<Appointment> cancelled = appointments.stream()
                .filter(a -> a.getStatus().equalsIgnoreCase("cancelled"))
                .collect(Collectors.toList());

        for (Appointment appointment : cancelled) {
            Node card = createAppointmentNode(appointment);
            appointmentsContainer.getChildren().add(card);
        }
    }

    private Node createAppointmentNode(Appointment appointment) {
        HBox card = new HBox();
        card.setSpacing(15);
        card.setAlignment(Pos.CENTER_LEFT);
        card.getStyleClass().add("appointment-card");

        // Patient's doctor info
        VBox doctorInfo = new VBox();
        doctorInfo.setSpacing(5);

        Label doctorName = new Label("Doctor: " + appointment.getDoctor().getName());
        doctorName.getStyleClass().add("patient-name");

        Label doctorUsername = new Label("@" + appointment.getDoctor().getUsername());
        doctorUsername.getStyleClass().add("patient-age");

        doctorInfo.getChildren().addAll(doctorName, doctorUsername);

        // Right side with date
        VBox rightSide = new VBox();
        rightSide.setSpacing(10);
        rightSide.setAlignment(Pos.CENTER_RIGHT);

        Label dateLabel = new Label("Date: " + "PRECISA PEGAR A DATA AINDA");
        dateLabel.getStyleClass().add("appointment-date");

        rightSide.getChildren().addAll(dateLabel);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        card.getChildren().addAll(doctorInfo, spacer, rightSide);
        return card;
    }

    @FXML
    public void showScheduledAppointments(ActionEvent e) {
        loadScheduledAppointments();
    }

    @FXML
    public void showConcludedAppointments(ActionEvent e) {
        loadConcludedAppointments();
    }

    @FXML
    public void showCancelledAppointments(ActionEvent e) {
        loadCancelledAppointments();
    }

    @FXML
    public void handleEvaluateDoctor(ActionEvent e) {
        System.out.println("TODO: Open evaluate doctor view.");
        // Implement your logic
    }

    @FXML
    public void handleCancelAppointment(ActionEvent e) {
        System.out.println("Cancel button pressed!");
        SceneManager.switchScene(e, "/com/example/clinic/PatientHomeScene/cancelappointment/PatientCancelAppointment.fxml");
    }

    @FXML
    public void switchToHome(ActionEvent e) {
        SceneManager.switchScene(e, "/com/example/clinic/PatientHomeScene/Home/patienthome-view.fxml");
    }
}
