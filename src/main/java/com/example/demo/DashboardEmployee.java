package com.example.demo;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.*;
import util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DashboardEmployee {

    public void start(Stage stage) {
        int employeeId = MainInterface.getLoggedInEmployeeId();

        // Section 1: Welcome Header
        Label welcomeLabel = new Label("Welcome to Your Dashboard!");
        welcomeLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;");

        // Section 2: Statistics
        HBox statsBox = createStatisticsBox(employeeId);

        // Section 3: Recent Notifications
        VBox notificationsBox = createNotificationsBox(employeeId);

        // Section 4: Navigation Menu
        VBox menuBox = createMenuBox(stage);

        // Main Layout
        BorderPane mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(20));

        // Layout Assignments
        mainLayout.setTop(welcomeLabel);
        BorderPane.setAlignment(welcomeLabel, Pos.CENTER);
        mainLayout.setCenter(statsBox);
        mainLayout.setLeft(menuBox);
        mainLayout.setRight(notificationsBox);

        Scene scene = new Scene(mainLayout, 800, 600);
        stage.setTitle("Employee Dashboard");
        stage.setScene(scene);
    }

    // Create the statistics box (e.g., Leave Status, Training Progress)
    private HBox createStatisticsBox(int employeeId) {
        // Leave Status Pie Chart
        PieChart leaveChart = new PieChart();
        leaveChart.setTitle("Leave Status");
        fetchLeaveStatistics(employeeId, leaveChart);

        // Training Progress
        VBox trainingBox = new VBox();
        Label trainingLabel = new Label("Training Progress:");
        trainingLabel.setStyle("-fx-font-weight: bold;");
        Label progress = fetchTrainingProgress(employeeId);
        progress.setStyle("-fx-font-size: 18px; -fx-text-fill: #0078D7;");

        trainingBox.getChildren().addAll(trainingLabel, progress);
        trainingBox.setAlignment(Pos.CENTER);
        trainingBox.setSpacing(10);

        // Combine Statistics
        HBox statsBox = new HBox(50, leaveChart, trainingBox);
        statsBox.setAlignment(Pos.CENTER);
        statsBox.setPadding(new Insets(20));
        statsBox.setStyle("-fx-border-color: #ddd; -fx-border-radius: 10; -fx-border-width: 2;");

        return statsBox;
    }

    // Create the notifications box
    private VBox createNotificationsBox(int employeeId) {
        VBox notificationsBox = new VBox(10);
        notificationsBox.setPadding(new Insets(10));
        notificationsBox.setStyle("-fx-border-color: #ddd; -fx-border-radius: 10; -fx-border-width: 2;");

        Label title = new Label("Recent Notifications");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        VBox messages = fetchNotifications(employeeId);

        notificationsBox.getChildren().addAll(title, messages);
        notificationsBox.setAlignment(Pos.TOP_LEFT);
        notificationsBox.setPrefWidth(250);

        return notificationsBox;
    }

    // Create the navigation menu
    private VBox createMenuBox(Stage stage) {
        VBox menuBox = new VBox(20);
        menuBox.setPadding(new Insets(10));
        menuBox.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #ddd; -fx-border-radius: 10;");

        Button viewLeaveHistory = new Button("View Leave History");
        Button requestLeave = new Button("Request Leave");
        Button updateInfo = new Button("Update Personal Info");
        Button logout = new Button("Logout");

        viewLeaveHistory.setOnAction(e -> new LeaveHistory().start(stage));
        requestLeave.setOnAction(e -> new LeaveRequestForm().start(stage));
        updateInfo.setOnAction(e -> new UpdateInfo().start(stage));
        logout.setOnAction(e -> {
            LoginInterface login = new LoginInterface();
            login.start(stage);
            MainInterface.setLoggedInEmployeeId(0);
        });

        menuBox.getChildren().addAll(viewLeaveHistory, requestLeave, updateInfo, logout);
        menuBox.setAlignment(Pos.CENTER_LEFT);

        return menuBox;
    }

    // Fetch leave statistics from the database
    private void fetchLeaveStatistics(int employeeId, PieChart chart) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            String query = "SELECT COUNT(*) AS total, " +
                    "SUM(CASE WHEN status = 'Approved' THEN 1 ELSE 0 END) AS approved, " +
                    "SUM(CASE WHEN status = 'Pending' THEN 1 ELSE 0 END) AS pending " +
                    "FROM leaverequests WHERE employeeid = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, employeeId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int total = rs.getInt("total");
                int approved = rs.getInt("approved");
                int pending = rs.getInt("pending");

                chart.setData(FXCollections.observableArrayList(
                        new PieChart.Data("Approved", approved),
                        new PieChart.Data("Pending", pending),
                        new PieChart.Data("Other", total - approved - pending)
                ));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Fetch training progress from the database
    private Label fetchTrainingProgress(int employeeId) {
        Label progressLabel = new Label("0% Completed");
        try (Connection conn = DatabaseUtil.getConnection()) {
            String query = "SELECT overallscore FROM performanceevaluations WHERE employeeid = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, employeeId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int progress = rs.getInt("overallscore");
                progressLabel.setText(progress + "% Completed");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return progressLabel;
    }

    // Fetch recent notifications from the database
    private VBox fetchNotifications(int employeeId) {
        VBox messages = new VBox(5);
        try (Connection conn = DatabaseUtil.getConnection()) {
            String query = "SELECT message, sentat FROM notifications WHERE employeeid = ? ORDER BY sentat DESC LIMIT 5";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, employeeId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String message = rs.getString("message");
                Label label = new Label("- " + message);
                messages.getChildren().add(label);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return messages;
    }
}