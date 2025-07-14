package com.example.clinic.HomeSystem.PatientHome;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.scene.control.Label;
import com.example.clinic.Entities.Appointment.Appointment;

public class PaymentWindow {

    private final Appointment appointment;
    private final VBox container;
    private final Node card;

    public PaymentWindow(Appointment appointment, VBox container, Node card) {
        this.appointment = appointment;
        this.container = container;
        this.card = card;
    }

    public void show() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/clinic/PatientHomeScene/paymentwindow/payment.fxml"));
            Parent root = loader.load();

            VBox layout = (VBox) root;
            Label label = (Label) layout.lookup("#paymentLabel");
            Button confirmButton = (Button) layout.lookup("#confirmPaymentButton");

            confirmButton.setOnAction(e -> {
                container.getChildren().remove(card);
                Stage stage = (Stage) confirmButton.getScene().getWindow();
                stage.close();
            });

            Scene scene = new Scene(root, 350, 200);
            Stage stage = new Stage();
            stage.setTitle("Payment");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
