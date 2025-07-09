package com.example.demo;

import Dao.EmployeeDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
        EmployeeDAO employeeDAO = new EmployeeDAO();
        employeeDAO.addEmployee(
                "mouadh",
                "Doe",
                "Software Developer",
                "IT",
                "2023-01-15",
                "TEST@GMAIL.COM"
        );
    }
}