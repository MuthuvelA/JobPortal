package com.example.job_portal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private DatabaseConnection() {}
    static String user = "root";
    static String password = "SHRI@2005";
    static String url = "jdbc:mysql://localhost:3306/project";
    static String driver  = "com.mysql.cj.jdbc.Driver";

    private static Connection dbConnection;

    private static Connection establishConnection() {
        Connection connection = null;
        try {
            Class.forName(driver);
        }
        catch(ClassNotFoundException e){
            System.out.println(e);
        }
        try {
            connection = DriverManager.getConnection(url, user, password);
        }
        catch(SQLException e){
            System.out.println(e);
        }
        return connection;
    }
    public static Connection getConnection(){
        if (dbConnection == null) {
            dbConnection = establishConnection();
        }
        return dbConnection;
    }

}