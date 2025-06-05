package org.example.miniproyecto_3;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.miniproyecto_3.View.GameStage;

/**
 * Entry point for the JavaFX application.
 * <p>
 * This class extends {@link javafx.application.Application Application} and is responsible
 * for launching the main game stage.
 *
 * @see javafx.application.Application
 * @see javafx.stage.Stage
 */
public class Main extends Application {

    /**
     * Starts the JavaFX application by initializing the main {@link GameStage}.
     *
     * @param primaryStage the primary stage for this application, provided by the JavaFX runtime
     * @throws Exception if there is an error during initialization
     * @see javafx.stage.Stage
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        new GameStage();
    }

    /**
     * Main method that launches the JavaFX application.
     *
     * @param args the command-line arguments passed to the application
     * @see javafx.application.Application#launch(String...)
     */
    public static void main(String[] args) {
        launch(args);
    }
}
