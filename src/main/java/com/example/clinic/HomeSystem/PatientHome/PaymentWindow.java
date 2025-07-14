package com.example.clinic.HomeSystem.PatientHome;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
        Stage stage = new Stage();
        stage.setTitle("Payment");

        Label label = new Label("Appointment concluded. Please proceed with payment.");
        Button confirmButton = new Button("Confirm Payment");
        confirmButton.setOnAction(e -> {
            container.getChildren().remove(card);
            stage.close();
        });

        VBox layout = new VBox(10, label, confirmButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        Scene scene = new Scene(layout, 300, 150);
        stage.setScene(scene);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
}

