package com.example.demo;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import java.sql.SQLException;

public class LoginInterface {

    public void start(Stage stage) {
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter Password");

        Button loginButton = new Button("Login");

        // Handle login action
        loginButton.setOnAction(e -> {
            try {
                // Authenticate and get role
                String role = MainInterface.getAuth().signIn(usernameField.getText(), passwordField.getText());
                if (role != null) {
                    if ("Employee".equalsIgnoreCase(role)) {
                        DashboardEmployee dashboard = new DashboardEmployee();
                        dashboard.start(stage);
                    } else if ("HR".equalsIgnoreCase(role)) {
                        HRDashboard dashboard = new HRDashboard();
                        dashboard.start(stage);
                    } else {
                        showToast("Unknown role. Please contact support.", loginButton);
                    }
                } else {
                    showToast("Invalid Credentials, The username or password is incorrect.", loginButton);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                showToast("An error occurred during login. Please try again.", loginButton);
            }
        });

        VBox layout = new VBox(10, usernameField, passwordField, loginButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 300, 200);
        stage.setTitle("Employee Login");
        stage.setScene(scene);
        stage.show();
    }

    private static void showToast(String message, Node parentNode) {
        Popup popup = new Popup();
        Label label = new Label(message);
        label.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-padding: 10px; -fx-border-radius: 5; -fx-background-radius: 5;");
        popup.getContent().add(label);
        popup.setAutoHide(true);
        popup.show(parentNode.getScene().getWindow());
    }
}

