package com.example.m4wwtbam;

import java.util.List;

public class Question {
    private String question;
    private int correctAnswer;
    private List<String> answers;

    public Question(String question, int correctAnswer, List<String> answers) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.answers = answers;
    }

    public String getQuestion() {
        return question;
    }
    public List<String> getAnswers() {
        return answers;
    }
    public int getCorrectAnswer() {
        return correctAnswer;
    }
}
