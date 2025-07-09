package com.example.demo;

import Dao.AuthenticationDao;
import javafx.application.Application;
import javafx.stage.Stage;
import util.DatabaseUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class MainInterface extends Application {
    private static int loggedInEmployeeId;
    static AuthenticationDao auth = new AuthenticationDao();

    public static void main(String[] args) {
        try (Connection connection = DatabaseUtil.getConnection()) {
            if (connection != null) {
                System.out.println("Connected to the PostgreSQL database successfully!");
            } else {
                System.out.println("Failed to make a connection!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        LoginInterface login = new LoginInterface();
        login.start(primaryStage);
    }

    public static void setLoggedInEmployeeId(int id) {
        loggedInEmployeeId = id;
    }

    public static int getLoggedInEmployeeId() {
        return loggedInEmployeeId;
    }

    public static AuthenticationDao getAuth() {
        return auth;
    }
}