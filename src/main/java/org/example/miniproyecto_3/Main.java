package org.example.miniproyecto_3;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.miniproyecto_3.View.GameStage;

public class Main extends Application {

    public void start(Stage primaryStage) throws Exception{
        new GameStage();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
