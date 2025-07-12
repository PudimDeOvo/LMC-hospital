module com.example.clinic {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.commons.csv;
    requires com.opencsv;


    opens com.example.clinic to javafx.fxml;
    opens com.example.clinic.initialSystem.registerSystem to javafx.fxml;
    opens com.example.clinic.initialSystem.loginSystem to javafx.fxml;
    opens com.example.clinic.initialSystem.welcomeSystem to javafx.fxml;
    opens com.example.clinic.homeSystem to javafx.fxml;

    exports com.example.clinic;
    exports com.example.clinic.Database.AppointmentDatabase;
    opens com.example.clinic.Database.AppointmentDatabase to javafx.fxml;
    exports com.example.clinic.Database.userDatabase;
    opens com.example.clinic.Database.userDatabase to javafx.fxml;
    opens com.example.clinic.homeSystem.doctorHome to javafx.fxml;
}