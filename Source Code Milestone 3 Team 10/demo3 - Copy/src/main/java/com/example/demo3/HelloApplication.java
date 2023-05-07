package com.example.demo3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setResizable(false);
        stage.setTitle("Translator");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        for (Translator t : HelloController.translatorsList) {
            t.saveMap();
        }
    }



    public static void main(String[] args) {
        launch();
    }
}