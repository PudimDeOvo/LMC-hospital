package com.example.clinic.registerSystem;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


public class SignUpController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField ageField;
    @FXML
    private TextField healthPlanField;

    @FXML
    protected void handleSignUpButtonAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String name = nameField.getText();
        String healthPlan = healthPlanField.getText();

        if (username.isEmpty() || password.isEmpty() || name.isEmpty() || ageField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erro no Formulário", "Por favor, preencha todos os campos obrigatórios.");
            return;
        }

        int age;
        try {
            age = Integer.parseInt(ageField.getText());
        } catch (NumberFormatException ex) {
            showAlert(Alert.AlertType.ERROR, "Erro no Formulário", "A idade deve ser um número válido.");
            return;
        }

        String[] pacientData = {username, password, name, Integer.toString(age), healthPlan};
        SignUpManager.storePacient(pacientData);
        System.out.println("Usuário cadastrado: " + username);

        showAlert(Alert.AlertType.INFORMATION, "Cadastro Realizado", "Usuário " + name + " cadastrado com sucesso!");
        clearFields();
    }

    private void clearFields() {
        usernameField.clear();
        passwordField.clear();
        nameField.clear();
        ageField.clear();
        healthPlanField.clear();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    protected void switchToLogin(ActionEvent event) {
        String FILE_PATH = "/com/example/clinic/LoginScene/login-view.fxm";
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/clinic/LoginScene/login-view.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);

            stage.show();
        } catch (IOException e) {
            System.err.println("Erro ao carregar a tela de login (login-view.fxml):");
            e.printStackTrace();
        }
    }
}
