package com.example.job_portal;

import com.example.job_portal.utils.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Stack;

public class RegisterControllerJobSeeker {

    @FXML
    protected static Stack<Scene> BackScene = new Stack<>();

    public Button signup;
    @FXML
    public Button login;
    @FXML
    public TextField textfield;
    @FXML
    public TextField passwordfield;

    private String userType; // Add this variable to store userType

    @FXML
    private void changeScene(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage curStage = (Stage) signup.getScene().getWindow();
            curStage.setScene(new Scene(root));
            curStage.setTitle(title);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleLogin(ActionEvent actionEvent) {
        String username = textfield.getText();
        String password = passwordfield.getText();
        UserSession.username = username;

        // Validate login and fetch userType
        if (validateLogin(username, password)) {
            // If login is successful, change the scene to the profile page
            //UserSession.username = username;
            UserSession.userType =  "Jobseeker";
//            SessionManager.setSession(username,"jobseeker");
//            SessionManager.insertSession();
            changeScene("ProfileOrSearch.fxml", "Profile Page seeker");
        } else {
            // If login fails, show an error message
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password");
        }
    }

    public void handleSignup(ActionEvent actionEvent) {
        // Your existing signup logic here
        changeScene("SignUpJobSeeker.fxml", "signup Page jobseeker");
        // After successful signup, update userType in the person table
    }

    private boolean validateLogin(String username, String password) {
        String url = "jdbc:mysql://localhost:3306/project";
        String user = "root";
        String pass = "SHRI@2005";

        try (Connection connection = DriverManager.getConnection(url, user, pass)) {
            String query = "SELECT * FROM person WHERE Username = ? AND Password = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {

                    return resultSet.next(); // Return true if there is at least one matching row
                }
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Error fetching userType from the database");
            return false;
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
