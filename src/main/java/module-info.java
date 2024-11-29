module com.example.clinica {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    exports com.example.clinica;
    opens com.example.clinica to javafx.fxml;
}