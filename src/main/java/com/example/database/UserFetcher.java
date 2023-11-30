package com.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserFetcher {
    private static final String DB_URL = "jdbc:sqlite:quiz.db";

    public static List<String> return_users() {
        List<String> names = new ArrayList<>();
        String sql = "SELECT username FROM users";
    
        try (Connection conn = DriverManager.getConnection(DB_URL);
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
    
            // Set the values
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                names.add(rs.getString("username"));
            }
    
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } 

        return names;
    }

    public static void main(String[] args) {
        for (String user: return_users()) {
            System.out.println(user);
        }
    }
}
