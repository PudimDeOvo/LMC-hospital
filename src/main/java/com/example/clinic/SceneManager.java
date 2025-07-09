package com.example.clinic;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SceneManager {

    private SceneManager(){}

    public static void switchScene(ActionEvent event, String fxmlPath) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            switchScene(stage, fxmlPath);
        } catch (Exception e) {
            System.err.println("Error switching scene from ActionEvent:");
            e.printStackTrace();
        }
    }

    private static void switchScene(Stage stage, String fxmlPath) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(SceneManager.class.getResource(fxmlPath)));
            Scene scene = new Scene(root);
            stage.setScene(scene);

            stage.show();

        } catch (IOException e) {
            System.err.println("Error loading FXML file: " + fxmlPath);
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.err.println("FXML file not found: " + fxmlPath);
            e.printStackTrace();
        }
    }
}
