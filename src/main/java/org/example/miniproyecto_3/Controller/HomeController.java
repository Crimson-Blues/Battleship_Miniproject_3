package org.example.miniproyecto_3.Controller;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.miniproyecto_3.View.Assets.ShipDrawer;

public class HomeController {
    @FXML
    private Label startLabel;

    @FXML
    public void initialize() {
        FadeTransition ft = new FadeTransition(Duration.seconds(1.2), startLabel);
        ft.setFromValue(1.0);
        ft.setToValue(0.3);
        ft.setCycleCount(Animation.INDEFINITE);
        ft.setAutoReverse(true);
        ft.play();

        startLabel.setOnMouseEntered(event -> {
            startLabel.setStyle("-fx-text-fill: #8A2BE2; " +
                    "-fx-effect: dropshadow(gaussian, rgba(138,43,226,0.7), 8, 0.5, 0, 0);" +
                    " -fx-font-weight: bold;"+
                    "-fx-cursor: hand;");

        });

        startLabel.setOnMouseExited(event -> {
            startLabel.setStyle("-fx-text-fill: black; " +
                    "-fx-cursor: hand;");

        });

        startLabel.setOnMouseClicked(this::handleStartClick);
    }

    private void handleStartClick(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/miniproyecto_3/GameView.fxml"));
            Scene sudokuScene = new Scene(loader.load());

            Stage stage = (Stage) startLabel.getScene().getWindow();
            stage.setScene(sudokuScene);

            GridPane enemyGridPane =  (GridPane) sudokuScene.lookup("#playerGridPane");

            ShipDrawer shipDrawer = new ShipDrawer();
            Pane smallShip = shipDrawer.drawSmallShip();
            Pane mediumShip = shipDrawer.drawMediumShip();
            Pane submarine = shipDrawer.drawSubmarine();
            Pane carrier = shipDrawer.drawCarrier();
            enemyGridPane.getChildren().addAll(carrier);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
