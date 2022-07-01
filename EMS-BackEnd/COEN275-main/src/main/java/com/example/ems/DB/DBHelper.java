package com.example.ems.DB;

import java.sql.Connection;
import java.sql.*;

public class DBHelper {
    public Connection connection;
    public Connection DBConnect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            String url = "jdbc:mysql://localhost:3306/ems";
            connection = DriverManager.getConnection(url, "root", "admin");
            System.out.println("Connected");
        }catch (Exception e){
            System.err.println("Unable to connect");
            e.printStackTrace();
        }
        return connection;
    }

    public void DBClose(){
        try{
            connection.close();
        }
        catch (Exception e){
            System.err.println("Unable to close db");
            e.printStackTrace();
        }
    }

}
