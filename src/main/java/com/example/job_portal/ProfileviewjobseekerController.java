package com.example.job_portal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.example.job_portal.RegisterControllerJobSeeker.BackScene;

public class ProfileviewjobseekerController {
    @FXML
    public TextField emailidd;
    @FXML
    public TextField phoneid;
    @FXML
    public TextField addressid;
    @FXML
    public TextField nameid;
    @FXML
    public TextField ageid;
    @FXML
    public TextField genid;
    @FXML
    public Button saveid;
    @FXML
    public TextArea aboutiid;
    @FXML
    public TextArea qualificationiid;
    @FXML
    public TextArea experienceid;
    @FXML
    public TextArea skillssid;
    public Button backbutton;
    public TextArea resumeid;

    private void showAlertIfFieldsEmpty(String... fields) {
        for (String field : fields) {
            if (field == null || field.trim().isEmpty()) {
                // If any field is empty, show an alert and return
                showAlert("Error", "All fields must be filled in.");
                return;
            }
        }
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    public void handleSave(ActionEvent actionEvent) {
        String name = nameid.getText();
        String address = addressid.getText();
        String email = emailidd.getText();
        String phone = phoneid.getText();
        String age = ageid.getText();
        String gender = genid.getText();
        String about = aboutiid.getText();  // Assuming about is a Label, not TextField
        String qualification = qualificationiid.getText();  // Assuming qualification is a Label
        String skills = skillssid.getText();  // Assuming skills is a Label
        String experience = experienceid.getText();  // Assuming experience is a Label
        String resume=resumeid.getText();

        // Validate inputs if needed
        showAlertIfFieldsEmpty(email, phone, address, name, age, gender);

        // Call a method to save the data to the database
        boolean saveSuccessful = saveToDatabase(name, address, email, phone, age, gender, about, qualification, skills, experience,resume);

        // Show a success or error message
        if (saveSuccessful) {
            showAlert("Save Successful", "Data saved successfully!");
        } else {
            showAlert("Save Failed", "Failed to save data. Please try again.");
        }
    }
    private boolean saveToDatabase(String name, String address, String email, String phone, String age,
                                   String gender, String about, String qualification, String skills, String experience,String resumelink) {
        String url = "jdbc:mysql://localhost:3306/project";
        String user = "root";
        String pass = "SHRI@2005";

        try (Connection connection = DriverManager.getConnection(url, user, pass)) {
            String query = "INSERT INTO jobseeker (Name, About, Qualification, Experience, Skills, Address, Mailid, PhoneNo, Gender, Age, resumelink) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, about);
                preparedStatement.setString(3, qualification);
                preparedStatement.setString(4, experience);
                preparedStatement.setString(5, skills);
                preparedStatement.setString(6, address);
                preparedStatement.setString(7, email);
                preparedStatement.setString(8, phone);
                preparedStatement.setString(9, gender);
                preparedStatement.setString(10, age);
                preparedStatement.setString(11, resumelink);

                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            } catch (SQLException e) {
                System.out.println(e);
            }
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
        return false;
    }

    public void HandleBack(ActionEvent actionEvent) {
        if(!BackScene.isEmpty()){
            Stage curStage = (Stage) backbutton.getScene().getWindow();
            curStage.setScene(BackScene.pop());
        }
    }
}
