package com.example.clinic.initialSystem.loginSystem;

import com.example.clinic.Database.userDatabase.DoctorDatabase;
import com.example.clinic.SceneManager;
import com.example.clinic.entities.user.Doctor;
import com.example.clinic.homeSystem.DoctorHomeController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class DoctorLoginController extends LoginController{
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    @Override
    protected void handleLoginButtonAction(ActionEvent e){
        String username = usernameField.getText();
        String password = passwordField.getText();

        System.out.println(username + password);

        if (DoctorDatabase.getInstance().checkCredentials(username, password)){
            System.out.println("Login bem-sucedido");
            Doctor loggedDoctor = DoctorDatabase.getInstance().getDoctor(username);
            switchToDoctorHome(e, loggedDoctor);
        } else {
            System.out.println("Credenciais inválidas");
        }
    }

    @FXML
    protected void switchToDoctorHome(ActionEvent e, Doctor doctor){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/clinic/DoctorHomeScene/doctorhome-view.fxml"));
            Parent root = loader.load();
            DoctorHomeController controller = loader.getController();
            controller.setDoctor(doctor);
            SceneManager.switchScene(e, root);
        } catch(IOException ex) {
            ex.printStackTrace(System.err);
        }

    }

    @FXML
    @Override
    protected void switchToSignUp(ActionEvent e){
        SceneManager.switchScene(e, "/com/example/clinic/SignUpScene/DoctorSignUpView/signup-view.fxml");
    }
}
