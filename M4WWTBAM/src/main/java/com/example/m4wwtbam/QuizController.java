package com.example.m4wwtbam;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.event.ActionEvent;
import java.util.List;
import com.example.m4wwtbam.QuestionBank;

public class QuizController {

    @FXML
    private TextArea questionText;

    @FXML
    private Label questionCounter;

    @FXML
    private Button option1;
    @FXML
    private Button option2;
    @FXML
    private Button option3;
    @FXML
    private Button option4;


    private Question currentQuestion;
    private int correctAnswer;

    @FXML
    public void initialize() {
       loadNextQuestion();
    }


    private void loadNextQuestion() {
        if (!QuestionBank.questionQueue.isEmpty()) {
            currentQuestion = QuestionBank.questionQueue.front();
            questionText.setText(currentQuestion.getQuestion());

            List<String> answers = currentQuestion.getAnswers();
            option1.setText(answers.get(0));
            option2.setText(answers.get(1));
            option3.setText(answers.get(2));
            option4.setText(answers.get(3));
        } else {
            questionText.setText("Quiz complete, no more questions.");
            option1.setDisable(true);
            option2.setDisable(true);
            option3.setDisable(true);
            option4.setDisable(true);
        }
    }

    @FXML
    private void handleAnswer(ActionEvent event) {
        Button clicked = (Button) event.getSource();
        int chosenIndex = -1;



        if (clicked == option1) chosenIndex = 0;
        else if (clicked == option2) chosenIndex = 1;
        else if (clicked == option3) chosenIndex = 2;
        else if (clicked == option4) chosenIndex = 3;

        if (chosenIndex == currentQuestion.getCorrectAnswer()) {
            QuestionBank.questionQueue.poll();
            correctAnswer++;
            questionCounter.setText(String.valueOf(correctAnswer));
        } else {
            Question q = QuestionBank.questionQueue.poll();
            QuestionBank.questionQueue.offer(q);
        }

        loadNextQuestion();
    }
}
