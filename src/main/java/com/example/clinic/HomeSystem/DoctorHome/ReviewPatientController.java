package com.example.clinic.HomeSystem.DoctorHome;

import com.example.clinic.Database.AppointmentDatabase.AppointmentDatabase;
import com.example.clinic.Entities.Appointment.Appointment;
import com.example.clinic.SceneManager;
import com.example.clinic.Session.AppointmentSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ReviewPatientController implements Initializable {
    @FXML private Button backButton;
    @FXML private Button saveButton;

    @FXML private TextField dateField;
    @FXML private TextField patientNameField;
    @FXML private TextField patientAgeField;
    @FXML private TextField diagnosisField;
    @FXML private TextArea observationsArea;

    private Appointment appointment;


    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        appointment = AppointmentSession.getInstance().getAppointment();

        if (appointment != null) {
            dateField.setText(appointment.getDate());
            patientNameField.setText(appointment.getPatient().getName());
            patientAgeField.setText(String.valueOf(appointment.getPatient().getAge()));
        }
    }

    @FXML
    private void saveReview(ActionEvent event) {
        if(appointment != null) {
            String diagnosis = diagnosisField.getText();
            String observations = observationsArea.getText();
            String review = "Diagnosis: " + diagnosis + "Observations: " + observations;

            appointment.setMedicalReview(review);
            AppointmentSession.getInstance().setAppointment(appointment);
            AppointmentDatabase.getInstance().updateAppointment(appointment);

            System.out.println("Review saved successfully.");
            SceneManager.switchScene(event, "/com/example/clinic/DoctorHomeScene/Appointments/OngoingAppointment/doctorappointments-view.fxml");
        } else {
            System.out.println("No appointment selected.");
        }
    }

    @FXML
    private void switchToMyAppointments(ActionEvent event) {
        System.out.println("Clicked on Back to My Appointments");
        SceneManager.switchScene(event, "/com/example/clinic/DoctorHomeScene/Appointments/OngoingAppointment/doctorappointments-view.fxml");
    }
}
