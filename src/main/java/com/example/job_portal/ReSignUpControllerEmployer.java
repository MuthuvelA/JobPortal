package com.example.job_portal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReSignUpControllerEmployer {
    @FXML
    public Button signup;
    @FXML
    public Label uname;
    @FXML
    public Label pass;
    @FXML
    public TextField empuname;
    @FXML
    public PasswordField emppassword;
    @FXML
    public TextField empmail;
    @FXML
    public TextField empphone;

    public void handleRegister(ActionEvent actionEvent) {
        String usernameValue = empuname.getText(); // Get the username from the label
        String passwordValue = emppassword.getText(); // Get the password from the label
        String emailvalue = empmail.getText();
        String phonenovalue = empphone.getText();
        try {

            Connection connection = DatabaseConnection.getConnection();


        String sql = "INSERT INTO person     (Username, Password,email,phone) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, usernameValue);
                statement.setString(2, passwordValue);
                statement.setString(3, emailvalue);
                statement.setString(4, phonenovalue );

                // Execute the SQL statement
                statement.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e);
            }

            // Close the database connection
            connection.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("Jobpost.fxml"));
            Parent root = loader.load();

            Stage curStage = (Stage) signup.getScene().getWindow();
            curStage.setScene(new Scene(root));
            curStage.setTitle("hi");
        } catch (IOException | SQLException e) {
            System.out.println(e);
        }
    }
}
