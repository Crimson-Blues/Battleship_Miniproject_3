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

public class EndGameController {
    @FXML
    private Label resultLabel;
    @FXML private Button playAgainButton;
    @FXML private Button exitButton;

    public void setResult(boolean playerWon) {
        resultLabel.setText(playerWon ? "¡You Won!" : "You Lost 😢");
    }

    @FXML
    public void initialize() {
        configurePlayAgainButton();
        configureExitButton();
        applyIntroAnimation();
        applyFadeEffect(playAgainButton);
        applyFadeEffect(exitButton);
    }

    /** Acciones del botón de volver a jugar */
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

    /** Acciones del botón de salir */
    private void configureExitButton() {
        exitButton.setOnAction(e -> {
            Stage stage = (Stage) resultLabel.getScene().getWindow();
            stage.close();
        });
    }

    /** Animación de entrada para el resultado */
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
