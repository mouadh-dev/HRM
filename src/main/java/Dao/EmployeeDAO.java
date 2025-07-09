package Dao;

import org.mindrot.jbcrypt.BCrypt;
import util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDAO {

    public boolean addEmployee(String firstName, String lastName, String position, String department, String email, String salary) {
        boolean result = false;
        String employeeSql = "INSERT INTO employees (firstname, lastname, position, department, email, salary) VALUES (?, ?, ?,?,?,?)";
        String userAccountSql = "INSERT INTO useraccounts (employeeid, username, passwordhash, role) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement employeeStmt = conn.prepareStatement(employeeSql, PreparedStatement.RETURN_GENERATED_KEYS);
             PreparedStatement userAccountStmt = conn.prepareStatement(userAccountSql)) {

            // Add employee to the Employees table
            employeeStmt.setString(1, firstName);
            employeeStmt.setString(2, lastName);
            employeeStmt.setString(3, position);
            employeeStmt.setString(4, department);
            employeeStmt.setString(5, email);
            employeeStmt.setString(6, salary);
//            employeeStmt.setString(1, firstName);
//            employeeStmt.setString(2, lastName);
//            employeeStmt.setString(3, position);
//            employeeStmt.setString(4, department);
//            employeeStmt.setDate(5, java.sql.Date.valueOf(hireDate));
//            employeeStmt.setString(6, email);

            // Execute the employee insertion
            int rowsInserted = employeeStmt.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println(rowsInserted + " employee(s) added successfully!");

                // Retrieve the generated employee ID
                ResultSet generatedKeys = employeeStmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int employeeId = generatedKeys.getInt(1);

                    // Generate username (e.g., based on firstName and lastName)
                    String username = firstName.toLowerCase() + "." + lastName.toLowerCase();

                    // Hash the username to use as a password
                    String hashedPassword = BCrypt.hashpw(username, BCrypt.gensalt());

                    // Determine the role based on the position
                    String role = position.equalsIgnoreCase("HR") ? "HR" : "Employee";

                    // Add user account to the useraccounts table
                    userAccountStmt.setInt(1, employeeId);
                    userAccountStmt.setString(2, username);
                    userAccountStmt.setString(3, hashedPassword);
                    userAccountStmt.setString(4, role);

                    int userRowsInserted = userAccountStmt.executeUpdate();
                    System.out.println(userRowsInserted + " user account(s) added successfully!");
                }
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }
}

