package com.example.demo;

import entities.Employee;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UpdateInfo {

    public void start(Stage stage) {
        int employeeId = MainInterface.getLoggedInEmployeeId();

        // Fetch current information for the employee
        Employee employee = fetchEmployeeInfo(employeeId);

        // Labels and input fields
        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField(employee.getName());

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField(employee.getEmail());

        Label positionLabel = new Label("Position:");
        TextField positionField = new TextField(employee.getPosition());

        // Update button
        Button updateButton = new Button("Update Info");
        updateButton.setOnAction(e -> {
            String newName = nameField.getText();
            String newEmail = emailField.getText();
            String newPosition = positionField.getText();

            if (updateEmployeeInfo(employeeId, newName, newEmail, newPosition)) {
                showAlert("Update Successful", "Your information has been updated.");
                DashboardEmployee dashboard = new DashboardEmployee();
                dashboard.start(stage); // Navigate back to the dashboard
            } else {
                showAlert("Update Failed", "Failed to update your information. Please try again.");
            }
        });

        // Back button
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            DashboardEmployee dashboard = new DashboardEmployee();
            dashboard.start(stage);
        });

        // Layout for the form
        GridPane formLayout = new GridPane();
        formLayout.setPadding(new Insets(20));
        formLayout.setVgap(10);
        formLayout.setHgap(10);
        formLayout.setAlignment(Pos.CENTER);
        formLayout.add(nameLabel, 0, 0);
        formLayout.add(nameField, 1, 0);
        formLayout.add(emailLabel, 0, 1);
        formLayout.add(emailField, 1, 1);
        formLayout.add(positionLabel, 0, 2);
        formLayout.add(positionField, 1, 2);
        formLayout.add(updateButton, 1, 3);

        // Main layout
        VBox mainLayout = new VBox(10, formLayout, backButton);
        mainLayout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(mainLayout, 400, 300);
        stage.setTitle("Update Information");
        stage.setScene(scene);
    }

    private Employee fetchEmployeeInfo(int employeeId) {
        Employee employee = new Employee();
        try (Connection conn = DatabaseUtil.getConnection()) {
            String query = "SELECT firstname, email, position FROM employees WHERE employeeid = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, employeeId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                employee.setName(rs.getString("name"));
                employee.setEmail(rs.getString("email"));
                employee.setPosition(rs.getString("position"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return employee;
    }

    private boolean updateEmployeeInfo(int employeeId, String name, String email, String position) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            String query = "UPDATE employees SET firstname = ?, lastname = ?, email = ?, position = ? WHERE employeeid = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, name);
            stmt.setString(3, email);
            stmt.setString(4, position);
            stmt.setInt(5, employeeId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private void showAlert(String title, String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
