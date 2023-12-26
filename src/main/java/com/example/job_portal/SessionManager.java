package com.example.job_portal;

import java.sql.*;

public class SessionManager {

    private static String activeSession; // Assuming this variable stores the current active session (e.g., username)
    private static String activetype;


    // Method to get the current active session (e.g., username)
    public static String getSession() {
        return activeSession;
    }
    public static String getType() {
        return activetype;
    }

    // Method to set the current active session (e.g., username)
    public static void setSession(String username,String type) {
        activeSession = username;
        activetype=type;
    }

    // Method to insert session data into the session table
    public static boolean insertSession() {
        String url = "jdbc:mysql://localhost:3306/project";
        String user = "root";
        String pass = "SHRI@2005";

        String username = getSession();
        String activetype=getType();

        if (username == null) {
            return false; // No active session to insert
        }

        try (Connection connection = DriverManager.getConnection(url, user, pass)) {
            String query = "INSERT INTO session (username,type) VALUES (?,?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, activetype);
                int rowsAffected = preparedStatement.executeUpdate();

                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    // Method to clear the session (logout) and remove session data from the session table
    public static boolean clearSession() {
        String url = "jdbc:mysql://localhost:3306/project";
        String user = "root";
        String pass = "SHRI@2005";

        String username = getSession();

        if (username == null) {
            return false; // No active session to clear
        }

        try (Connection connection = DriverManager.getConnection(url, user, pass)) {
            String query = "DELETE FROM session WHERE username = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                int rowsAffected = preparedStatement.executeUpdate();

                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }
}
