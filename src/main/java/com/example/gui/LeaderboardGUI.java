package com.example.gui;

import javax.swing.*;

import com.example.database.UserFetcher;
import com.example.statistics.Leaderboard;

import java.awt.*;

public class LeaderboardGUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    public LeaderboardGUI() {
        createAndShowGUI();
    }
    public static void createAndShowGUI(){
                
        // Create a JFrame for learn page
        JFrame frame = new JFrame("Stats Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
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
        JLabel headingLabel = new JLabel("Leaderboard");
        headingLabel.setFont(new Font("Gotham Rounded", Font.BOLD, 24));
        headingLabel.setForeground(Color.BLACK);
        headingLabel.setBounds(220, 10, 400, 30); // Set coordinates and size
        
        //Add Table to display stats
        JTable table = new JTable(UserFetcher.return_users().size(),3);
        table.setBounds(100, 100, 400, 200);
        table.setValueAt("1st", 0, 0);
        
        for (int i = 0; i < UserFetcher.return_users().size(); i++) {

            table.setValueAt(i+1, i, 0);
            table.setValueAt(Leaderboard.createLeaderboard().get(i).getUsername(), i, 1);
            table.setValueAt(Leaderboard.createLeaderboard().get(i).getScore(), i, 2);
        }
        
        //Add scroll pane to table
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(100, 100, 400, 200);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        //Add back button
        JButton backButton = new JButton("Back");
        backButton.setBounds(250, 320, 100, 30);
        backButton.setBackground(Color.RED);
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> {
            frame.dispose();
            new StatSelectorGUI();
        });

        formPanel.add(headingLabel);
        formPanel.add(scrollPane);
        formPanel.add(backButton);

        frame.add(formPanel);

        frame.setVisible(true);
    }
}
