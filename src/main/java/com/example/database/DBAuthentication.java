package com.example.database;

import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import com.example.authentication.IAuthentication;

public class DBAuthentication implements IAuthentication {

    private static final String JDBC_URL = "jdbc:sqlite:quiz.db";

    @Override
    public boolean signup(String username, String password) throws Exception {
        if (usernameExists(username)) {
            System.out.println("Username already exists. Please choose a different username.");
            return false;
        }

        byte[] salt = generateSalt();
        byte[] hash = hashPassword(password, salt);

        try (Connection conn = DriverManager.getConnection(JDBC_URL);
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO users(username, hashed_password, salt) VALUES(?,?,?)")) {
            pstmt.setString(1, username);
            pstmt.setString(2, Base64.getEncoder().encodeToString(hash));
            pstmt.setString(3, Base64.getEncoder().encodeToString(salt));
            pstmt.executeUpdate();
        }

        System.out.println("User registered successfully!");
        return true;
    }

    @Override
    public boolean check(String username, String password) throws Exception {
        try (Connection conn = DriverManager.getConnection(JDBC_URL);
             PreparedStatement pstmt = conn.prepareStatement("SELECT hashed_password, salt FROM users WHERE username = ?")) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    byte[] salt = Base64.getDecoder().decode(rs.getString("salt"));
                    byte[] hash = hashPassword(password, salt);
                    return Base64.getEncoder().encodeToString(hash).equals(rs.getString("hashed_password"));
                }
            }
        }
        return false;
    }

    private boolean usernameExists(String username) throws Exception {
        try (Connection conn = DriverManager.getConnection(JDBC_URL);
             PreparedStatement pstmt = conn.prepareStatement("SELECT username FROM users WHERE username = ?")) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    private byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    private byte[] hashPassword(String password, byte[] salt) throws Exception {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        return factory.generateSecret(spec).getEncoded();
    }
}
