module com.example.clinica {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    exports com.example.clinica;
    opens com.example.clinica to javafx.fxml;
    exports com.example.clinica.backend.Models;
}