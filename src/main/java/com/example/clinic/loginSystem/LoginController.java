package com.example.clinic.loginSystem;


import com.example.clinic.Database.PatientDatabase;
import com.example.clinic.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

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


        if (PatientDatabase.getInstance().checkCredentials(username, password)) {
            System.out.println("Login bem-sucedido!");
        } else {
            System.out.println("Credenciais inválidas.");
        }
    }

    @FXML
    protected void switchToSignUp(ActionEvent e) {
        SceneManager.switchScene(e, "/com/example/clinic/SignUpScene/signup-view.fxml");
    }

    @FXML
    protected void switchToWelcome(ActionEvent e){
        SceneManager.switchScene(e, "/com/example/clinic/WelcomeScene/welcome-view.fxml");
    }
}