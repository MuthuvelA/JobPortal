package com.example.job_portal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.job_portal.RegisterControllerEmployer.backScene;
import static com.example.job_portal.RegisterControllerJobSeeker.BackScene;


public class Statusorpost {
    @FXML
    public Button JobPost;
    @FXML
    public Button requests;
    @FXML
    public Button editprofile;
    public Button backbutton;
    public Button logoutid;


    public void HandleJobPost(ActionEvent actionEvent) {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("JobPost.fxml"));
                Parent root = loader.load();
                Stage curStage = (Stage) JobPost.getScene().getWindow();
                Scene s = new Scene(root);
                BackScene.push(curStage.getScene());
                curStage.setScene(s);
                curStage.setTitle("hi");
            } catch (IOException e) {
                System.out.println(e);
            }
            System.out.println("JobPost");
    }

    public void HandleRequest(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EmployeeDecision.fxml"));
            Parent root = loader.load();

            Stage curStage = (Stage) requests.getScene().getWindow();
            Scene s = new Scene(root);
            BackScene.push(curStage.getScene());
            curStage.setScene(s);

            curStage.setTitle("DECISION");
        } catch (IOException e) {
            System.out.println(e);
        }

     }


    public void HandleEditProfile(ActionEvent actionEvent) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("profile-view-employer.fxml"));
                Parent root = loader.load();

                Stage curStage = (Stage) editprofile.getScene().getWindow();
                Scene s = new Scene(root);
                BackScene.push(curStage.getScene());
                curStage.setScene(s);
                curStage.setTitle("hi");
            } catch (IOException e) {
                System.out.println(e);
            }
    }

    public void HandleBack(ActionEvent actionEvent) {
        if(!backScene.isEmpty()){
            Stage curStage = (Stage) backbutton.getScene().getWindow();
            curStage.setScene(backScene.pop());
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
            backScene.clear(); // Clear the BackScene stack when logging out
            curStage.setScene(newScene);
            curStage.setTitle("Login Page");
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}

