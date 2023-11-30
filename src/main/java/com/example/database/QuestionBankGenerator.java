package com.example.database;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.example.model.Question;
import com.example.model.QuestionBank;


public class QuestionBankGenerator {

    private static final String DB_URL = "jdbc:sqlite:quiz.db";

    public static QuestionBank generateQuestionBank(String quizType) {
        String query = generateQuery(quizType);
        
        QuestionBank questions = returnQuestions(query, quizType);

        return questions;
    }

    private static QuestionBank returnQuestions(String query, String quizType) {
        QuestionBank questionBank = new QuestionBank();
    
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
    
            if ("Novice".equals(quizType) || "Intermediate".equals(quizType) || "Advanced".equals(quizType)) {
                pstmt.setString(1, quizType.toUpperCase());
            }
    
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String questionText = rs.getString("question");
                    List<String> options = List.of(rs.getString("option_a"), rs.getString("option_b"), rs.getString("option_c"));
                    String correctOption = rs.getString("correct_answer");
                    Question question = new Question(questionText, quizType, options, correctOption);
                    questionBank.addQuestion(question);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return questionBank;
    }

    private static String generateQuery(String quizType) {
        String sql = "SELECT question, option_a, option_b, option_c, correct_answer FROM Questions";
    
        switch (quizType) {
            case "Novice":
            case "Intermediate":
            case "Advanced":
                return sql + " WHERE difficulty = ?";
            case "Quickfire":
                return sql + " ORDER BY RANDOM() LIMIT 6";
            case "Learn":
                return sql;
            default:
                return null;
        }
    }
    
}
