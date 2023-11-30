package com.example.gui;
import com.example.model.Question;
import com.example.database.QuestionDatabaseManager;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class QuestionManagementGUI {
    private JFrame frame;
    private QuestionDatabaseManager questionManager;

    public QuestionManagementGUI() {
        questionManager = new QuestionDatabaseManager();
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        frame = new JFrame("Question Management");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.dispose();
                new MainGUI();
            }
        });
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());

        // Add components
        frame.add(createInputPanel(), BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(2, 2, 2, 2);

        JTextField questionField = new JTextField(20);
        JComboBox<String> difficultyBox = new JComboBox<>(new String[]{"Novice", "Intermediate", "Advanced"});
        JTextField optionAField = new JTextField(20);
        JTextField optionBField = new JTextField(20);
        JTextField optionCField = new JTextField(20);
        JTextField correctAnswerField = new JTextField(20);

        inputPanel.add(new JLabel("Question:"), gbc);
        inputPanel.add(questionField, gbc);
        inputPanel.add(new JLabel("Difficulty:"), gbc);
        inputPanel.add(difficultyBox, gbc);
        inputPanel.add(new JLabel("Option A:"), gbc);
        inputPanel.add(optionAField, gbc);
        inputPanel.add(new JLabel("Option B:"), gbc);
        inputPanel.add(optionBField, gbc);
        inputPanel.add(new JLabel("Option C:"), gbc);
        inputPanel.add(optionCField, gbc);
        inputPanel.add(new JLabel("Correct Answer:"), gbc);
        inputPanel.add(correctAnswerField, gbc);

        JButton addButton = new JButton("Add Question");
        addButton.addActionListener(e -> {
            String questionText = questionField.getText();
            String difficulty = (String) difficultyBox.getSelectedItem();
            List<String> options = List.of(optionAField.getText(), optionBField.getText(), optionCField.getText());
            String correctAnswer = correctAnswerField.getText();

            Question question = new Question(questionText, difficulty, options, correctAnswer);
            questionManager.addQuestion(question);
            JOptionPane.showMessageDialog(frame, "Question added successfully!");
        });

        JButton removeButton = new JButton("Remove Question");
        removeButton.addActionListener(e -> {
            String questionText = JOptionPane.showInputDialog(frame, "Enter question text to remove:");
            if (questionText != null && !questionText.trim().isEmpty()) {
                questionManager.removeQuestionByQuestionText(questionText.trim());
                JOptionPane.showMessageDialog(frame, "Question removed successfully!");
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        
        frame.add(inputPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        return inputPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new QuestionManagementGUI());
    }
}
