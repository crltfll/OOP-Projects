

package com.example.samplesocialnetinterface;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class SocialNetRunner extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SocialNetRunner.class.getResource("socialnet.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        scene.getStylesheets().add(getClass().getResource("/com/example/samplesocialnetinterface/style.css").toExternalForm());
        scene.setFill(Color.TRANSPARENT);


        stage.setTitle("Social Network LBYCPA2 Tafalla");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}