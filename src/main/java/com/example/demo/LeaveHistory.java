package com.example.demo;

import entities.LeaveRequest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LeaveHistory {

    public void start(Stage stage) {
        int employeeId = MainInterface.getLoggedInEmployeeId();
        System.out.println(employeeId);

        // TableView for displaying leave history
        TableView<LeaveRequest> tableView = new TableView<>();
        tableView.setItems(fetchLeaveHistory(employeeId));

        // Define columns
        TableColumn<LeaveRequest, String> startDateColumn = new TableColumn<>("Start Date");
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));

        TableColumn<LeaveRequest, String> endDateColumn = new TableColumn<>("End Date");
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));

        TableColumn<LeaveRequest, String> reasonColumn = new TableColumn<>("Reason");
        reasonColumn.setCellValueFactory(new PropertyValueFactory<>("reason"));

        TableColumn<LeaveRequest, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        tableView.getColumns().addAll(startDateColumn, endDateColumn, reasonColumn, statusColumn);

        // Back button to return to the dashboard
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            DashboardEmployee dashboard = new DashboardEmployee();
            dashboard.start(stage);
        });

        VBox buttonBox = new VBox(backButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10));

        // Layout
        BorderPane layout = new BorderPane();
        layout.setCenter(tableView);
        layout.setBottom(buttonBox);
        BorderPane.setAlignment(buttonBox, Pos.CENTER);

        Scene scene = new Scene(layout, 600, 400);
        stage.setTitle("Leave History");
        stage.setScene(scene);
    }

    // Fetch leave history from the database
    private ObservableList<LeaveRequest> fetchLeaveHistory(int employeeId) {
        ObservableList<LeaveRequest> leaveRequests = FXCollections.observableArrayList();

        try (Connection conn = DatabaseUtil.getConnection()) {
            String query = "SELECT startdate, enddate, reason, status FROM leaverequests WHERE employeeid = ? ORDER BY startdate DESC";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, employeeId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String startDate = rs.getString("startdate");
                String endDate = rs.getString("enddate");
                String reason = rs.getString("reason");
                String status = rs.getString("status");

                leaveRequests.add(new LeaveRequest(startDate, endDate, reason, status));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return leaveRequests;
    }
}

