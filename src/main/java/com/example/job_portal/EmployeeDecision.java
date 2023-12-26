package com.example.job_portal;

import com.example.job_portal.utils.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.job_portal.RegisterControllerEmployer.backScene;
import static com.example.job_portal.RegisterControllerJobSeeker.BackScene;


public class EmployeeDecision {

    public Button acceptButton;
    public Button rejectButton;

    public TextArea applicants;

    public Button fetchid;
    public TextField jobid;
    public Button backbutton;
    public Button fetchprofile;

    public TextField nameid;


    public void handleAccept(ActionEvent actionEvent) {
        // Assuming you have a method to retrieve the selected jobseeker username
        String username = getSelectedJobseekerUsername();

        // Update the status to "accept" for the specified job ID and username
        updateStatus("accept", getSelectedJobId(), username);
    }

    public void handleReject(ActionEvent actionEvent) {
        // Assuming you have a method to retrieve the selected jobseeker username
        String username = getSelectedJobseekerUsername();

        // Update the status to "reject" for the specified job ID and username
        updateStatus("reject", getSelectedJobId(), username);
    }
    private String getSelectedJobseekerUsername() {
        // Assuming 'nameid' is a TextField where the user enters the username
        return nameid.getText();
    }

    private void updateStatus(String status, int jobid, String username) {
        // Update the status in the 'application' table based on the specified job ID and username
        String updateSql = "UPDATE application SET status = ? WHERE jobid = ? AND name = ?";
        String url = "jdbc:mysql://localhost:3306/project";
        String user = "root";
        String pass = "SHRI@2005";

        try (Connection connection = DriverManager.getConnection(url, user, pass);
             PreparedStatement preparedStatement = connection.prepareStatement(updateSql)) {

            // Set the status, specified job ID, and username parameters
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, jobid);
//            preparedStatement.setString(3, username);
            preparedStatement.setString(3,username);

            // Execute the update
            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Status Updated", "Status updated successfully!");
            } else {
                showAlert(Alert.AlertType.ERROR, "Status NOT Updated", "ERROR");
            }

        } catch (SQLException e) {
            System.out.println(e);
            showAlert(Alert.AlertType.INFORMATION, "ERROR", "ERROR");
        }
    }


    // Helper method to show an alert
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void loadApplicantsInfo() {
        applicants.clear();

        // Load applicants information from the 'application' table into the 'applicants' TextArea
        String selectSql = "SELECT * FROM application";
        String url = "jdbc:mysql://localhost:3306/project";
        String user = "root";
        String pass = "SHRI@2005";

        try (Connection connection = DriverManager.getConnection(url, user, pass);
             PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int jobid = resultSet.getInt("jobid");
                String jobname = resultSet.getString("jobname");
                String status = resultSet.getString("status");
                String name = resultSet.getString("name");

                // Append the information to the 'applicants' TextArea
                applicants.appendText("Job ID: " + jobid + "\n");
                applicants.appendText("Job Name: " + jobname + "\n");
                applicants.appendText("Status: " + status + "\n");
                applicants.appendText("Name: " + name + "\n\n");
            }

        } catch (SQLException e) {
            System.out.println(e);
            applicants.setText("Error loading applicants information");
        }
    }


    private int getSelectedJobId() {
        try {
            // Assuming 'jobid' is a TextField where the user enters the job ID
            String jobIdText = jobid.getText().trim();
            return Integer.parseInt(jobIdText);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Job ID", "Please enter a valid integer for Job ID.");
            return -1; // Return a sentinel value to indicate an error
        }
    }



    public void handleinfo(ActionEvent actionEvent) {
        loadApplicantsInfo();
    }

    public void HandleBack(ActionEvent actionEvent) {
            if (!backScene.isEmpty()) {
                Stage curStage = (Stage) backbutton.getScene().getWindow();
                curStage.setScene(backScene.pop());
            }
            else {
                System.out.println("No Scenes");
            }
    }

    public void handleprofilefetch(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Fetchprofile.fxml"));
            Parent root = loader.load();
            Stage curStage = (Stage) fetchid.getScene().getWindow();
            Scene s = new Scene(root);
            BackScene.push(s);
            curStage.setScene(s);
            curStage.setTitle("APPLY FOR JOB");
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
