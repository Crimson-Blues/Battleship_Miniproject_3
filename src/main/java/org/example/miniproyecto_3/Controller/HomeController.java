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

import java.io.File;

public class HomeController {
    @FXML
    private TextField nicknameField;
    @FXML
    private Button startButton;
    @FXML
    private Button continueButton;
    @FXML
    private Button exitButton;

    @FXML
    public void initialize() {
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

    private void handleStart() {
        String nick = nicknameField.getText().trim();
        if (nick.isEmpty()) {
            nicknameField.setPromptText("¡Ingresa tu nickname!");
            nicknameField.setStyle("-fx-font-family: 'Segoe UI';");
            return;
        }
        // falta guardar nickname en archivo plano
        loadGameView(false);
    }

    private boolean hasSavedGame() {
        // Falta la lógica para verificar si existe un .ser de partida guardada
        // Por ejemplo, new File("savegame.ser").exists(). Esto para el boton continuar
        File saved = new File("savegame.ser");
        return saved.exists();
        //return false;
    }

    private void handleContinue() {
        // falta cargar estado de juego serializado
        loadGameView(true);
    }

    private void loadGameView(boolean isContinue) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/org/example/miniproyecto_3/GameView.fxml")
            );
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) startButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}