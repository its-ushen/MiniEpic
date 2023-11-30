package com.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

public class ScoreLogger {

    private static final String DB_URL = "jdbc:sqlite:quiz.db";

    // A method to log a user's score
    public static void log_score(String username, String difficulty, int score) {
    // Check that difficulty is one of the expected values
    if (!Arrays.asList("Quickfire", "Novice", "Intermediate", "Advanced").contains(difficulty)) {
        System.out.println("Invalid quiz difficulty provided.");
        return;
    }

    // SQL statement to insert a new score into the scores table
    String sql = "INSERT INTO scores(username, difficulty, score) VALUES(?, ?, ?)";

    // Try-with-resources block to automatically close the connection
    try (Connection conn = DriverManager.getConnection(DB_URL);
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
        // Set the values
        pstmt.setString(1, username);
        pstmt.setString(2, difficulty);
        pstmt.setInt(3, score);
        pstmt.executeUpdate();

        System.out.println("Score logged successfully!");

    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
}


    // Test out the log_score function
    public static void main(String[] args) {
        log_score("Ushen", "Novice", 100);
        log_score("Jane", "Novice", 75);    
        log_score("John", "Novice", 80);    
        log_score("Ahmed", "Novice", 90);    
    }
}
