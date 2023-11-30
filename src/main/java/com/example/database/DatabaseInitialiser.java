package com.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DatabaseInitialiser {

    private static boolean initialised = false;
    // A method to execute a given SQL file
    public static void executeSQLFile(String filePath, Connection conn) throws Exception {
        String sql = new String(Files.readAllBytes(Paths.get(filePath)));
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        }
    }

    // The main method to initialise the database
    public static void initialise() {
        String jdbcUrl = "jdbc:sqlite:quiz.db"; 
        if (initialised == true) {
            return;
        }
        // Try-with-resources block to automatically close the connection
        try (Connection conn = DriverManager.getConnection(jdbcUrl)) {

            executeSQLFile("src\\main\\resources\\database_setup.sql", conn);
            executeSQLFile("src\\main\\resources\\questions_insert.sql", conn);

            System.out.println("Database setup complete!");
            initialised = true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        initialise();
    }
}
