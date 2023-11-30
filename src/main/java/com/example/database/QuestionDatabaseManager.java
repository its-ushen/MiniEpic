package com.example.database;

import com.example.model.Question;
import java.sql.*;

public class QuestionDatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:quiz.db"; // Replace with your database path

    public QuestionDatabaseManager() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Method to add a question
    public void addQuestion(Question question) {
        String sql = "INSERT INTO questions (question, difficulty, option_a, option_b, option_c, correct_answer) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, question.getQuestionText());
            pstmt.setString(2, question.getDifficulty().toUpperCase());
            pstmt.setString(3, question.getOptions().get(0));
            pstmt.setString(4, question.getOptions().get(1));
            pstmt.setString(5, question.getOptions().get(2));
            pstmt.setString(6, question.getCorrectAnswer());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error adding question: " + e.getMessage());
        }
    }

    // Method to remove a question by its text
    public void removeQuestionByQuestionText(String questionText) {
        String sql = "DELETE FROM questions WHERE question = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, questionText);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error removing question: " + e.getMessage());
        }
    }
}
