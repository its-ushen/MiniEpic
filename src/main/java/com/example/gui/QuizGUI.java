package com.example.gui;

import com.example.database.QuestionBankGenerator;
import com.example.database.ScoreLogger;
import com.example.model.Question;
import com.example.model.QuestionBank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class QuizGUI {
    private QuestionBank questionBank;
    private String quizName;
    private int currentQuestionIndex;
    private JFrame frame;
    private JLabel questionLabel;
    private ButtonGroup optionsGroup;
    private JPanel optionsPanel;
    private int score;

    public QuizGUI(String name) {
        this.quizName = name;
        QuestionBank questionBank = QuestionBankGenerator.generateQuestionBank(quizName);
        this.questionBank = questionBank;
        this.currentQuestionIndex = 0;
        this.score = 0;
        createQuizGUI(quizName);
    }

    private void createQuizGUI(String name) {
        frame = new JFrame(name + " Quiz");
        
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.dispose();
                new TestDifficultySelectorGUI();
            }
        });

        
        frame.setSize(800, 600);

        questionLabel = new JLabel("Question will appear here");
        questionLabel.setFont(new Font("Arial", Font.BOLD, 18));
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);

        optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));

        JButton nextButton = new JButton("Next Question");
        nextButton.addActionListener(this::nextQuestionAction);

        frame.setLayout(new BorderLayout());
        frame.add(questionLabel, BorderLayout.NORTH);
        frame.add(new JScrollPane(optionsPanel), BorderLayout.CENTER);
        frame.add(nextButton, BorderLayout.SOUTH);

        centerFrame(frame);
        frame.setVisible(true);

        loadQuestion();
    }

    private void centerFrame(JFrame frame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((screenSize.width - frame.getWidth()) / 2, (screenSize.height - frame.getHeight()) / 2);
    }

    private void loadQuestion() {
        if (currentQuestionIndex < questionBank.getTotalQuestions()) {
            Question currentQuestion = questionBank.getQuestion(currentQuestionIndex);
            questionLabel.setText(currentQuestion.getQuestionText());
            List<String> options = currentQuestion.getOptions();
            optionsGroup = new ButtonGroup();
            optionsPanel.removeAll();

            for (String option : options) {
                JRadioButton optionButton = new JRadioButton(option);
                optionButton.setActionCommand(option);
                optionsGroup.add(optionButton);
                optionsPanel.add(optionButton);
            }

            frame.validate();
            frame.repaint();
        } else {
            endQuiz();
        }
    }

    private void nextQuestionAction(ActionEvent e) {
        if (optionsGroup.getSelection() != null) {
            String selectedOption = optionsGroup.getSelection().getActionCommand();
            Question currentQuestion = questionBank.getQuestion(currentQuestionIndex);
            boolean isCorrect = currentQuestion.isCorrect(selectedOption);

            if (isCorrect) {
                JOptionPane.showMessageDialog(frame, "Correct answer!", "Result", JOptionPane.INFORMATION_MESSAGE);
                score++; // Increment score if the answer is correct
            } else {
                JOptionPane.showMessageDialog(frame, "Wrong answer! The correct answer is: " + currentQuestion.getCorrectAnswer(),
                                              "Result", JOptionPane.ERROR_MESSAGE);
            }

            currentQuestionIndex++;
            loadQuestion();
        } else {
            JOptionPane.showMessageDialog(frame, "Please select an option before proceeding.", "No Selection", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void endQuiz() {
        ScoreLogger.log_score(LoginGUI.getFutureUsername(), quizName, (int) (((double) score / 6) * 100));
        questionLabel.setText("Quiz Complete!");
        optionsPanel.setVisible(false);
        // Show a message with the final score
        JOptionPane.showMessageDialog(frame, "Well done! You got " + score + "/" + questionBank.getTotalQuestions() + " questions correct.",
                                      "Quiz Complete", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        
        
        new QuizGUI("Novice");
    }
}
