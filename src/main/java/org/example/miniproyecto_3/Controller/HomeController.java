package org.example.miniproyecto_3.Controller;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.miniproyecto_3.Model.FileHandlers.PlainTextFileHandler;

import java.io.File;

/**
 * Controller class for the home screen of the Battleship game.
 * <p>
 * Handles user interaction on the home view, such as starting a new game,
 * continuing a saved one, or exiting the application. Also manages nickname input
 * and visual UI effects.
 * </p>
 */
public class HomeController {

    /**
     * Text field where the player enters their nickname.
     */
    @FXML
    private TextField nicknameField;

    /**
     * Button to start a new game.
     */
    @FXML
    private Button startButton;
    /**
     * Button to continue a previously saved game.
     */
    @FXML
    private Button continueButton;
    /**
     * Button to exit the application.
     */
    @FXML
    private Button exitButton;
    /**
     * Utility class to handle saving and reading plain text files, such as scores or nicknames.
     */
    private PlainTextFileHandler plainTextFileHandler;
    /**
     * File path for the saved game file.
     */
    private static final String SAVE_FILE = "savegame.ser";

    /**
     * Initializes the controller once the FXML elements are loaded.
     * Sets up button actions, UI effects, and loads the file handler.
     */
    @FXML
    public void initialize() {
        plainTextFileHandler = new PlainTextFileHandler();
        continueButton.setDisable(!hasSavedGame());

        applyFadeEffect(startButton);
        applyFadeEffect(continueButton);
        applyFadeEffect(exitButton);

        startButton.setOnAction(evt -> handleStart());
        continueButton.setOnAction(evt -> handleContinue());
        exitButton.setOnAction(evt -> System.exit(0));

        nicknameField.focusedProperty().addListener((obs, old, focused) -> {
            if (focused) {
                nicknameField.setStyle("-fx-background-color: #FFF8DC; -fx-border-color: #F5A623; -fx-border-width: 2;");
            } else {
                nicknameField.setStyle("");
            }
        });

    }

    /**
     * Applies a fade animation and hover effect to a button.
     *
     * @param btn the button to apply the effect to
     */
    private void applyFadeEffect(Button btn) {
        FadeTransition ft = new FadeTransition(Duration.seconds(1.2), btn);
        ft.setFromValue(1.0);
        ft.setToValue(0.5);
        ft.setCycleCount(FadeTransition.INDEFINITE);
        ft.setAutoReverse(true);
        ft.play();

        btn.addEventHandler(MouseEvent.MOUSE_ENTERED, e ->
                btn.setStyle(btn.getStyle() + "-fx-scale-x: 1.05; -fx-scale-y: 1.05;")
        );
        btn.addEventHandler(MouseEvent.MOUSE_EXITED, e ->
                btn.setStyle(btn.getStyle()+ "-fx-scale-x: 1.0; -fx-scale-y: 1.0;")
        );
    }

    /**
     * Handles the logic when the user presses the "Start" button.
     * Saves the entered nickname and transitions to the game screen.
     */
    private void handleStart() {
        String nick = nicknameField.getText().trim();
        if (nick.isEmpty()) {
            nicknameField.setPromptText("¡Ingresa tu nickname!");
            nicknameField.setStyle("-fx-font-family: 'Segoe UI';");
            return;
        }
        System.out.println("Iniciando juego");
        plainTextFileHandler.writeToFile("nickname.csv", nick + "\n" + 0);

        loadGameView(false);
    }

    /**
     * Checks whether there is a saved game file available.
     *
     * @return {@code true} if a save file exists, {@code false} otherwise
     */
    private boolean hasSavedGame() {
        return new File(SAVE_FILE).exists();
    }

    /**
     * Handles the logic when the user presses the "Continue" button.
     * Loads the game view with the previous game state.
     */
    private void handleContinue() {
        loadGameView(true);
    }

    /**
     * Loads the game scene (GameView.fxml) and passes control to the game controller.
     *
     * @param continueGame {@code true} if resuming a saved game, {@code false} if starting new
     */
    private void loadGameView(Boolean continueGame) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/org/example/miniproyecto_3/GameView.fxml")
            );
            Scene scene = new Scene(fxmlLoader.load());

            GameController gameController = fxmlLoader.getController();
            gameController.setContinueGame(continueGame);
            gameController.setUpController();

            Stage stage = (Stage) startButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}