package com.example.clinic.homeSystem;

import com.example.clinic.Database.AppointmentDatabase.AppointmentDatabase;
import com.example.clinic.Database.userDatabase.DoctorDatabase;
import com.example.clinic.entities.appointment.Appointment;
import com.example.clinic.entities.user.Doctor;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class DoctorMyAppointmentsController implements Initializable {
    @FXML private VBox appointmentsContainer;
    @FXML private Button notConcludedBtn;
    @FXML private Button concludedBtn;
    @FXML private Button allBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadAppointments();
    }

    private void loadAppointments() {
        Doctor doctor = DoctorDatabase.getInstance().getDoctor("nayeon");
        appointmentsContainer.getChildren().clear();

        List<Appointment> appointments = AppointmentDatabase.getInstance().getAppointments("src/main/database/AppointmentDatabase.csv", true, doctor.getUsername());
        for (Appointment appointment : appointments) {
            Node appointmentNode = createAppointmentNode(appointment);
            appointmentsContainer.getChildren().add(appointmentNode);
        }
    }

    private Node createAppointmentNode(Appointment appointment) {
        // Create the appointment card programmatically
        HBox card = new HBox();
        card.setSpacing(15);
        card.setAlignment(Pos.CENTER_LEFT);
        card.getStyleClass().add("appointment-card");

        // User icon (placeholder circle)
        Circle userIcon = new Circle(25);
        userIcon.getStyleClass().add("user-icon");

        // Patient info section
        VBox patientInfo = new VBox();
        patientInfo.setSpacing(5);

        Label nameLabel = new Label(appointment.getPatient().getName());
        nameLabel.getStyleClass().add("patient-name");

        Label ageLabel = new Label("Age    " + appointment.getPatient().getAge() + " years");
        ageLabel.getStyleClass().add("patient-age");

        patientInfo.getChildren().addAll(nameLabel, ageLabel);

        // Right side with date and conclude button
        VBox rightSide = new VBox();
        rightSide.setSpacing(10);
        rightSide.setAlignment(Pos.CENTER_RIGHT);

        Label dateLabel = new Label("Date: " + appointment.getDate());
        dateLabel.getStyleClass().add("appointment-date");

        Button concludeBtn = new Button("Conclude");
        concludeBtn.getStyleClass().add("conclude-button");
        concludeBtn.setPrefWidth(120);

        rightSide.getChildren().addAll(dateLabel, concludeBtn);

        // Add spacing to push the conclude button to the right
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        card.getChildren().addAll(userIcon, patientInfo, spacer, rightSide);
        return card;
    }
}
