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

    private void setActiveFilter(Button activeButton) {
        List<Button> allButtons = List.of(scheduledBtn, concludedBtn, cancelledBtn);

        for (Button button : allButtons) {
            button.getStyleClass().remove("filter-button-active");
        }

        if (!activeButton.getStyleClass().contains("filter-button-active")) {
            activeButton.getStyleClass().add("filter-button-active");
        }
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

        VBox doctorInfo = new VBox();
        doctorInfo.setSpacing(5);

        Label doctorName = new Label("Doctor: " + appointment.getDoctor().getName());
        doctorName.getStyleClass().add("patient-name");

        Label doctorUsername = new Label("@" + appointment.getDoctor().getUsername());
        doctorUsername.getStyleClass().add("patient-age");

        doctorInfo.getChildren().addAll(doctorName, doctorUsername);

        VBox rightSide = new VBox();
        rightSide.setSpacing(10);
        rightSide.setAlignment(Pos.CENTER_RIGHT);

        Label dateLabel = new Label("Date: " + appointment.getDate());
        dateLabel.getStyleClass().add("appointment-date");

        rightSide.getChildren().addAll(dateLabel);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        card.getChildren().addAll(doctorInfo, spacer, rightSide);
        return card;
    }

    @FXML
    public void showScheduledAppointments(ActionEvent e) {
        System.out.println("Mostrar marcados");
        loadScheduledAppointments();
        setActiveFilter(scheduledBtn);
    }

    @FXML
    public void showConcludedAppointments(ActionEvent e) {
        System.out.println("Mostrar concluídos");
        loadConcludedAppointments();
        setActiveFilter(concludedBtn);
    }

    @FXML
    public void showCancelledAppointments(ActionEvent e) {
        System.out.println("Mostrar cancelados");
        loadCancelledAppointments();
        setActiveFilter(cancelledBtn);
    }

    @FXML
    public void handleEvaluateDoctor(ActionEvent e) {
        System.out.println("apertou no botao de avaliar");
        SceneManager.switchScene(e, "/com/example/clinic/PatientHomeScene/ReviewDoctor/reviewdoctor-view.fxml");
    }

    public void handleCreateAppointment(ActionEvent e){
        System.out.println("apertou no botao de criar consulta");
        SceneManager.switchScene(e, "/com/example/clinic/PatientHomeScene/PacientAppointment/patientcreateappointment-view.fxml");
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
