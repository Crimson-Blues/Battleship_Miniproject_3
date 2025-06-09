package org.example.miniproyecto_3.Controller;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Controller for the End Game screen.
 * Manages the display of the game result and the actions for "Play Again" and "Exit" buttons,
 * including animations and button hover effects.
 */
public class EndGameController {
    /**
     * Label used to display the result of the game (win or loss).
     */
    @FXML
    private Label resultLabel;
    /**
     * Button that allows the user to return to the home screen and start a new game.
     */
    @FXML private Button playAgainButton;
    /**
     * Button that allows the user to exit the application.
     */
    @FXML private Button exitButton;

    /**
     * Sets the result message based on whether the player won or lost.
     *
     * @param playerWon true if the player won, false otherwise
     */
    public void setResult(boolean playerWon) {
        resultLabel.setText(playerWon ? "¡You Won!" : "You Lost 😢");
    }

    /**
     * Initializes the controller.
     * Configures button actions and applies intro animation and fade effects.
     * This method is called automatically by JavaFX after the FXML elements are loaded.
     */
    @FXML
    public void initialize() {
        configurePlayAgainButton();
        configureExitButton();
        applyIntroAnimation();
        applyFadeEffect(playAgainButton);
        applyFadeEffect(exitButton);
    }

    /**
     * Configures the "Play Again" button to load the Home view when clicked.
     * Loads the HomeView.fxml and sets it as the current scene.
     */
    private void configurePlayAgainButton() {
        playAgainButton.setOnAction(e -> {
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/org/example/miniproyecto_3/HomeView.fxml"));
                Scene scene = new Scene(loader.load());
                Stage stage = (Stage) resultLabel.getScene().getWindow();
                stage.setScene(scene);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    /**
     * Configures the "Exit" button to close the application window when clicked.
     */
    private void configureExitButton() {
        exitButton.setOnAction(e -> {
            Stage stage = (Stage) resultLabel.getScene().getWindow();
            stage.close();
        });
    }

    /**
     * Applies an introductory scale and fade-in animation to the result label.
     * The animation grows the label from zero size to full size with easing,
     * and fades it from transparent to fully visible.
     */
    private void applyIntroAnimation() {
        ScaleTransition scale = new ScaleTransition(Duration.seconds(1.1), resultLabel);
        scale.setFromX(0);
        scale.setToX(1);
        scale.setFromY(0);
        scale.setToY(1);
        scale.setInterpolator(Interpolator.EASE_OUT);

        FadeTransition fade = new FadeTransition(Duration.seconds(1.1), resultLabel);
        fade.setFromValue(0);
        fade.setToValue(1);

        scale.play();
        fade.play();
    }

    /**
     * Applies a continuous fade animation to the given button,
     * making it fade between full opacity and half opacity indefinitely.
     * Also adds mouse enter/exit handlers to scale the button slightly for hover effect.
     *
     * @param btn the Button to apply the fade and hover animations to
     */
    private void applyFadeEffect(Button btn) {
        FadeTransition ft = new FadeTransition(Duration.seconds(1.5), btn);
        ft.setFromValue(1.0);
        ft.setToValue(0.5);
        ft.setCycleCount(FadeTransition.INDEFINITE);
        ft.setAutoReverse(true);
        ft.play();

        btn.addEventHandler(MouseEvent.MOUSE_ENTERED, e ->
                btn.setStyle(btn.getStyle() + "-fx-scale-x: 1.05; -fx-scale-y: 1.05;")
        );
        btn.addEventHandler(MouseEvent.MOUSE_EXITED, e ->
                btn.setStyle(btn.getStyle() + "-fx-scale-x: 1.0; -fx-scale-y: 1.0;")
        );
    }
}
