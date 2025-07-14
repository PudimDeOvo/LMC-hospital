package com.example.clinic.HomeSystem.DoctorHome;

import com.example.clinic.Database.AppointmentDatabase.AppointmentDatabase;
import com.example.clinic.SceneManager;
import com.example.clinic.Entities.Appointment.Appointment;
import com.example.clinic.Session.DoctorSession;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.Priority;
import javafx.scene.shape.Circle;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class DoctorHomeController implements Initializable {

    @FXML private Label star1, star2, star3, star4, star5;
    @FXML private Circle profileImage, profileImageInner;
    @FXML private Label doctorNameLabel;
    @FXML private Label specialtyLabel;
    @FXML private Button editProfileButton;
    @FXML private Button logoutButton;
    @FXML private Label welcomeLabel;
    @FXML private Label dateLabel;
    @FXML private VBox todaysAppointments;
    @FXML private Button allAppointmentsButton;

    // Doctor information (in a real app, this would come from a database)
    private String doctorName = DoctorSession.getInstance().getLoggedDoctor().getName();
    private String specialty = DoctorSession.getInstance().getLoggedDoctor().getSpecialty().getDisplayName(); //era toString();
    private int rating = DoctorSession.getInstance().getLoggedDoctor().getStars(); // out of 5 stars

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupDoctorInfo();
        setupCurrentDate();
        setupStarRating();
        loadTodaysAppointments();
    }

    private void setupDoctorInfo() {
        doctorNameLabel.setText(doctorName);
        specialtyLabel.setText(specialty);
        welcomeLabel.setText("Welcome back, " + doctorName);
    }

    private void setupCurrentDate() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        dateLabel.setText("Today is: " + today.format(formatter));
    }

    private void setupStarRating() {
        Label[] stars = {star1, star2, star3, star4, star5};
        for (int i = 0; i < stars.length; i++) {
            if (i < rating) {
                stars[i].setText("★");
            } else {
                stars[i].setText("☆");
            }
        }
    }

    private void loadTodaysAppointments() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        List<Appointment> appointments = AppointmentDatabase.getInstance().getAppointmentsByDate("src/main/database/AppointmentDatabase.csv",
                true,
                DoctorSession.getInstance().getLoggedDoctor().getUsername(),
                today.format(formatter));

        for (Appointment appointment : appointments) {
            Node appointmentNode = createAppointmentNode(appointment);
            todaysAppointments.getChildren().add(appointmentNode);
        }

        // If no appointments, show a message
        if (appointments.isEmpty()) {
            Label noAppointments = new Label("No appointments scheduled for today.");
            noAppointments.getStyleClass().add("no-appointments-message");
            todaysAppointments.getChildren().add(noAppointments);
        }
    }

    private Node createAppointmentNode(Appointment appointment) {
        // Create the appointment card programmatically
        HBox card = new HBox();
        card.setSpacing(12);
        card.setAlignment(Pos.CENTER_LEFT);
        card.getStyleClass().add("appointment-card");

        // User icon (smaller for compact design)
        Circle userIcon = new Circle(18);
        userIcon.getStyleClass().add("user-icon");

        // Patient info section
        VBox patientInfo = new VBox();
        patientInfo.setSpacing(3);

        Label nameLabel = new Label(appointment.getPatient().getName());
        nameLabel.getStyleClass().add("patient-name");

        Label ageLabel = new Label("Age: " + String.valueOf(appointment.getPatient().getAge()));
        ageLabel.getStyleClass().add("appointment-age-compact");

        patientInfo.getChildren().addAll(nameLabel, ageLabel);

        // Right side with time and action
        VBox rightSide = new VBox();
        rightSide.setSpacing(6);
        rightSide.setAlignment(Pos.CENTER_RIGHT);

        Label timeLabel = new Label(appointment.getDate());
        timeLabel.getStyleClass().add("appointment-time-compact");

        Button concludeBtn = new Button("Conclude");
        concludeBtn.getStyleClass().add("view-button");
        concludeBtn.setPrefWidth(100);

        rightSide.getChildren().addAll(timeLabel, concludeBtn);

        // Add spacing to push the right content to the right
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        card.getChildren().addAll(userIcon, patientInfo, spacer, rightSide);
        return card;
    }

    @FXML
    private void switchToEdit(ActionEvent event) {
        System.out.println("Edit Profile clicked");
        SceneManager.switchScene(event, "/com/example/clinic/DoctorHomeScene/EditInfo/doctoredit-view.fxml");
    }

    @FXML
    private void handleLogout(ActionEvent e) {
        System.out.println("Logout clicked");
        DoctorSession.getInstance().setLoggedDoctor(null);
        SceneManager.switchScene(e, "/com/example/clinic/LoginScene/DoctorLoginView/login-view.fxml");
    }

    @FXML
    private void handleAllAppointments(ActionEvent e) {
        SceneManager.switchScene(e, "/com/example/clinic/DoctorHomeScene/Appointments/doctorappointments-view.fxml");
    }

    // Getter methods for accessing private fields (useful for testing or external access)
    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
        if (doctorNameLabel != null) {
            doctorNameLabel.setText(doctorName);
            welcomeLabel.setText("Welcome back, " + doctorName);
        }
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
        if (specialtyLabel != null) {
            specialtyLabel.setText(specialty);
        }
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = Math.max(0, Math.min(5, rating)); // Ensure rating is between 0 and 5
        if (star1 != null) {
            setupStarRating();
        }
    }
}
