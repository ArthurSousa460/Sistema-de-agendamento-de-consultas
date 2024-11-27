package com.example.clinica.backend.Services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDatabase {

    private static final String URL = "jdbc:mysql://localhost:3306/clinica";
    private static final String USERNAME = "root";
    private static final String PASSSWORD = "";

    public static Connection connect(){
        try{
            return DriverManager.getConnection(URL, USERNAME, PASSSWORD);
        }catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
