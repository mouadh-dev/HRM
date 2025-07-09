package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
    private static final String URL = DatabaseConfig.DB_URL;
    private static final String USERNAME = DatabaseConfig.DB_USERNAME;
    private static final String PASSWORD = DatabaseConfig.DB_PASSWORD;


    public static Connection getConnection() throws SQLException {
        try {
            // Load PostgreSQL JDBC Driver (Optional if using modern JDBC)
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL Driver not found!");
            e.printStackTrace();
        }

        // Establish and return the connection
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}