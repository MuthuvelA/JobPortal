package com.example.job_portal;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.example.job_portal.RegisterControllerEmployer.backScene;
import static com.example.job_portal.RegisterControllerJobSeeker.BackScene;

public class JobPost {
    public Button upload;
    public TextArea description;
    public TextArea skills;
    public TextField jtitle;
    public TextField jtype;
    public TextField jobno;
    public Button backbutton;

    private void showAlertIfFieldsEmpty(String... fields) {
        for (String field : fields) {
            if (field == null || field.trim().isEmpty()) {
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
//        backScene.push();
        String jobTitle = jtitle.getText();
        String jobDescription = description.getText();
        String jobType = jtype.getText();
        String skillsRequired = skills.getText();
        String jobNumber = jobno.getText();

        showAlertIfFieldsEmpty(jobTitle, jobDescription, jobType, skillsRequired, jobNumber);

        boolean saveSuccessful = saveToDatabase(jobTitle, jobDescription, jobType, skillsRequired, jobNumber);

        if (saveSuccessful) {
            showAlert("Save Successful", "Data saved successfully!");
        } else {
            showAlert("Save Failed", "Failed to save data. Please try again.");
        }
    }

    private boolean saveToDatabase(String jobTitle, String jobDescription, String jobType,
                                   String skillsRequired, String jobNumber) {
        String url = "jdbc:mysql://localhost:3306/project";
        String user = "root";
        String pass = "SHRI@2005";

        try (Connection connection = DriverManager.getConnection(url, user, pass)) {
            String query = "INSERT INTO jobposting (jobtitle, jobdescription, jobtype, skills_required, jobnumber) " +
                    "VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, jobTitle);
                preparedStatement.setString(2, jobDescription);
                preparedStatement.setString(3, jobType);
                preparedStatement.setString(4, skillsRequired);
                preparedStatement.setString(5, jobNumber);

                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            } catch (SQLException e) {
                System.out.println(e);
            }
        } catch (SQLException e) {
            System.out.println(e);
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