package com.example.job_portal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeciderController {

    public Button jobSeeker;
    public Button employer;

    @FXML
    private void handleJobSeekerButtonClick(ActionEvent actionEvent) {
        try {
            FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("RegisterJobSeeker.fxml"));
            Parent loginRoot = loginLoader.load();

            Stage curStage = (Stage) jobSeeker.getScene().getWindow();

            curStage.setScene(new Scene(loginRoot));
            curStage.setTitle("Login Page");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleJobPosterButtonClick(ActionEvent actionEvent) {
        try {
            FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("RegisterEmployer.fxml"));
            Parent loginRoot = loginLoader.load();

            Stage curStage = (Stage) employer.getScene().getWindow();
            curStage.setScene(new Scene(loginRoot));
            curStage.setTitle("Login Page");
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void updatePersonUserType(String userType, String username) {
        String url = "jdbc:mysql://localhost:3306/project";
        String user = "root";
        String pass = "SHRI@2005";

        try (Connection connection = DriverManager.getConnection(url, user, pass)) {
            String updateQuery = "UPDATE person SET userType = ? WHERE Username = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, userType);
                preparedStatement.setString(2, username);
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("UserType updated successfully.");
                } else {
                    System.out.println("Failed to update userType.");
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
