package com.example.job_portal;

import com.example.job_portal.utils.EmployerSession;
import com.example.job_portal.utils.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Stack;

public class RegisterControllerEmployer {

    protected static Stack<Scene> backScene = new Stack<>();

    @FXML
    public Button signup;
    @FXML
    public Button login;
    public TextField enewuse;
    public PasswordField enewpass;
    public Button empbtn;

    @FXML
    private void changeScene(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage curStage = (Stage) signup.getScene().getWindow();
            backScene.push(curStage.getScene());
            curStage.setScene(new Scene(root));
            curStage.setTitle(title);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
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
                    if (resultSet.next()) {

                        return true; // Return true if there is at least one matching row
                    } else {
                        return false; // No matching row found
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false in case of an exception
        }
    }

    @FXML
    public void handleLogin(ActionEvent actionEvent) {
        String username = enewuse.getText();
        String password = enewpass.getText();

        // Validate login
        boolean loginSuccessful = validateLogin(username, password);

        if (loginSuccessful) {
            // If login is successful, change the scene to the profile page
//            SessionManager.setSession(username,"employer");
//            SessionManager.insertSession();
            EmployerSession.username = username;
            EmployerSession.userType =  "Employer";
            changeScene("StatusorPost.fxml", "MENU PAGE");
        } else {
            // If login fails, show an error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText(null);
            alert.setContentText("Invalid username or password");
            alert.showAndWait();
        }
    }

    @FXML
    public void handleRegister(ActionEvent actionEvent) {
        changeScene("SignUpEmployer.fxml", "signup Page employer");
    }

    public void setUserType(String employer) {
        String url = "jdbc:mysql://localhost:3306/project";
        String user = "root";
        String pass = "SHRI@2005";

        try (Connection connection = DriverManager.getConnection(url, user, pass)) {
            String updateQuery = "UPDATE person SET userType = ? WHERE Username = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, "employer");
                preparedStatement.setString(2, employer);
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("UserType updated successfully.");
                } else {
                    System.out.println("Failed to update userType.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
