package com.example.clinic.welcomeSystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class WelcomeController {
    @FXML
    protected void switchToPatientLogin(ActionEvent event) {
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
