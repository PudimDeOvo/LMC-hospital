package com.example.clinic.loginSystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LoginController {

    LoginManager loginManager = new LoginManager();

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;


    @FXML
    protected void handleLoginButtonAction(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        System.out.println("--- Tentativa de Login ---");
        System.out.println("Usuário digitado: " + username);
        System.out.println("Senha digitada: " + password);


        if (loginManager.checkCredentials(username, password)) {
            System.out.println("Login bem-sucedido!");
        } else {
            System.out.println("Credenciais inválidas.");
        }
    }

    @FXML
    protected void switchToSignUp(ActionEvent event) {
        String FILE_PATH = "/com/example/clinic/SignUpScene/signup-view.fxml";

        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(FILE_PATH)));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);

            stage.show();
        } catch (IOException e) {
            System.err.println("Erro ao carregar a tela de cadastro (signup-view.fxml):");
            e.printStackTrace();
        }
    }}