package com.example.coursework1;

import java.sql.*;

public class Database {
    static Connection connection;
    static Statement statement;

    static void connection()  {
        try {
            // below two lines are used for connectivity.
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:8080/restaurant",
                    "root", "");
            statement = connection.createStatement();
        }catch (Exception e){
            System.out.println(e);
        }
    }
    static ResultSet readDatabase(String query) {
        try {
            connection();
            return statement.executeQuery(query);
        }
        catch (Exception exception) {
            System.out.println(exception);
            return null;
        }
    } // function ends
    static void writeDatabase(String query){
        try{
            connection();
            PreparedStatement pstmt=connection.prepareStatement(query);
            pstmt.executeUpdate();
        }catch (Exception e){
            System.out.println(e);
        }
    }
} // class ends
