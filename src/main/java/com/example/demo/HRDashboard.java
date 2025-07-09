//package com.example.demo;
//
//import Dao.EmployeeDAO;
//import entities.EmployeeLeaveRequest;
//import entities.LeaveRequest;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.geometry.Insets;
//import javafx.geometry.Pos;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//import javafx.scene.control.*;
//import javafx.scene.layout.*;
//import util.DatabaseUtil;
//
//import java.sql.*;
//
//public class HRDashboard {
//    public void start(Stage stage) {
//        // Create main layout
//        BorderPane mainLayout = new BorderPane();
//        mainLayout.setPadding(new Insets(20));
//
//        // Menu bar for navigation
//        MenuBar menuBar = createMenuBar(stage);
//
//        // Sections for RH management features
//        VBox employeeManagementSection = createEmployeeManagementSection();
//        VBox leaveManagementSection = createLeaveManagementSection();
//        VBox payrollManagementSection = createPayrollManagementSection();
//        VBox performanceEvaluationSection = createPerformanceEvaluationSection();
//        VBox trainingManagementSection = createTrainingManagementSection();
//        VBox reportsSection = createReportsSection();
//
//        // Set menu and sections in layout
//        mainLayout.setTop(menuBar);
//        mainLayout.setLeft(employeeManagementSection);
//        mainLayout.setCenter(leaveManagementSection); // You can dynamically switch sections as needed
//        mainLayout.setRight(payrollManagementSection);
//
//        // Scene setup
//        Scene scene = new Scene(mainLayout, 1000, 600);
//        stage.setTitle("RH Interface");
//        stage.setScene(scene);
//        stage.show();
//    }
//
//    // Create the navigation menu bar
//    private MenuBar createMenuBar(Stage stage) {
//        MenuBar menuBar = new MenuBar();
//
//        // Menu items
//        Menu fileMenu = new Menu("account");
//        MenuItem logoutItem = new MenuItem("Logout");
//        logoutItem.setOnAction(e -> logout(stage));
//
//        fileMenu.getItems().add(logoutItem);
//        menuBar.getMenus().add(fileMenu);
//
//        return menuBar;
//    }
//
//    // Employee Management Section
//    private VBox createEmployeeManagementSection() {
//        VBox employeeManagementBox = new VBox(15);
//        employeeManagementBox.setAlignment(Pos.TOP_LEFT);
//        employeeManagementBox.setPadding(new Insets(10));
//        employeeManagementBox.setStyle("-fx-border-color: #ddd; -fx-border-radius: 10; -fx-border-width: 2;");
//
//        // Controls for managing employees
//        Button addEmployeeButton = new Button("Add Employee");
//        Button modifyEmployeeButton = new Button("Modify Employee");
//        Button removeEmployeeButton = new Button("Remove Employee");
//        Button searchEmployeeButton = new Button("Search Employee");
//
//        // Add actions here
//        addEmployeeButton.setOnAction(e -> showAddEmployeeForm());
//        modifyEmployeeButton.setOnAction(e -> showModifyEmployeeForm());
//        removeEmployeeButton.setOnAction(e -> removeEmployee());
//        searchEmployeeButton.setOnAction(e -> showSearchEmployeeDialog());
//
//        employeeManagementBox.getChildren().addAll(
//                addEmployeeButton, modifyEmployeeButton, removeEmployeeButton, searchEmployeeButton
//        );
//
//        return employeeManagementBox;
//    }
//
//    private void showAddEmployeeForm() {
//        Stage stage = new Stage();
//        stage.setTitle("Add Employee");
//
//        VBox form = new VBox(10);
//        form.setPadding(new Insets(10));
//        form.setAlignment(Pos.CENTER);
//
//        TextField firstnameField = new TextField();
//        firstnameField.setPromptText("Employee First name");
//
//        TextField lastNameField = new TextField();
//        lastNameField.setPromptText("Employee Last name");
//
//        TextField positionField = new TextField();
//        positionField.setPromptText("Position");
//
//        TextField departmentField = new TextField();
//        departmentField.setPromptText("Departement");
//
//        TextField emailField = new TextField();
//        emailField.setPromptText("Email");
//
//        TextField salaryField = new TextField();
//        salaryField.setPromptText("Salary");
//
//        Button saveButton = new Button("Save");
//        saveButton.setOnAction(e -> {
//            String firstName = firstnameField.getText();
//            String lastName = lastNameField.getText();
//            String department = departmentField.getText();
//            String email = emailField.getText();
//            String position = positionField.getText();
//            String salary = salaryField.getText();
//
//            if (!firstName.isEmpty() && !lastName.isEmpty() && !department.isEmpty() && !email.isEmpty() && !position.isEmpty() && !salary.isEmpty()) {
//                    EmployeeDAO employeeDao = new EmployeeDAO();
//
//                    if (employeeDao.addEmployee(firstName,lastName,position,department,email,salary)){
//                        stage.close();
//                        showAlert("Success", "Employee added successfully!");
//                    } else {
//                        showAlert("Error", "Failed to add employee.");
//                    }
//            } else {
//                showAlert("Validation Error", "All fields are required.");
//            }
//        });
//
//        form.getChildren().addAll(firstnameField,lastNameField,emailField, positionField, salaryField, departmentField, saveButton);
//
//        Scene scene = new Scene(form, 300, 200);
//        stage.setScene(scene);
//        stage.show();
//    }
//
//
//    private void showModifyEmployeeForm() {
//        // Logic to show modify employee form
//    }
//
//    private void removeEmployee() {
//        // Logic to remove employee
//    }
//
//    private void showSearchEmployeeDialog() {
//        TextInputDialog dialog = new TextInputDialog();
//        dialog.setTitle("Search Employee");
//        dialog.setHeaderText("Enter Employee Name");
//        dialog.setContentText("Search:");
//
//        dialog.showAndWait().ifPresent(text -> {
//            try (Connection connection = DatabaseUtil.getConnection()) {
//                // Use LIKE for partial matches and search both firstname and lastname
//                String query = "SELECT * FROM employees WHERE firstname LIKE ? OR lastname LIKE ?";
//                PreparedStatement statement = connection.prepareStatement(query);
//                statement.setString(1, "%" + text + "%"); // Add wildcards for partial matching
//                statement.setString(2, "%" + text + "%");
//
//                ResultSet resultSet = statement.executeQuery();
//
//                StringBuilder resultText = new StringBuilder();
//                boolean found = false;
//
//                // Iterate through all matching employees
//                while (resultSet.next()) {
//                    found = true;
//                    String firstName = resultSet.getString("firstname");
//                    String lastName = resultSet.getString("lastname");
//                    String position = resultSet.getString("position");
//                    String salary = resultSet.getString("salary");
//
//                    // Append each employee's details to the result text
//                    resultText.append("Name: ").append(firstName).append(" ").append(lastName)
//                            .append("\nPosition: ").append(position)
//                            .append("\nSalary: ").append(salary)
//                            .append("\n\n");
//                }
//
//                if (found) {
//                    // Display all matching employees
//                    showAlert("Employees Found", resultText.toString());
//                } else {
//                    showAlert("Not Found", "No employees found with the given name.");
//                }
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//                showAlert("Error", "Failed to search for employees.");
//            }
//        });
//    }
//
//
//    private void showAlert(String title, String message) {
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle(title);
//        alert.setHeaderText(null);
//        alert.setContentText(message);
//        alert.showAndWait();
//    }
//
//    // Leave Management Section
//    private VBox createLeaveManagementSection() {
//        VBox leaveManagementBox = new VBox(15);
//        leaveManagementBox.setAlignment(Pos.TOP_LEFT);
//        leaveManagementBox.setPadding(new Insets(10));
//
//        // Fetch leave requests from the database
//        ObservableList<EmployeeLeaveRequest> leaveRequests = fetchLeaveRequestsFromDatabase();
//
//        // Create a list of leave requests
//        for (EmployeeLeaveRequest request : leaveRequests) {
//            // HBox for each leave request
//            HBox requestBox = new HBox(10);
//            requestBox.setAlignment(Pos.CENTER_LEFT);
//
//            // Labels for leave details
//            Label leaveDetails = new Label(request.getEmployeeName() + " - " + request.getLeaveType() +
//                    " (" + request.getStartDate() + " to " + request.getEndDate() + ")");
//
//            // Approve button
//            Button approveButton = new Button("Approve");
//            approveButton.setOnAction(e ->
//            {
//                requestBox.setStyle("-fx-background-color: lightgreen; -fx-border-color: lightgray;");
//                approveLeaveRequest(request);
//            }
//        );
//
//            // Decline button
//            Button declineButton = new Button("Decline");
//            declineButton.setOnAction(e -> {
//                requestBox.setStyle("-fx-background-color: lightcoral; -fx-border-color: lightgray;");
//                declineLeaveRequest(request);
//            });
//
//            // Add details and buttons to the HBox
//            requestBox.getChildren().addAll(leaveDetails, approveButton, declineButton);
//
//            // Add the HBox to the VBox
//            leaveManagementBox.getChildren().add(requestBox);
//        }
//
//        return leaveManagementBox;
//    }
//
//    // Method to fetch leave requests from the database
//    private ObservableList<EmployeeLeaveRequest> fetchLeaveRequestsFromDatabase() {
//        ObservableList<EmployeeLeaveRequest> leaveRequests = FXCollections.observableArrayList();
//
//        String query = """
//        SELECT lr.requestid, lr.employeeid, e.firstname, lr.leavetype,
//               lr.startdate, lr.enddate, lr.reason, lr.status
//        FROM public.leaverequests lr
//        JOIN public.employees e ON lr.employeeid = e.employeeid
//        WHERE lr.status = 'Pending'
//        ORDER BY lr.requestid ASC;
//    """;
//
//        try (Connection connection = DatabaseUtil.getConnection()){
//             PreparedStatement statement = connection.prepareStatement(query);
//             ResultSet resultSet = statement.executeQuery();
//
//                while (resultSet.next()) {
//                    int requestId = resultSet.getInt("requestid");
//                    int employeeId = resultSet.getInt("employeeid");
//                    String employeeName = resultSet.getString("firstname");
//                    String leaveType = resultSet.getString("leavetype");
//                    String startDate = resultSet.getString("startdate");
//                    String endDate = resultSet.getString("enddate");
//                    String reason = resultSet.getString("reason");
//                    String status = resultSet.getString("status");
//
//                    leaveRequests.add(new EmployeeLeaveRequest(
//                            requestId, employeeId, employeeName, leaveType, startDate, endDate, reason, status
//                    ));
//                }
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return leaveRequests;
//    }
//
//    private void approveLeaveRequest(EmployeeLeaveRequest request) {
//        updateLeaveRequestStatus(request.getRequestId(), "Approved");
//        System.out.println("Approved leave for " + request.getEmployeeName());
//    }
//
//    // Action to decline a leave request
//    private void declineLeaveRequest(EmployeeLeaveRequest request) {
//        updateLeaveRequestStatus(request.getRequestId(), "Declined");
//        System.out.println("Declined leave for " + request.getEmployeeName());
//    }
//
//    private void showLeaveHistory() {
//        // Logic for showing leave history
//    }
//
//    private void updateLeaveRequestStatus(int id, String status) {
//
//        String query = "UPDATE LeaveRequests SET status = ? WHERE requestid = ?";
//
//        try (Connection connection = DatabaseUtil.getConnection();
//             PreparedStatement statement = connection.prepareStatement(query)) {
//
//            statement.setString(1, status);
//            statement.setInt(2, id);
//
//            int rowsUpdated = statement.executeUpdate();
//            if (rowsUpdated > 0) {
//                System.out.println("Successfully updated status to " + status + " for request ID: " + id);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    // Payroll Management Section
//    private VBox createPayrollManagementSection() {
//        VBox payrollManagementBox = new VBox(15);
//        payrollManagementBox.setAlignment(Pos.TOP_LEFT);
//        payrollManagementBox.setPadding(new Insets(10));
//
//        // Controls for payroll management
//        Button generatePayslipsButton = new Button("Generate Payslips");
//        Button viewPayrollHistoryButton = new Button("View Payroll History");
//
//        // Add actions here
//        generatePayslipsButton.setOnAction(e -> generatePayslips());
//        viewPayrollHistoryButton.setOnAction(e -> showPayrollHistory());
//
//        payrollManagementBox.getChildren().addAll(
//                generatePayslipsButton, viewPayrollHistoryButton
//        );
//
//        return payrollManagementBox;
//    }
//
//    private void generatePayslips() {
//        // Logic for generating payslips
//    }
//
//    private void showPayrollHistory() {
//        // Logic for showing payroll history
//    }
//
//    // Performance Evaluation Section
//    private VBox createPerformanceEvaluationSection() {
//        VBox performanceEvaluationBox = new VBox(15);
//        performanceEvaluationBox.setAlignment(Pos.TOP_LEFT);
//        performanceEvaluationBox.setPadding(new Insets(10));
//
//        // Controls for performance evaluations
//        Button addEvaluationButton = new Button("Add Evaluation");
//        Button viewEvaluationHistoryButton = new Button("View Evaluation History");
//
//        // Add actions here
//        addEvaluationButton.setOnAction(e -> addPerformanceEvaluation());
//        viewEvaluationHistoryButton.setOnAction(e -> showPerformanceEvaluationHistory());
//
//        performanceEvaluationBox.getChildren().addAll(
//                addEvaluationButton, viewEvaluationHistoryButton
//        );
//
//        return performanceEvaluationBox;
//    }
//
//    private void addPerformanceEvaluation() {
//        // Logic for adding a performance evaluation
//    }
//
//    private void showPerformanceEvaluationHistory() {
//        // Logic for showing performance evaluation history
//    }
//
//    // Training Management Section
//    private VBox createTrainingManagementSection() {
//        VBox trainingManagementBox = new VBox(15);
//        trainingManagementBox.setAlignment(Pos.TOP_LEFT);
//        trainingManagementBox.setPadding(new Insets(10));
//
//        // Controls for training management
//        Button assignTrainingButton = new Button("Assign Training");
//        Button viewTrainingHistoryButton = new Button("View Training History");
//
//        // Add actions here
//        assignTrainingButton.setOnAction(e -> assignTraining());
//        viewTrainingHistoryButton.setOnAction(e -> showTrainingHistory());
//
//        trainingManagementBox.getChildren().addAll(
//                assignTrainingButton, viewTrainingHistoryButton
//        );
//
//        return trainingManagementBox;
//    }
//
//    private void assignTraining() {
//        // Logic for assigning training
//    }
//
//    private void showTrainingHistory() {
//        // Logic for showing training history
//    }
//
//    // Reports Section
//    private VBox createReportsSection() {
//        VBox reportsBox = new VBox(15);
//        reportsBox.setAlignment(Pos.TOP_LEFT);
//        reportsBox.setPadding(new Insets(10));
//
//        // Controls for generating reports
//        Button generateReportsButton = new Button("Generate Reports");
//
//        // Add actions here
//        generateReportsButton.setOnAction(e -> generateReports());
//
//        reportsBox.getChildren().add(generateReportsButton);
//
//        return reportsBox;
//    }
//
//    private void generateReports() {
//        // Logic for generating reports
//    }
//
//    // Handle Logout
//    private void logout(Stage stage) {
//        // Logic for logging out (show login screen, clear session data)
//        LoginInterface login = new LoginInterface();
//        login.start(stage);
//    }
//}

package com.example.demo;

import Dao.EmployeeDAO;
import entities.EmployeeLeaveRequest;
import entities.PayrollRecord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HRDashboard {
    public void start(Stage stage) {
        // Create main layout
        BorderPane mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(20));

        // Menu bar for navigation
        MenuBar menuBar = createMenuBar(stage);

        // Sections for HR management features
        VBox employeeManagementSection = createEmployeeManagementSection();
        VBox leaveManagementSection = createLeaveManagementSection();
        VBox payrollManagementSection = createPayrollManagementSection();
        VBox performanceEvaluationSection = createPerformanceEvaluationSection();
        VBox trainingManagementSection = createTrainingManagementSection();
        VBox reportsSection = createReportsSection();

        // Set menu and sections in layout
        mainLayout.setTop(menuBar);
        mainLayout.setLeft(employeeManagementSection);
        mainLayout.setCenter(leaveManagementSection); // You can dynamically switch sections as needed
        mainLayout.setRight(payrollManagementSection);

        // Scene setup
        Scene scene = new Scene(mainLayout, 1000, 600);
        stage.setTitle("HR Interface");
        stage.setScene(scene);
        stage.show();
    }

    // Create the navigation menu bar
    private MenuBar createMenuBar(Stage stage) {
        MenuBar menuBar = new MenuBar();

        // Menu items
        Menu fileMenu = new Menu("Account");
        MenuItem logoutItem = new MenuItem("Logout");
        logoutItem.setOnAction(e -> logout(stage));

        fileMenu.getItems().add(logoutItem);
        menuBar.getMenus().add(fileMenu);

        return menuBar;
    }

    // Employee Management Section
    private VBox createEmployeeManagementSection() {
        VBox employeeManagementBox = new VBox(15);
        employeeManagementBox.setAlignment(Pos.TOP_LEFT);
        employeeManagementBox.setPadding(new Insets(10));
        employeeManagementBox.setStyle("-fx-border-color: #ddd; -fx-border-radius: 10; -fx-border-width: 2;");

        // Controls for managing employees
        Button addEmployeeButton = new Button("Add Employee");
        Button modifyEmployeeButton = new Button("Modify Employee");
        Button removeEmployeeButton = new Button("Remove Employee");
        Button searchEmployeeButton = new Button("Search Employee");

        // Add actions here
        addEmployeeButton.setOnAction(e -> showAddEmployeeForm());
        modifyEmployeeButton.setOnAction(e -> showModifyEmployeeForm());
        removeEmployeeButton.setOnAction(e -> removeEmployee());
        searchEmployeeButton.setOnAction(e -> showSearchEmployeeDialog());

        employeeManagementBox.getChildren().addAll(
                addEmployeeButton, modifyEmployeeButton, removeEmployeeButton, searchEmployeeButton
        );

        return employeeManagementBox;
    }

    private void showAddEmployeeForm() {
        Stage stage = new Stage();
        stage.setTitle("Add Employee");

        VBox form = new VBox(10);
        form.setPadding(new Insets(10));
        form.setAlignment(Pos.CENTER);

        TextField firstnameField = new TextField();
        firstnameField.setPromptText("Employee First Name");

        TextField lastNameField = new TextField();
        lastNameField.setPromptText("Employee Last Name");

        TextField positionField = new TextField();
        positionField.setPromptText("Position");

        TextField departmentField = new TextField();
        departmentField.setPromptText("Department");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        TextField salaryField = new TextField();
        salaryField.setPromptText("Salary");

        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            String firstName = firstnameField.getText();
            String lastName = lastNameField.getText();
            String department = departmentField.getText();
            String email = emailField.getText();
            String position = positionField.getText();
            String salary = salaryField.getText();

            if (!firstName.isEmpty() && !lastName.isEmpty() && !department.isEmpty() && !email.isEmpty() && !position.isEmpty() && !salary.isEmpty()) {
                EmployeeDAO employeeDao = new EmployeeDAO();

                if (employeeDao.addEmployee(firstName, lastName, position, department, email, salary)) {
                    stage.close();
                    showAlert("Success", "Employee added successfully!");
                } else {
                    showAlert("Error", "Failed to add employee.");
                }
            } else {
                showAlert("Validation Error", "All fields are required.");
            }
        });

        form.getChildren().addAll(firstnameField, lastNameField, emailField, positionField, salaryField, departmentField, saveButton);

        Scene scene = new Scene(form, 300, 300);
        stage.setScene(scene);
        stage.show();
    }

    private void showModifyEmployeeForm() {
        // Logic to show modify employee form
        showAlert("Info", "Modify Employee Form - Not Implemented Yet.");
    }

    private void removeEmployee() {
        Stage stage = new Stage();
        stage.setTitle("Remove Employee");

        VBox form = new VBox(10);
        form.setPadding(new Insets(10));
        form.setAlignment(Pos.CENTER);

        TextField employeeNameField = new TextField();
        employeeNameField.setPromptText("Enter Employee Name");

        Button searchButton = new Button("Search");

        ListView<String> employeeListView = new ListView<>();
        Button deleteButton = new Button("Delete Selected Employee");
        deleteButton.setDisable(true); // Disabled until selection is made

        searchButton.setOnAction(e -> {
            String employeeName = employeeNameField.getText().trim();

            if (!employeeName.isEmpty()) {
                try (Connection connection = DatabaseUtil.getConnection()) {
                    String query = "SELECT employeeid, firstname, lastname FROM employees WHERE firstname ILIKE ?";
                    try (PreparedStatement stmt = connection.prepareStatement(query)) {
                        stmt.setString(1, employeeName);

                        ResultSet rs = stmt.executeQuery();
                        ObservableList<String> employeeList = FXCollections.observableArrayList();

                        while (rs.next()) {
                            int employeeId = rs.getInt("employeeid");
                            String name = rs.getString("firstname");
                            employeeList.add(employeeId + " - " + name);
                        }

                        if (employeeList.isEmpty()) {
                            showAlert("Not Found", "No employee found with that name.");
                        } else {
                            employeeListView.setItems(employeeList);
                            deleteButton.setDisable(false); // Enable delete button
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    showAlert("Error", "Database error occurred.");
                }
            } else {
                showAlert("Validation Error", "Please enter an employee name.");
            }
        });

        deleteButton.setOnAction(e -> {
            String selectedItem = employeeListView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                int employeeId = Integer.parseInt(selectedItem.split(" - ")[0]); // Extract employee ID

                try (Connection connection = DatabaseUtil.getConnection()) {
                    connection.setAutoCommit(false); // Start transaction

                    // Delete associated user account first
                    String deleteUserQuery = "DELETE FROM public.useraccounts WHERE employeeid = ?";
                    try (PreparedStatement deleteUserStmt = connection.prepareStatement(deleteUserQuery)) {
                        deleteUserStmt.setInt(1, employeeId);
                        deleteUserStmt.executeUpdate();
                    }

                    // Delete employee record
                    String deleteEmployeeQuery = "DELETE FROM public.employees WHERE employeeid = ?";
                    try (PreparedStatement deleteEmployeeStmt = connection.prepareStatement(deleteEmployeeQuery)) {
                        deleteEmployeeStmt.setInt(1, employeeId);
                        int rowsDeleted = deleteEmployeeStmt.executeUpdate();

                        if (rowsDeleted > 0) {
                            connection.commit(); // Commit transaction
                            stage.close();
                            showAlert("Success", "Employee removed successfully!");
                        } else {
                            connection.rollback(); // Rollback if deletion failed
                            showAlert("Error", "Failed to remove employee.");
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    showAlert("Error", "Database error occurred.");
                }
            } else {
                showAlert("Selection Error", "Please select an employee from the list.");
            }
        });

        form.getChildren().addAll(employeeNameField, searchButton, employeeListView, deleteButton);

        Scene scene = new Scene(form, 400, 300);
        stage.setScene(scene);
        stage.show();
    }


    private void showSearchEmployeeDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Search Employee");
        dialog.setHeaderText("Enter Employee Name");
        dialog.setContentText("Search:");

        dialog.showAndWait().ifPresent(text -> {
            try (Connection connection = DatabaseUtil.getConnection()) {
                String query = "SELECT * FROM employees WHERE firstname LIKE ? OR lastname LIKE ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, "%" + text + "%");
                statement.setString(2, "%" + text + "%");

                ResultSet resultSet = statement.executeQuery();

                StringBuilder resultText = new StringBuilder();
                boolean found = false;

                while (resultSet.next()) {
                    found = true;
                    String firstName = resultSet.getString("firstname");
                    String lastName = resultSet.getString("lastname");
                    String position = resultSet.getString("position");
                    String salary = resultSet.getString("salary");

                    resultText.append("Name: ").append(firstName).append(" ").append(lastName)
                            .append("\nPosition: ").append(position)
                            .append("\nSalary: ").append(salary)
                            .append("\n\n");
                }

                if (found) {
                    showAlert("Employees Found", resultText.toString());
                } else {
                    showAlert("Not Found", "No employees found with the given name.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                showAlert("Error", "Failed to search for employees.");
            }
        });
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Leave Management Section
    private VBox createLeaveManagementSection() {
        VBox leaveManagementBox = new VBox(15);
        leaveManagementBox.setAlignment(Pos.TOP_LEFT);
        leaveManagementBox.setPadding(new Insets(10));

        // Fetch leave requests from the database
        ObservableList<EmployeeLeaveRequest> leaveRequests = fetchLeaveRequestsFromDatabase();

        // Create a list of leave requests
        for (EmployeeLeaveRequest request : leaveRequests) {
            HBox requestBox = new HBox(10);
            requestBox.setAlignment(Pos.CENTER_LEFT);

            Label leaveDetails = new Label(request.getEmployeeName() + " - " + request.getLeaveType() +
                    " (" + request.getStartDate() + " to " + request.getEndDate() + ")");

            Button approveButton = new Button("Approve");
            approveButton.setOnAction(e -> {
                requestBox.setStyle("-fx-background-color: lightgreen; -fx-border-color: lightgray;");
                approveLeaveRequest(request);
            });

            Button declineButton = new Button("Decline");
            declineButton.setOnAction(e -> {
                requestBox.setStyle("-fx-background-color: lightcoral; -fx-border-color: lightgray;");
                declineLeaveRequest(request);
            });

            requestBox.getChildren().addAll(leaveDetails, approveButton, declineButton);
            leaveManagementBox.getChildren().add(requestBox);
        }

        return leaveManagementBox;
    }

    private ObservableList<EmployeeLeaveRequest> fetchLeaveRequestsFromDatabase() {
        ObservableList<EmployeeLeaveRequest> leaveRequests = FXCollections.observableArrayList();

        String query = """
            SELECT lr.requestid, lr.employeeid, e.firstname, lr.leavetype, 
                   lr.startdate, lr.enddate, lr.reason, lr.status
            FROM public.leaverequests lr
            JOIN public.employees e ON lr.employeeid = e.employeeid
            WHERE lr.status = 'Pending'
            ORDER BY lr.requestid ASC;
        """;

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int requestId = resultSet.getInt("requestid");
                int employeeId = resultSet.getInt("employeeid");
                String employeeName = resultSet.getString("firstname");
                String leaveType = resultSet.getString("leavetype");
                String startDate = resultSet.getString("startdate");
                String endDate = resultSet.getString("enddate");
                String reason = resultSet.getString("reason");
                String status = resultSet.getString("status");

                leaveRequests.add(new EmployeeLeaveRequest(
                        requestId, employeeId, employeeName, leaveType, startDate, endDate, reason, status
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return leaveRequests;
    }

    private void approveLeaveRequest(EmployeeLeaveRequest request) {
        updateLeaveRequestStatus(request.getRequestId(), "Approved");
        System.out.println("Approved leave for " + request.getEmployeeName());
    }

    private void declineLeaveRequest(EmployeeLeaveRequest request) {
        updateLeaveRequestStatus(request.getRequestId(), "Declined");
        System.out.println("Declined leave for " + request.getEmployeeName());
    }

    private void updateLeaveRequestStatus(int id, String status) {
        String query = "UPDATE LeaveRequests SET status = ? WHERE requestid = ?";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, status);
            statement.setInt(2, id);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Successfully updated status to " + status + " for request ID: " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Payroll Management Section
    private VBox createPayrollManagementSection() {
        VBox payrollManagementBox = new VBox(15);
        payrollManagementBox.setAlignment(Pos.TOP_LEFT);
        payrollManagementBox.setPadding(new Insets(10));

        Button generatePayslipsButton = new Button("Generate Payslips");
        Button viewPayrollHistoryButton = new Button("View Payroll History");

        generatePayslipsButton.setOnAction(e -> generatePayslips());
        viewPayrollHistoryButton.setOnAction(e -> showPayrollHistory());

        payrollManagementBox.getChildren().addAll(
                generatePayslipsButton, viewPayrollHistoryButton
        );

        return payrollManagementBox;
    }

    private void generatePayslips() {
        showAlert("Info", "Generate Payslips - Not Implemented Yet.");
    }

    private void showPayrollHistory() {
        Stage stage = new Stage();
        stage.setTitle("Payroll History");

        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.setAlignment(Pos.CENTER);

        TableView<PayrollRecord> payrollTable = new TableView<>();

        // Define table columns
        TableColumn<PayrollRecord, Integer> payrollIdColumn = new TableColumn<>("Payroll ID");
        payrollIdColumn.setCellValueFactory(new PropertyValueFactory<>("payrollId"));

        TableColumn<PayrollRecord, Integer> employeeIdColumn = new TableColumn<>("Employee ID");
        employeeIdColumn.setCellValueFactory(new PropertyValueFactory<>("employeeId"));

        TableColumn<PayrollRecord, Double> basicSalaryColumn = new TableColumn<>("Basic Salary");
        basicSalaryColumn.setCellValueFactory(new PropertyValueFactory<>("basicSalary"));

        TableColumn<PayrollRecord, Double> bonusesColumn = new TableColumn<>("Bonuses");
        bonusesColumn.setCellValueFactory(new PropertyValueFactory<>("bonuses"));

        TableColumn<PayrollRecord, Double> deductionsColumn = new TableColumn<>("Deductions");
        deductionsColumn.setCellValueFactory(new PropertyValueFactory<>("deductions"));

        TableColumn<PayrollRecord, Double> taxColumn = new TableColumn<>("Tax");
        taxColumn.setCellValueFactory(new PropertyValueFactory<>("tax"));

        TableColumn<PayrollRecord, Double> netSalaryColumn = new TableColumn<>("Net Salary");
        netSalaryColumn.setCellValueFactory(new PropertyValueFactory<>("netSalary"));

        TableColumn<PayrollRecord, String> paymentDateColumn = new TableColumn<>("Payment Date");
        paymentDateColumn.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));

        payrollTable.getColumns().addAll(payrollIdColumn, employeeIdColumn, basicSalaryColumn, bonusesColumn, deductionsColumn, taxColumn, netSalaryColumn, paymentDateColumn);

        // Fetch payroll data from database
        ObservableList<PayrollRecord> payrollList = FXCollections.observableArrayList();

        String query = "SELECT * FROM public.payroll ORDER BY payrollid ASC";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int payrollId = resultSet.getInt("payrollid");
                int employeeId = resultSet.getInt("employeeid");
                double basicSalary = resultSet.getDouble("basicsalary");
                double bonuses = resultSet.getDouble("bonuses");
                double deductions = resultSet.getDouble("deductions");
                double tax = resultSet.getDouble("tax");
                double netSalary = resultSet.getDouble("netsalary");
                String paymentDate = resultSet.getString("paymentdate");

                payrollList.add(new PayrollRecord(payrollId, employeeId, basicSalary, bonuses, deductions, tax, netSalary, paymentDate));
            }

            payrollTable.setItems(payrollList);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "Failed to retrieve payroll history.");
        }

        root.getChildren().add(payrollTable);

        Scene scene = new Scene(root, 800, 500);
        stage.setScene(scene);
        stage.show();
    }


    // Performance Evaluation Section
    private VBox createPerformanceEvaluationSection() {
        VBox performanceEvaluationBox = new VBox(15);
        performanceEvaluationBox.setAlignment(Pos.TOP_LEFT);
        performanceEvaluationBox.setPadding(new Insets(10));

        Button addEvaluationButton = new Button("Add Evaluation");
        Button viewEvaluationHistoryButton = new Button("View Evaluation History");

        addEvaluationButton.setOnAction(e -> addPerformanceEvaluation());
        viewEvaluationHistoryButton.setOnAction(e -> showPerformanceEvaluationHistory());

        performanceEvaluationBox.getChildren().addAll(
                addEvaluationButton, viewEvaluationHistoryButton
        );

        return performanceEvaluationBox;
    }

    private void addPerformanceEvaluation() {
        showAlert("Info", "Add Performance Evaluation - Not Implemented Yet.");
    }

    private void showPerformanceEvaluationHistory() {
        showAlert("Info", "View Performance Evaluation History - Not Implemented Yet.");
    }

    // Training Management Section
    private VBox createTrainingManagementSection() {
        VBox trainingManagementBox = new VBox(15);
        trainingManagementBox.setAlignment(Pos.TOP_LEFT);
        trainingManagementBox.setPadding(new Insets(10));

        Button assignTrainingButton = new Button("Assign Training");
        Button viewTrainingHistoryButton = new Button("View Training History");

        assignTrainingButton.setOnAction(e -> assignTraining());
        viewTrainingHistoryButton.setOnAction(e -> showTrainingHistory());

        trainingManagementBox.getChildren().addAll(
                assignTrainingButton, viewTrainingHistoryButton
        );

        return trainingManagementBox;
    }

    private void assignTraining() {
        showAlert("Info", "Assign Training - Not Implemented Yet.");
    }

    private void showTrainingHistory() {
        showAlert("Info", "View Training History - Not Implemented Yet.");
    }

    // Reports Section
    private VBox createReportsSection() {
        VBox reportsBox = new VBox(15);
        reportsBox.setAlignment(Pos.TOP_LEFT);
        reportsBox.setPadding(new Insets(10));

        Button generateReportsButton = new Button("Generate Reports");
        generateReportsButton.setOnAction(e -> generateReports());

        reportsBox.getChildren().add(generateReportsButton);

        return reportsBox;
    }

    private void generateReports() {
        showAlert("Info", "Generate Reports - Not Implemented Yet.");
    }

    // Handle Logout
    private void logout(Stage stage) {
        LoginInterface login = new LoginInterface();
        login.start(stage);
    }
}