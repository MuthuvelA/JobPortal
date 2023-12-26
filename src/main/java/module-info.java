module com.example.jobportal {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.job_portal to javafx.fxml;
    exports com.example.job_portal;
}