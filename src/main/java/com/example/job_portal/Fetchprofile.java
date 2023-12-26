package com.example.job_portal;

import com.example.job_portal.utils.UserSession;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.job_portal.RegisterControllerEmployer.backScene;

public class Fetchprofile {
    public Button backbutton;
    public TextArea detailid;
    public Button fetchapplicant;
    public TextField usernameid;

    public void HandleBack(ActionEvent actionEvent) {
        if (!backScene.isEmpty()) {
            Stage curStage = (Stage) backbutton.getScene().getWindow();
            curStage.setScene(backScene.pop());
        } else {
            System.out.println("No Scenes");
        }
    }

    public void Fetchapplicantdetails(ActionEvent actionEvent) {
        String enteredUsername = usernameid.getText().trim();

        if (!enteredUsername.isEmpty()) {
            String url = "jdbc:mysql://localhost:3306/project";
            String user = "root";
            String pass = "SHRI@2005";

            String sql = "SELECT * FROM jobseeker WHERE Name = ?";

            try (Connection connection = DriverManager.getConnection(url, user, pass);
                 PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, enteredUsername);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        displayApplicantDetails(resultSet);
                    } else {
                        showAlert("Applicant not found");
                    }
                }
            } catch (SQLException e) {
                System.out.println(e);
                showAlert("Error fetching applicant details");
            }
        } else {
            showAlert("Please enter the applicant's username");
        }
    }

    private void displayApplicantDetails(ResultSet resultSet) {
        try {
            int jobseekerId = resultSet.getInt("JobseekerId");
            String name = resultSet.getString("Name");
            String about = resultSet.getString("About");
            String qualification = resultSet.getString("Qualification");
            String experience = resultSet.getString("Experience");
            String skills = resultSet.getString("Skills");
            String address = resultSet.getString("Address");
            String mailId = resultSet.getString("Mailid");
            int phoneNo = resultSet.getInt("PhoneNo");
            String gender = resultSet.getString("Gender");
            int age = resultSet.getInt("Age");
            String resumeLink = resultSet.getString("resumelink");

            String details = String.format("JobseekerId: %d\nName: %s\nAbout: %s\nQualification: %s\nExperience: %s\nSkills: %s\nAddress: %s\nMailid: %s\nPhoneNo: %d\nGender: %s\nAge: %d\nResumelink: %s\n",
                    jobseekerId, name, about, qualification, experience, skills, address, mailId, phoneNo, gender, age, resumeLink);

            detailid.setText(details);
        } catch (SQLException e) {
            showAlert("Error retrieving applicant details");
            e.printStackTrace(); // Log or handle the exception as needed
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
