package com.example.m4wwtbam;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class QuizRunner extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("questionmaker.fxml"));
        Scene scene = new Scene(loader.load(), 800, 600);

        stage.setTitle("WWTBAM Activity - LBYCPA2");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}