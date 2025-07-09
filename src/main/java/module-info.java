module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires jbcrypt;

    // Open com.example.demo to FXML-related reflection
    opens com.example.demo to javafx.fxml;

    // Open the entities package for reflection used by JavaFX (e.g., TableView)
    opens entities to javafx.base;

    // Export the com.example.demo package for external use
    exports com.example.demo;
}