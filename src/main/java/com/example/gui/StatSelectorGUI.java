package com.example.gui;

import javax.swing.*;

import com.example.database.ScoreFetcher;
import com.example.statistics.ScoreCalculator;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StatSelectorGUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    public StatSelectorGUI() {
        createAndShowGUI();
    }
    public static void createAndShowGUI(){
            
        // Create a JFrame for learn page
        JFrame frame = new JFrame("Stats Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 300);
        frame.setLayout(new BorderLayout());

        // Center the frame on the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(x, y);

        //Panel for the form
        JPanel formPanel = new JPanel();
        formPanel.setLayout(null); // Set layout to null (absolute positioning)
        formPanel.setBackground(Color.LIGHT_GRAY); //set background panel

        //Heading label
        JLabel headingLabel = new JLabel("Please select an option:");
        headingLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headingLabel.setForeground(Color.BLACK);
        headingLabel.setBounds(160, 10, 400, 30); // Set coordinates and size


        //Create 5 buttons
        JButton novice = new JButton("Novice");
        novice.setBackground(Color.RED);
        novice.setForeground(Color.WHITE);
        novice.setBounds(50, 90, 200, 50); // Set coordinates and size
        novice.addActionListener(new ActionListener() {      //add action listener to the button
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new StatsGUI("Novice", new ScoreCalculator(new ScoreFetcher()), LoginGUI.getFutureUsername());
            }
        });

        JButton intermediate = new JButton("Intermediate");
        intermediate.setBackground(Color.RED);
        intermediate.setForeground(Color.WHITE);
        intermediate.setBounds(344, 90, 200, 50);// Set coordinates and size
        intermediate.addActionListener(new ActionListener() {      //add action listener to the button
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new StatsGUI("Intermediate", new ScoreCalculator(new ScoreFetcher()), LoginGUI.getFutureUsername());
            }
        });

        JButton advanced = new JButton("Advanced");
        advanced.setBackground(Color.RED);
        advanced.setForeground(Color.WHITE);
        advanced.setBounds(50, 150, 200, 50);// Set coordinates and size
        advanced.addActionListener(new ActionListener() {      //add action listener to the button
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new StatsGUI("Advanced", new ScoreCalculator(new ScoreFetcher()), LoginGUI.getFutureUsername());
            }
        });

        JButton quickfire = new JButton("Quickfire");
        quickfire.setBackground(Color.RED);
        quickfire.setForeground(Color.WHITE);
        quickfire.setBounds(344, 150, 200, 50);// Set coordinates and size
        quickfire.addActionListener(new ActionListener() {      //add action listener to the button
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new StatsGUI("Quickfire", new ScoreCalculator(new ScoreFetcher()), LoginGUI.getFutureUsername());
            }
        });

        JButton leaderboard = new JButton("Leaderboard");
        leaderboard.setBackground(Color.RED);
        leaderboard.setForeground(Color.WHITE);
        leaderboard.setBounds(50, 210, 200, 50);// Set coordinates and size
        leaderboard.addActionListener(new ActionListener() {      //add action listener to the button
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new LeaderboardGUI();
            }
        });

        JButton back = new JButton("Back");
        back.setBackground(Color.RED);
        back.setForeground(Color.WHITE);
        back.setBounds(344, 210, 200, 50);// Set coordinates and size
        back.addActionListener(new ActionListener() {      //add action listener to the button
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new MainGUI();
            }
        });

        // Add the components to the form panel 
        formPanel.add(headingLabel);
        formPanel.add(novice);
        formPanel.add(intermediate);
        formPanel.add(advanced);
        formPanel.add(leaderboard);
        formPanel.add(back);
        formPanel.add(quickfire);

        // Add the form panel to the frame
        frame.add(formPanel, BorderLayout.CENTER);

        // Make the frame visible
        frame.setVisible(true);
    }
}
