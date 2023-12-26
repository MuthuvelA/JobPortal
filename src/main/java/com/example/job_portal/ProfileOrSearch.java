package com.example.job_portal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

import static com.example.job_portal.RegisterControllerJobSeeker.BackScene;

public class ProfileOrSearch {

    public Button edit_profile;
    public Button search;
    public TextField jobiid;
    public Button backbutton;
    public TextArea showstatus;
    public TextField cname;
    public Button logoutid;

    public void HandleEditProfile(ActionEvent actionEvent) {
        try {
            FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("profile-view-job-seeker.fxml"));
            Parent loginRoot = loginLoader.load();
            Stage curStage = (Stage) edit_profile.getScene().getWindow();
            Scene s = new Scene(loginRoot);
            BackScene.push(curStage.getScene());
            curStage.setScene(s);
            curStage.setTitle("Login Page");
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void HandleSearch(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("JobSearch.fxml"));
            Parent root = loader.load();

            Stage curStage = (Stage) search.getScene().getWindow();
            BackScene.push(curStage.getScene());
            curStage.setScene(new Scene(root));
            curStage.setTitle("SEARCH JOB");
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void Handlestatus(ActionEvent actionEvent) {
        String url = "jdbc:mysql://localhost:3306/project";
        String user = "root";
        String pass = "SHRI@2005";

        try (Connection connection = DriverManager.getConnection(url, user, pass)) {
            // Retrieve and display status from the 'application' table
            String selectStatusSql = "SELECT status FROM application WHERE jobid = ?";
            int specifiedJobId = getSelectedJobId();

            try (PreparedStatement statusPreparedStatement = connection.prepareStatement(selectStatusSql)) {
                statusPreparedStatement.setInt(1, specifiedJobId);
                ResultSet statusResultSet = statusPreparedStatement.executeQuery();

                if (statusResultSet.next()) {
                    String status = statusResultSet.getString("status");
                    showstatus.setText("Job Application Status: " + status);
                } else {
                    showstatus.setText("No record found for the specified job ID.");
                }
            }

            // Fetch details from the 'company' table based on the entered company name
            String selectCompanySql = "SELECT * FROM company WHERE companyname = ?";
            String specifiedCompanyName = cname.getText().trim();

            try (PreparedStatement companyPreparedStatement = connection.prepareStatement(selectCompanySql)) {
                companyPreparedStatement.setString(1, specifiedCompanyName);
                ResultSet companyResultSet = companyPreparedStatement.executeQuery();

                if (companyResultSet.next()) {
                    String companyDetails = String.format(
                            "Company Name: %s\nPosition: %s\nAbout Us: %s\n",
                            companyResultSet.getString("companyname"),
                            companyResultSet.getString("position"),
                            companyResultSet.getString("aboutus")
                            // Add other fields as needed
                    );
                    showstatus.appendText("\n\n" + companyDetails);
                } else {
                    showstatus.appendText("\n\nNo record found for the specified company name.");
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
            showAlert(Alert.AlertType.ERROR, "Database Error", "Error retrieving data from the database");
        }
    }


    // Helper method to show an alert
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private int getSelectedJobId() {
        // Assuming 'jobiid' is a TextField where the user enters the job ID
        String jobIdText = jobiid.getText().trim();

        try {
            // Parse the input as an integer
            return Integer.parseInt(jobIdText);
        } catch (NumberFormatException e) {
            // Handle the case where the input is not a valid integer (e.g., show an alert)
            showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter a valid Job ID.");
            return -1;  // or throw an exception, depending on your error-handling strategy
        }
    }

    public void HandleBack(ActionEvent actionEvent) {
        if(!BackScene.isEmpty()){
            Stage curStage = (Stage) backbutton.getScene().getWindow();
            curStage.setScene(BackScene.pop());
        }
    }

    public void HandleLogout(ActionEvent actionEvent) {
//        SessionManager.clearSession();

        // Navigate back to the login page
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Decider.fxml"));
            Parent root = loader.load();

            Stage curStage = (Stage) logoutid.getScene().getWindow();
            Scene newScene = new Scene(root);
            BackScene.clear(); // Clear the BackScene stack when logging out
            curStage.setScene(newScene);
            curStage.setTitle("Login Page");
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
