package com.example.model;

import java.util.List;

public class Question {
    private String questionText;
    private String difficulty;
    private List<String> options;
    private String correctAnswer;

    public Question(String questionText, String difficulty, List<String> options, String correctAnswer) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.difficulty = difficulty;
    }

    // Getters and setters
    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    // Method to check if the provided answer is correct
    public boolean isCorrect(String answer) {
        return answer != null && answer.equalsIgnoreCase(correctAnswer);
    }

}
