package com.example.clinic.HomeSystem.DoctorHome;

import com.example.clinic.Database.AppointmentDatabase.AppointmentDatabase;
import com.example.clinic.SceneManager;
import com.example.clinic.Entities.Appointment.Appointment;
import com.example.clinic.Entities.User.Doctor;
import com.example.clinic.Session.AppointmentSession;
import com.example.clinic.Session.DoctorSession;
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
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class DoctorMyAppointmentsController implements Initializable {
    @FXML private VBox appointmentsContainer;
    @FXML private Button notConcludedBtn;
    @FXML private Button concludedBtn;
    @FXML private Button allBtn;
    @FXML private Button cancelAppointmentBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadAppointments();
    }

    private void loadAppointments() {
        Doctor doctor = DoctorSession.getInstance().getLoggedDoctor();
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
        concludeBtn.setOnAction(event -> {
            System.out.println("Conclude Appointment clicked for: " + appointment.getPatient().getName());
            appointment.setConcluded(true);
            AppointmentDatabase.getInstance().updateAppointment(appointment);
            AppointmentSession.getInstance().setAppointment(appointment);

            SceneManager.switchScene(event, "/com/example/clinic/DoctorHomeScene/Appointments/ReviewPatient/reviewpatient-view.fxml");

            loadAppointments(); // carrega as consultsa depois da avaliacao
        });

        rightSide.getChildren().addAll(dateLabel, concludeBtn);

        // Add spacing to push the conclude button to the right
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        card.getChildren().addAll(userIcon, patientInfo, spacer, rightSide);
        return card;
    }

    @FXML
    private void loadAppointmentsByStatus(boolean concluded) {
        Doctor doctor = DoctorSession.getInstance().getLoggedDoctor();
        appointmentsContainer.getChildren().clear();

        List<Appointment> appointments = AppointmentDatabase.getInstance().getAppointments("src/main/database/AppointmentDatabase.csv", true, doctor.getUsername());

        for (Appointment appointment : appointments) {
            if (appointment.isConcluded() == concluded) {
                Node appointmentNode = createAppointmentNode(appointment);
                appointmentsContainer.getChildren().add(appointmentNode);
            }
        }
    }

    // add so pra ter como saber onde o doutor ta clicando
    private void setActiveFilter(Button activeButton) {
        List<Button> allButtons = List.of(notConcludedBtn, concludedBtn, allBtn);

        for (Button button : allButtons) {
            button.getStyleClass().remove("filter-button-active");
        }

        if (!activeButton.getStyleClass().contains("filter-button-active")) {
            activeButton.getStyleClass().add("filter-button-active");
        }
    }

    @FXML
    public void showNotConcludedAppointments(ActionEvent e) {
        loadAppointmentsByStatus(false);
        setActiveFilter(notConcludedBtn);
    }

    @FXML
    public void showConcludedAppointments(ActionEvent e) {
        loadAppointmentsByStatus(true);
        setActiveFilter(concludedBtn);
    }

    @FXML
    public void showAllAppointments(ActionEvent e) {
        loadAppointments();
        setActiveFilter(allBtn);
    }

    @FXML
    private void switchToCancelAppointment(ActionEvent event) {
        System.out.println("Cancel Appointment clicked");
        SceneManager.switchScene(event, "/com/example/clinic/DoctorHomeScene/Appointments/CancelAppointment/cancelledappointment-view.fxml");
    }

    @FXML
    public void switchToHome(ActionEvent e){
        SceneManager.switchScene(e, "/com/example/clinic/DoctorHomeScene/Home/doctorhome-view.fxml");
    }
}
