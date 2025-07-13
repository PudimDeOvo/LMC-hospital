module com.example.clinic {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.commons.csv;
    requires com.opencsv;
    requires javafx.graphics;


    opens com.example.clinic to javafx.fxml;
    opens com.example.clinic.InitialSystem.RegisterSystem to javafx.fxml;
    opens com.example.clinic.InitialSystem.LoginSystem to javafx.fxml;
    opens com.example.clinic.InitialSystem.WelcomeSystem to javafx.fxml;
    opens com.example.clinic.HomeSystem.DoctorHome to javafx.fxml;
    opens com.example.clinic.HomeSystem.PatientHome to javafx.fxml;

    exports com.example.clinic;
    exports com.example.clinic.Database.AppointmentDatabase;
    opens com.example.clinic.Database.AppointmentDatabase to javafx.fxml;
    exports com.example.clinic.Database.userDatabase;
    opens com.example.clinic.Database.userDatabase to javafx.fxml;
}