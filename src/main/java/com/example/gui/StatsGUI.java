package com.example.gui;

import javax.swing.*;

import com.example.database.ScoreFetcher;
import com.example.model.ScoreStats;
import com.example.statistics.ScoreCalculator;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.*;


public class StatsGUI {
    private String difficulty;
    private ScoreCalculator scoreCalculator;
    private String username;

    public StatsGUI(String difficulty, ScoreCalculator scoreCalculator, String username) {
        this.difficulty = difficulty;
        this.scoreCalculator = scoreCalculator;
        this.username = username;
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        // Create and set up the window
        JFrame frame = new JFrame(difficulty + " Stats Page");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.dispose();
                new StatSelectorGUI();
            }
        });
        frame.setSize(600, 300);
        frame.setLayout(new BorderLayout());
        centerFrame(frame);

        // Create heading label
        JLabel headingLabel = new JLabel(difficulty);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headingLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Create table to display stats
        JTable table = createStatsTable(scoreCalculator, username, difficulty);

        // Add components to the frame
        frame.add(headingLabel, BorderLayout.NORTH);
        frame.add(new JScrollPane(table), BorderLayout.CENTER);

        // Display the window
        frame.setVisible(true);
    }

    private JTable createStatsTable(ScoreCalculator scoreCalculator, String username, String difficulty) {
    JTable table = new JTable(4, 3);
    ScoreStats userStats = scoreCalculator.calculateUserStats(username, difficulty);
    ScoreStats allUserStats = scoreCalculator.calculateAllUsersStats(difficulty);

    table.setValueAt(" ", 0, 0);
    table.setValueAt("User Score", 0, 1);
    table.setValueAt("Average Score", 0, 2);
    table.setValueAt("Mean", 1, 0);
    table.setValueAt(userStats.getMean() + "%", 1, 1);
    table.setValueAt(allUserStats.getMean() + "%", 1, 2);
    table.setValueAt("Median", 2, 0);
    table.setValueAt(userStats.getMedian() + "%", 2, 1);
    table.setValueAt(allUserStats.getMedian() + "%", 2, 2);
    table.setValueAt("Standard Deviation", 3, 0);
    table.setValueAt(userStats.getStandardDeviation(), 3, 1);
    table.setValueAt(allUserStats.getStandardDeviation(), 3, 2);

    return table;
}


    private void centerFrame(JFrame frame) {
        frame.setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
        ScoreFetcher scoreFetcher = new ScoreFetcher(); // Assume this class is instantiated with the correct dependencies.
        ScoreCalculator scoreCalculator = new ScoreCalculator(scoreFetcher);
        new StatsGUI("Novice", scoreCalculator, LoginGUI.getFutureUsername()); // Example usage, replace "yourUsername" with the actual username.
    });
}

}
