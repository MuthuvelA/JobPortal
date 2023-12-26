package com.example.job_portal;
// FIRST TIME LOGIN CONTROLLER JOBSEEKER
import javafx.event.ActionEvent;
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
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.example.job_portal.RegisterControllerJobSeeker.BackScene;

public class ReSignUpControllerJobSeeker {
    public Button signup;
    public TextField textfield;
    public PasswordField passwordfield;
    public TextField mailid;
    public TextField phoneno;

    public void handleRegister(ActionEvent actionEvent) {
        String usernameValue = textfield.getText(); // Get the username from the label
        String passwordValue = passwordfield.getText(); // Get the password from the label
        String emailvalue = mailid.getText();
        String phonenovalue = phoneno.getText();
        try {

            Connection connection = DatabaseConnection.getConnection();


            // Insert data into the database
            String sql = "INSERT INTO person     (Username, Password,email,phone) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, usernameValue);
                statement.setString(2, passwordValue);
                statement.setString(3, emailvalue);
                statement.setString(4, phonenovalue );

                // Execute the SQL statement
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Close the database connection
            connection.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("ProfileOrSearch.fxml"));
            Parent root = loader.load();

            Stage curStage = (Stage) signup.getScene().getWindow();
            BackScene.push(curStage.getScene());
            curStage.setScene(new Scene(root));
            curStage.setTitle("hi");
        } catch (IOException e) {
            System.out.println(e);;
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
