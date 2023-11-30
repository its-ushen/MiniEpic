package com.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.example.model.UserScore;

import java.util.ArrayList;

public class ScoreFetcher {
    private static final String DB_URL = "jdbc:sqlite:quiz.db";

    public List<UserScore> fetchUserScoresBydifficulty(String username, String difficulty) {
        return returnScores(username, difficulty);
    }

    
    public List<UserScore> fetchAllScoresBydifficulty(String difficulty) {
        return returnScores(null,difficulty);
    }
    
    public static List<UserScore> fetchUserScores(String username) {
        return returnScores(username, null);
    }

    private static String generateQuery(String username, String difficulty) {
        if (username != null && difficulty != null) {
            return "SELECT username, score FROM scores WHERE username = ? AND difficulty = ?";
        } else if (username == null && difficulty != null) {
            return "SELECT username, score FROM scores WHERE difficulty = ?";
        } else if (difficulty == null && username != null) {
            return "SELECT username, score FROM scores WHERE username = ?";
        }
        return null;
    }
    
    private static PreparedStatement createPreparedStatement(Connection conn, String query, String username, String difficulty) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(query);
        int paramIndex = 1;  // Placeholder index starts at 1
        if (username != null) {
            pstmt.setString(paramIndex++, username); // Set username parameter
        }
        if (difficulty != null) {
            pstmt.setString(paramIndex, difficulty); // Set difficulty parameter
        }
        return pstmt;
    }
    
    
    private static List<UserScore> returnScores(String username, String difficulty) {
        String query = generateQuery(username, difficulty);
        List<UserScore> userScores = new ArrayList<>();
    
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = createPreparedStatement(conn, query, username, difficulty);
             ResultSet rs = pstmt.executeQuery()) {
    
            while (rs.next()) {
                String user = username != null ? username : rs.getString("username");
                double score = rs.getDouble("score");
                userScores.add(new UserScore(user, score));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching scores: " + e.getMessage());
        }
    
        return userScores;
    }
}