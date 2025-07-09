package com.example.demo;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class LeaveRequestForm {

    public void start(Stage stage) {
        int employeeId = MainInterface.getLoggedInEmployeeId();

        // Labels and input fields
        Label startDateLabel = new Label("Start Date:");
        DatePicker startDatePicker = new DatePicker();

        Label endDateLabel = new Label("End Date:");
        DatePicker endDatePicker = new DatePicker();

        Label reasonLabel = new Label("Reason:");
        TextArea reasonTextArea = new TextArea();
        reasonTextArea.setPromptText("Enter the reason for leave...");
        reasonTextArea.setWrapText(true);

        // Submit button
        Button submitButton = new Button("Submit Request");
        submitButton.setOnAction(e -> {
            if (startDatePicker.getValue() == null || endDatePicker.getValue() == null || reasonTextArea.getText().isEmpty()) {
                showAlert("Validation Error", "All fields are required.");
                return;
            }

            String startDate = startDatePicker.getValue().toString();
            String endDate = endDatePicker.getValue().toString();
            String reason = reasonTextArea.getText();

            if (submitLeaveRequest(employeeId, startDate, endDate, reason)) {
                showAlert("Request Submitted", "Your leave request has been submitted successfully.");
                DashboardEmployee dashboard = new DashboardEmployee();
                dashboard.start(stage); // Navigate back to the dashboard
            } else {
                showAlert("Submission Failed", "An error occurred while submitting your request. Please try again.");
            }
        });

        // Back button
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            DashboardEmployee dashboard = new DashboardEmployee();
            dashboard.start(stage);
        });

        // Form layout
        GridPane formLayout = new GridPane();
        formLayout.setPadding(new Insets(20));
        formLayout.setVgap(10);
        formLayout.setHgap(10);
        formLayout.setAlignment(Pos.CENTER);
        formLayout.add(startDateLabel, 0, 0);
        formLayout.add(startDatePicker, 1, 0);
        formLayout.add(endDateLabel, 0, 1);
        formLayout.add(endDatePicker, 1, 1);
        formLayout.add(reasonLabel, 0, 2);
        formLayout.add(reasonTextArea, 1, 2);

        // Main layout
        VBox mainLayout = new VBox(15, formLayout, submitButton, backButton);
        mainLayout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(mainLayout, 400, 300);
        stage.setTitle("Submit Leave Request");
        stage.setScene(scene);
    }

    // Submit the leave request to the database
    private boolean submitLeaveRequest(int employeeId, String startDate, String endDate, String reason) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            String query = "INSERT INTO leaverequests (employeeid, startdate, enddate, reason, status) VALUES (?, ?, ?, ?, 'Pending')";
            PreparedStatement stmt = conn.prepareStatement(query);

            // Convert LocalDate to java.sql.Date
            stmt.setInt(1, employeeId);
            stmt.setDate(2, java.sql.Date.valueOf(startDate)); // Convert startDate to java.sql.Date
            stmt.setDate(3, java.sql.Date.valueOf(endDate));   // Convert endDate to java.sql.Date
            stmt.setString(4, reason);

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // Show alert messages
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

