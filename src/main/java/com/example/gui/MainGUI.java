package com.example.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainGUI {

    private JFrame frame;

    public MainGUI() {
        createAndShowMainGUI();
    }

    private void createAndShowMainGUI() {
        frame = new JFrame("Flinder");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 300);
        frame.setLayout(null);
        centerFrame(frame);

        JPanel buttonPanel = createButtonPanel();
        frame.add(buttonPanel);
        frame.setVisible(true);
    }

    private void centerFrame(JFrame frame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((screenSize.width - frame.getWidth()) / 2, (screenSize.height - frame.getHeight()) / 2);
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 2, 10, 10));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBounds(50, 50, 500, 200);

        // Create buttons
        JButton testButton = new JButton("Test");
        JButton customiseButton = new JButton("Customise");
        JButton learnButton = new JButton("Learn");
        JButton statsButton = new JButton("Stats");

        // Style buttons
        styleButton(testButton, Color.RED);
        styleButton(customiseButton, Color.RED);
        styleButton(learnButton, Color.RED);
        styleButton(statsButton, Color.RED);

        // Add action listeners
        testButton.addActionListener(this::onTestButtonClicked);
        customiseButton.addActionListener(this::onCustomiseButtonClicked);
        learnButton.addActionListener(this::onLearnButtonClicked);
        statsButton.addActionListener(this::onStatsButtonClicked);

        // Add buttons to panel
        buttonPanel.add(testButton);
        buttonPanel.add(customiseButton);
        buttonPanel.add(learnButton);
        buttonPanel.add(statsButton);

        return buttonPanel;
    }

    private void styleButton(JButton button, Color backgroundColor) {
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
    }

    private void onTestButtonClicked(ActionEvent e) {
        frame.dispose();
        new TestDifficultySelectorGUI();
    }

    private void onCustomiseButtonClicked(ActionEvent e) {
        frame.dispose();
        new QuestionManagementGUI();
    }

    private void onLearnButtonClicked(ActionEvent e) {
        frame.dispose();
        new LearnGUI();
    }

    private void onStatsButtonClicked(ActionEvent e) {
        frame.dispose();
        new StatSelectorGUI();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainGUI::new);
    }
}
