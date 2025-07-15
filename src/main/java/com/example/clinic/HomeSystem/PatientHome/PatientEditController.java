package com.example.clinic.HomeSystem.PatientHome;

import com.example.clinic.Database.userDatabase.PatientDatabase;
import com.example.clinic.Entities.HealthInsurancePlan;
import com.example.clinic.Entities.User.Patient;
import com.example.clinic.Session.PatientSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import com.example.clinic.SceneManager;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class PatientEditController implements Initializable {

    @FXML private TextField newPatientNameField;
    @FXML private TextField newPatientUsernameField;
    @FXML private PasswordField newPatientPasswordField;
    @FXML private TextField newPatientAgeField;
    @FXML private ChoiceBox<HealthInsurancePlan> newPatientHealthPlanChoiceBox;
    @FXML private Button saveButton;
    @FXML private Button backButton;

    private Patient currentPatient;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        newPatientHealthPlanChoiceBox.getItems().addAll(HealthInsurancePlan.values());

        currentPatient = PatientSession.getCurrentPatient();

        if (currentPatient != null) {
            populateFields(currentPatient);
        } else {
            showAlert(Alert.AlertType.ERROR, "No patient is currently logged in.");
        }
    }

    private void populateFields(Patient patient) {
        newPatientNameField.setText(patient.getName());
        newPatientUsernameField.setText(patient.getUsername());
        newPatientAgeField.setText(String.valueOf(patient.getAge()));
        newPatientHealthPlanChoiceBox.setValue(patient.getHealthPlan());
    }

    @FXML
    private void saveChanges(ActionEvent event) {
        if (currentPatient == null) {
            showAlert(Alert.AlertType.ERROR, "No patient session found.");
            return;
        }

        String newName = newPatientNameField.getText().trim();
        String newUsername = newPatientUsernameField.getText().trim();
        String newPassword = newPatientPasswordField.getText().trim();
        String ageText = newPatientAgeField.getText().trim();
        HealthInsurancePlan selectedPlan = newPatientHealthPlanChoiceBox.getValue();

        if (newName.isEmpty() || newUsername.isEmpty() || ageText.isEmpty() || selectedPlan == null) {
            showAlert(Alert.AlertType.WARNING, "Please fill in all required fields.");
            return;
        }

        int newAge;
        try {
            newAge = Integer.parseInt(ageText);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid age format.");
            return;
        }

        String oldUsername = currentPatient.getUsername();

        currentPatient.setName(newName);
        currentPatient.setUsername(newUsername);
        currentPatient.setAge(newAge);
        currentPatient.setHealthPlan(selectedPlan);

        PatientSession.setCurrentPatient(currentPatient);

        boolean success = updatePatientInCSV(oldUsername, newUsername, newPassword, newName, newAge, selectedPlan);

        if (success) {
            PatientDatabase db = PatientDatabase.getInstance();
            db.reloadCredentials();
            showAlert(Alert.AlertType.INFORMATION, "Profile updated successfully.");
        } else {
            showAlert(Alert.AlertType.ERROR, "Failed to update profile.");
        }
    }

    @FXML
    private void switchToBack(ActionEvent event) {
        System.out.println("Back button pressed.");
        SceneManager.switchScene(event, "/com/example/clinic/PatientHomeScene/Home/patienthome-view.fxml");
    }

    private boolean updatePatientInCSV(String oldUsername, String newUsername, String newPassword,
                                       String newName, int newAge, HealthInsurancePlan newPlan) {
        File inputFile = new File("src/main/database/PatientDatabase.csv");
        File tempFile = new File("src/main/database/TempPatientDatabase.csv");

        boolean updated = false;

        try (
                BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))
        ) {
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    writer.write(line);
                    writer.newLine();
                    isFirstLine = false;
                    continue;
                }

                String[] data = line.split(",");
                if (data.length > 0 && data[0].trim().equals(oldUsername)) {
                    // Replace line with updated data
                    String passwordToWrite = newPassword.isEmpty() ? data[1].trim() : newPassword;
                    String updatedLine = String.join(",", newUsername, passwordToWrite, newName,
                            String.valueOf(newAge), formatHealthPlan(newPlan));
                    writer.write(updatedLine);
                    writer.newLine();
                    updated = true;
                } else {
                    writer.write(line);
                    writer.newLine();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        if (updated) {
            if (!inputFile.delete() || !tempFile.renameTo(inputFile)) {
                System.err.println("Error replacing file.");
                return false;
            }
        }

        return updated;
    }

    private String formatHealthPlan(HealthInsurancePlan plan) {
        return plan.name().toLowerCase().replace("_", " ");
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle("Edit Profile");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
