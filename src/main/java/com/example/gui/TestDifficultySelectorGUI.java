package com.example.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TestDifficultySelectorGUI {

    private JFrame frame;

    public TestDifficultySelectorGUI() {
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        frame = new JFrame("Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 300);
        frame.setLayout(new BorderLayout());
        centerFrame(frame);

        // Title label
        JLabel titleLabel = new JLabel("Test", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(0, 20, 600, 30);
        frame.add(titleLabel);

        // Subtitle label
        JLabel subtitleLabel = new JLabel("Please select a difficulty:", SwingConstants.CENTER);
        subtitleLabel.setBounds(0, 60, 600, 10);
        frame.add(subtitleLabel);

        JPanel buttonPanel = createButtonPanel();
        frame.add(buttonPanel, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        styleButton(backButton, Color.BLUE);
        backButton.addActionListener(this::onBackButtonClicked);
        frame.add(backButton, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void centerFrame(JFrame frame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((screenSize.width - frame.getWidth()) / 2, (screenSize.height - frame.getHeight()) / 2);
    }

    private JPanel createButtonPanel() {
        // Creating a panel with GridBagLayout for more control over component placement
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();

        // Common settings for buttons
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;
        gbc.insets = new Insets(10, 10, 10, 10);  // Margin between buttons

        // Create buttons
        JButton noviceButton = new JButton("Novice");
        JButton intermediateButton = new JButton("Intermediate");
        JButton advancedButton = new JButton("Advanced");
        JButton quickfireButton = new JButton("Quickfire");

        // Style buttons
        styleButton(noviceButton, Color.RED);
        styleButton(intermediateButton, Color.RED);
        styleButton(advancedButton, Color.RED);
        styleButton(quickfireButton, Color.RED);

        // Position the buttons
        gbc.gridx = 0; // Column 0
        gbc.gridy = 0; // Row 0
        buttonPanel.add(noviceButton, gbc);

        gbc.gridx = 1; // Column 1
        gbc.gridy = 0; // Row 0
        buttonPanel.add(intermediateButton, gbc);

        gbc.gridx = 0; // Column 0
        gbc.gridy = 1; // Row 1
        buttonPanel.add(advancedButton, gbc);

        gbc.gridx = 1; // Column 1
        gbc.gridy = 1; // Row 1
        buttonPanel.add(quickfireButton, gbc);

        // Add action listeners
        noviceButton.addActionListener(e -> onDifficultyButtonClicked("Novice"));
        intermediateButton.addActionListener(e -> onDifficultyButtonClicked("Intermediate"));
        advancedButton.addActionListener(e -> onDifficultyButtonClicked("Advanced"));
        quickfireButton.addActionListener(e -> onDifficultyButtonClicked("Quickfire"));

        return buttonPanel;
    }

    private void styleButton(JButton button, Color backgroundColor) {
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
    }

    private void onDifficultyButtonClicked(String difficulty) {
        // This method will be called when a difficulty button is clicked
        // For example, create a new QuizGUI passing the difficulty
        new QuizGUI(difficulty);
        frame.dispose(); // Close the difficulty selection window
    }

    private void onBackButtonClicked(ActionEvent e) {
        // Logic to handle Back button click, for example, show the main menu
        frame.dispose(); // or create and show the main menu GUI
        new MainGUI(); // Assuming MainGUI is the class for main menu
    }

    // Main method for testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(TestDifficultySelectorGUI::new);
    }
}
