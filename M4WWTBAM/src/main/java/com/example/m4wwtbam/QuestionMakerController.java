package com.example.m4wwtbam;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class QuestionMakerController {

    @FXML
    private TextArea questionField;
    @FXML
    private TextField optionAField;
    @FXML
    private TextField optionBField;
    @FXML
    private TextField optionCField;
    @FXML
    private TextField optionDField;
    @FXML
    private TextField correctAnswerField;
    @FXML
    private Button saveButton;
    @FXML
    private Button goBackButton;

    @FXML
    private void initialize() {
        saveButton.setOnAction(e -> handleSave());
        goBackButton.setOnAction(e -> {
            try {
                goToQuiz(e);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    @FXML
    private void handleSave() {
        String question = questionField.getText().trim();
        String a = optionAField.getText().trim();
        String b = optionBField.getText().trim();
        String c = optionCField.getText().trim();
        String d = optionDField.getText().trim();
        String correct = correctAnswerField.getText().trim().toUpperCase();

        if (question.isEmpty() || a.isEmpty() || b.isEmpty() || c.isEmpty() || d.isEmpty() || correct.isEmpty()) {
            showAlert("Error", "Please fill out all fields.");
            return;
        }

        if (!correct.matches("[ABCD]")) {
            showAlert("Error", "Correct answer must be A, B, C, or D.");
            return;
        }

        List<String> answers = new ArrayList<>();
        answers.add(a);
        answers.add(b);
        answers.add(c);
        answers.add(d);

        int correctIndex = switch (correct) {
            case "A" -> 0;
            case "B" -> 1;
            case "C" -> 2;
            case "D" -> 3;
            default -> -1;
        };

        Question q = new Question(question, correctIndex, answers);
        QuestionBank.questionQueue.offer(q);

        showAlert("Success", "Question added successfully!");
        clearFields();
    }


    private void clearFields() {
        questionField.clear();
        optionAField.clear();
        optionBField.clear();
        optionCField.clear();
        optionDField.clear();
        correctAnswerField.clear();
    }


    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }


    private void goToQuiz(ActionEvent event) throws Exception {
        if (QuestionBank.questionQueue.isEmpty()) {
            showAlert("Error", "Please add at least one question before starting the quiz!");
            return;
        }
        Stage stage = (Stage) goBackButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("quizscreen.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }
}