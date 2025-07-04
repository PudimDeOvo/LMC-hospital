module com.example.clinic {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.commons.csv;
    requires com.opencsv;


    opens com.example.clinic to javafx.fxml;
    opens com.example.clinic.registerSystem to javafx.fxml;
    opens com.example.clinic.loginSystem to javafx.fxml;
    exports com.example.clinic;
}