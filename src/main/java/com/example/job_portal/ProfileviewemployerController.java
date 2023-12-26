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

public class ProfileviewemployerController {

    public Button backbutton;
    public Button saveid;
    public TextArea contactinfoid;
    public TextArea companyoverviewid;
    public TextArea aboutcompanyid;
    public TextField mailiid;
    public TextField mobileid;
    public TextField cnameid;
    public TextField positionid;
    public TextField empnameid;
    public TextArea jobopeningid;
    public TextArea socialid;

    public void HandleBack(ActionEvent actionEvent) {
        if (!backScene.isEmpty()) {
            Stage curStage = (Stage) backbutton.getScene().getWindow();
            curStage.setScene(backScene.pop());
        }
    }

    public void HandleSave(ActionEvent actionEvent) {
        // Retrieve data from JavaFX controls
        String empname = empnameid.getText();
        String mailid = mailiid.getText();
        String mobile = mobileid.getText();
        String cname = cnameid.getText();
        String position = positionid.getText();
        String about = aboutcompanyid.getText();
        String companyoverview = companyoverviewid.getText();
        String jobopening = jobopeningid.getText();
        String contactinfo = contactinfoid.getText();
        String socialmedia = socialid.getText();

        // Validate inputs if needed
        if (empname == null || empname.trim().isEmpty()) {
            showAlert("Error", "Empname must be filled in.");
            return;
        }

        // Call a method to save the data to the database
        boolean saveSuccessful = saveToDatabase(empname, mailid, mobile, cname, position, about,
                companyoverview, jobopening, contactinfo, socialmedia);

        // Show a success or error message
        if (saveSuccessful) {
            showAlert("Save Successful", "Data saved successfully!");
        } else {
            showAlert("Save Failed", "Failed to save data. Please try again.");
        }
    }

    private boolean saveToDatabase(String empname, String mailid, String mobile, String cname,
                                   String position, String about, String companyoverview,
                                   String jobopening, String contactinfo, String socialmedia) {
        String url = "jdbc:mysql://localhost:3306/project";
        String user = "root";
        String pass = "SHRI@2005";

        try (Connection connection = DriverManager.getConnection(url, user, pass)) {
            String query = "INSERT INTO company (CompanyMailId, empname, phoneno, companyname, position, " +
                    "aboutus, companyoverview, jobopening, contactinfo, socialmedia) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, mailid);
                preparedStatement.setString(2, empname);
                preparedStatement.setString(3, mobile);
                preparedStatement.setString(4, cname);
                preparedStatement.setString(5, position);
                preparedStatement.setString(6, about);
                preparedStatement.setString(7, companyoverview);
                preparedStatement.setString(8, jobopening);
                preparedStatement.setString(9, contactinfo);
                preparedStatement.setString(10, socialmedia);

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

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
