package org.example.miniproyecto_3.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class EndGameController {
    @FXML
    private Label resultLabel;
    @FXML private Button playAgainButton;
    @FXML private Button exitButton;

    /** Llamado desde el GameController antes de mostrar la ventana */
    public void setResult(boolean playerWon) {
        resultLabel.setText(playerWon ? "¡You Won!" : "You Lost 😢");
    }

    @FXML
    public void initialize() {
        playAgainButton.setOnAction(e -> {
            try {
                // Vuelve a la pantalla de inicio
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/org/example/miniproyecto_3/HomeView.fxml"));
                Scene scene = new Scene(loader.load());
                Stage stage = (Stage) resultLabel.getScene().getWindow();
                stage.setScene(scene);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        exitButton.setOnAction(e -> {
            // Cierra la aplicación
            Stage stage = (Stage) resultLabel.getScene().getWindow();
            stage.close();
        });
    }
}
