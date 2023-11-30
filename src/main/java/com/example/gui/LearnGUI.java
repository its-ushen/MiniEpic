package com.example.gui;

import com.example.database.QuestionBankGenerator;
import com.example.model.Question;
import com.example.model.QuestionBank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
public class LearnGUI {
    private QuestionBank questionBank;
    private int currentQuestionIndex;
    private JFrame frame;
    private JLabel questionLabel;
    private JButton previousButton;
    private JButton nextButton;
    private JButton exitButton;
    private boolean showAnswer;

    public LearnGUI() {
        this.questionBank = QuestionBankGenerator.generateQuestionBank("Learn");
        this.currentQuestionIndex = 0;
        this.showAnswer = false;
        createLearnGUI();
    }

    private void createLearnGUI() {
        frame = new JFrame("Learn Mode");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.dispose();
                new MainGUI();
            }
        });
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        questionLabel = new JLabel();
        questionLabel.setFont(new Font("Arial", Font.BOLD, 18));
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        questionLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                toggleAnswer();
            }
        });

        JPanel navigationPanel = new JPanel();
        previousButton = new JButton("Previous");
        nextButton = new JButton("Next");
        exitButton = new JButton("Exit");

        previousButton.addActionListener(this::previousQuestionAction);
        nextButton.addActionListener(this::nextQuestionAction);
        exitButton.addActionListener(e -> {
            frame.dispose();
            new MainGUI();
        });
        

        navigationPanel.add(previousButton);
        navigationPanel.add(exitButton);
        navigationPanel.add(nextButton);

        frame.add(questionLabel, BorderLayout.CENTER);
        frame.add(navigationPanel, BorderLayout.SOUTH);

        centerFrame(frame);
        updateQuestion();
        frame.setVisible(true);
    }

    private void centerFrame(JFrame frame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((screenSize.width - frame.getWidth()) / 2, (screenSize.height - frame.getHeight()) / 2);
    }

    private void updateQuestion() {
        showAnswer = false; // Reset to show question by default
        if (currentQuestionIndex >= 0 && currentQuestionIndex < questionBank.getTotalQuestions()) {
            Question currentQuestion = questionBank.getQuestion(currentQuestionIndex);
            questionLabel.setText(formatHtml(currentQuestion.getQuestionText()));
        }
        previousButton.setEnabled(currentQuestionIndex > 0);
        nextButton.setEnabled(currentQuestionIndex < questionBank.getTotalQuestions() - 1);
    }

    private void toggleAnswer() {
        Question currentQuestion = questionBank.getQuestion(currentQuestionIndex);
        if (showAnswer) {
            questionLabel.setText(formatHtml(currentQuestion.getQuestionText()));
        } else {
            questionLabel.setText(formatHtml("Answer: " + currentQuestion.getCorrectAnswer()));
        }
        showAnswer = !showAnswer;
    }

    private String formatHtml(String text) {
        return "<html><body style='text-align: center'>" + text + "</body></html>";
    }

    private void previousQuestionAction(ActionEvent e) {
        if (currentQuestionIndex > 0) {
            currentQuestionIndex--;
            updateQuestion();
        }
    }

    private void nextQuestionAction(ActionEvent e) {
        if (currentQuestionIndex < questionBank.getTotalQuestions() - 1) {
            currentQuestionIndex++;
            updateQuestion();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LearnGUI::new);
    }
}
