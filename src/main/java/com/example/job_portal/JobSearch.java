package com.example.job_portal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.job_portal.RegisterControllerJobSeeker.BackScene;

public class JobSearch {

    @FXML
    public TextField searchable;
    @FXML
    public Button search;
    @FXML
    public TextArea resulttid;
    public Button applyid;
    public Button backbutton;

    public void HandleSearch(ActionEvent actionEvent) {
        System.out.println("Searched");



        // Fetch data from the database based on the search criteria

        String sql = "SELECT * FROM jobposting WHERE jobtitle = ?";
        String searchQuery = searchable.getText();
        String url = "jdbc:mysql://localhost:3306/project";
        String user = "root";
        String pass = "SHRI@2005";

        try (Connection connection = DriverManager.getConnection(url, user, pass);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Set the search parameter
            preparedStatement.setString(1, searchQuery);

            // Execute the query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    // Assuming you have a 'description' column in your jobposting table
                    String jobtitle = resultSet.getString("jobtitle");
                    String jobdescription = resultSet.getString("jobdescription");
                    String jobtype = resultSet.getString("jobtype");
                    String skills_required = resultSet.getString("skills_required");
                    int jobnumber = resultSet.getInt("jobnumber");

//                    System.out.println("Job Title: " + jobtitle);
//                    System.out.println("Job Description: " + jobdescription);
//                    System.out.println("Job Type: " + jobtype);
//                    System.out.println("Skills Required: " + skills_required);
//                    System.out.println("Job Number: " + jobnumber);

                    // Append the result to the TextArea
                    resulttid.appendText("Job Title: " + jobtitle + "\n");
                    resulttid.appendText("Job Description: " + jobdescription + "\n");
                    resulttid.appendText("Job Type: " + jobtype + "\n");
                    resulttid.appendText("Skills Required: " + skills_required + "\n");
                    resulttid.appendText("Job Number: " + jobnumber + "\n\n");
                }
            }

        } catch (SQLException e) {
            System.out.println(e);
            resulttid.setText("NOT FOUND!!!");
        }
    }

    public void HandleApply(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("JobApply.fxml"));
            Parent root = loader.load();

            Stage curStage = (Stage) applyid.getScene().getWindow();
            BackScene.push(curStage.getScene());
            curStage.setScene(new Scene(root));
            curStage.setTitle("APPLY FOR JOB");
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void HandleBack(ActionEvent actionEvent) {
        if(!BackScene.isEmpty()){
            Stage curStage = (Stage) backbutton.getScene().getWindow();
            curStage.setScene(BackScene.pop());
        }
    }
}
