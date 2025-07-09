package Dao;

import com.example.demo.MainInterface;
import util.DatabaseUtil;

import java.sql.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AuthenticationDao {
    // Sign In (Authenticate existing user)
    public String signIn(String username, String password) throws SQLException {
        System.out.println("Attempting to sign in with username: " + username);
        String sql = "SELECT role,employeeid,crypt(?, PasswordHash) = PasswordHash FROM UserAccounts WHERE LOWER(Username) = LOWER(?)";
        System.out.println(sql);

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, password);  // Entered password
            ps.setString(2, username);  // Username
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                MainInterface.setLoggedInEmployeeId(rs.getInt("employeeid"));
//                boolean isMatch = rs.getBoolean("is_match");
//                System.out.println("Password match: " + isMatch);
                return rs.getString("role");
            } else {
                System.out.println("User not found.");
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
