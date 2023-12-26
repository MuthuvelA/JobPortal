package com.example.job_portal;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.example.job_portal.RegisterControllerJobSeeker.BackScene;

public class JobApply {
    public TextField job_id;
    public TextField job_name;
    public Button applyid;
    public TextField appusername;
    public Button backbutton;

    public void HandleApply(ActionEvent actionEvent){
        String url = "jdbc:mysql://localhost:3306/project";
        String user = "root";
        String pass = "SHRI@2005";
        String jobId = job_id.getText();
        String jobName = job_name.getText();
        String applicantUsername = appusername.getText(); // Changed variable name to reflect the TextField for username

        try (Connection connection = DriverManager.getConnection(url, user, pass)) {
            // Modified SQL query to include 'name' column
            String sql = "INSERT INTO application (jobid, jobname, name) VALUES (?, ?, ?)";

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, Integer.parseInt(jobId));
            pstmt.setString(2, jobName);
            pstmt.setString(3, applicantUsername);

            pstmt.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Application Status");
            alert.setHeaderText(null);
            alert.setContentText("APPLIED SUCCESSFULLY");
            alert.showAndWait();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void HandleBack(ActionEvent actionEvent) {
        if(!BackScene.isEmpty()){
            Stage curStage = (Stage) backbutton.getScene().getWindow();
            curStage.setScene(BackScene.pop());
        }
    }
}
