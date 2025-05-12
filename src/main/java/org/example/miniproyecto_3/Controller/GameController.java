package org.example.miniproyecto_3.Controller;

import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import org.example.miniproyecto_3.View.Assets.ShipDrawer;

public class GameController {
    @FXML
    AnchorPane baseAnchorPane;
    @FXML
    StackPane smallShipStack;
    @FXML
    StackPane mediumShipStack;
    @FXML
    StackPane submarineStack;
    @FXML
    StackPane carrierStack;
    @FXML
    GridPane userGridPane;
    @FXML
    GridPane computerGridPane;
    private double dragDeltax,dragDeltay;


    @FXML
    public void initialize(){
        drawShips();
    }

    //Draws the ships and places them in the stackPanes
    public void drawShips(){
        ShipDrawer shipDrawer = new ShipDrawer();

        //Draws the small ships
        for(int i = 0; i < 4; i++){
            Pane smallShipPane = shipDrawer.drawSmallShip();
            smallShipPane.setStyle("-fx-border-color: red; -fx-border-width: 2;");
            smallShipPane.setOnMousePressed(e -> {

                //Calculate the position deltas
                dragDeltax = e.getSceneX() - smallShipPane.localToScene(0, 0).getX();
                dragDeltay = e.getSceneY() - smallShipPane.localToScene(0, 0).getY();

                // Remove from StackPane
                Pane parent = (Pane) smallShipPane.getParent();
                parent.getChildren().remove(smallShipPane);

                // Add to game board or placement layer
                baseAnchorPane.getChildren().add(smallShipPane);

                smallShipPane.setLayoutX(e.getSceneX() - dragDeltax);
                smallShipPane.setLayoutY(e.getSceneY() - dragDeltay);

                // Bring it to front
                smallShipPane.toFront();

            });

            smallShipPane.setOnMouseDragged(e -> {
                smallShipPane.setLayoutX(e.getSceneX() - dragDeltax);
                smallShipPane.setLayoutY(e.getSceneY() - dragDeltay);
            });

            smallShipPane.setOnMouseReleased(e -> {
                int cellSize = 40;
                double width = smallShipPane.getWidth();
                double height = smallShipPane.getHeight();

                double centerX = smallShipPane.localToScene(0,0).getX() + width/2;
                double centerY = smallShipPane.localToScene(0,0).getY() + height/2;

                double gridX = userGridPane.localToScene(0,0).getX();
                double gridY = userGridPane.localToScene(0,0).getY();

                Bounds boundsInScene = userGridPane.localToScene(userGridPane.getBoundsInLocal());

                Pane parent = (Pane) smallShipPane.getParent();
                parent.getChildren().remove(smallShipPane);

                if(boundsInScene.contains(centerX, centerY)){
                    int cellX = (int) ((centerX - gridX) / cellSize);
                    int cellY = (int) ((centerY - gridY) / cellSize);
                    userGridPane.add(smallShipPane, cellX, cellY);

                    System.out.println("X: " + userGridPane.getColumnIndex(smallShipPane) + " Y: " + userGridPane.getRowIndex(smallShipPane));

                    /*
                    int cellSize = 40; // adjust based on your GridPane cell size
                    double snappedX = Math.round(centerX / cellSize) * cellSize;
                    double snappedY = Math.round(centerY / cellSize) * cellSize;

                    smallShipStack.setLayoutX(snappedX);
                    smallShipPane.setLayoutY(snappedY);

                     */
                }
                else{
                    smallShipStack.getChildren().add(smallShipPane);
                }

            });
            smallShipStack.getChildren().add(smallShipPane);
        }

        for(int i = 0; i < 3; i++){
            Pane mediumShipPane = shipDrawer.drawMediumShip();
            mediumShipPane.setStyle("-fx-border-color: red; -fx-border-width: 2;");
            mediumShipPane.setOnMousePressed(e -> {

                //Calculate the position deltas
                dragDeltax = e.getSceneX() - mediumShipPane.localToScene(0, 0).getX();
                dragDeltay = e.getSceneY() - mediumShipPane.localToScene(0, 0).getY();

                // Remove from StackPane
                Pane parent = (Pane) mediumShipPane.getParent();
                parent.getChildren().remove(mediumShipPane);

                // Add to game board or placement layer
                baseAnchorPane.getChildren().add(mediumShipPane);

                mediumShipPane.setLayoutX(e.getSceneX() - dragDeltax);
                mediumShipPane.setLayoutY(e.getSceneY() - dragDeltay);

                // Bring it to front
                mediumShipPane.toFront();

            });

            mediumShipPane.setOnMouseDragged(e -> {
                mediumShipPane.setLayoutX(e.getSceneX() - dragDeltax);
                mediumShipPane.setLayoutY(e.getSceneY() - dragDeltay);
            });

            mediumShipPane.setOnMouseReleased(e -> {
                int cellSize = 40;
                double width = mediumShipPane.getWidth();
                double height = mediumShipPane.getHeight();

                double centerX = mediumShipPane.localToScene(0,0).getX();
                double centerY = mediumShipPane.localToScene(0,0).getY() + height/2;

                double gridX = userGridPane.localToScene(0,0).getX();
                double gridY = userGridPane.localToScene(0,0).getY();

                Bounds boundsInScene = userGridPane.localToScene(userGridPane.getBoundsInLocal());

                Pane parent = (Pane) mediumShipPane.getParent();
                parent.getChildren().remove(mediumShipPane);

                if(boundsInScene.contains(centerX, centerY)){
                    int cellX = (int) ((centerX - gridX) / cellSize);
                    int cellY = (int) ((centerY - gridY) / cellSize);
                    GridPane.setColumnSpan(mediumShipPane, 2);
                    GridPane.setRowSpan(mediumShipPane, 1);
                    userGridPane.add(mediumShipPane, cellX, cellY);

                    System.out.println("X: " + userGridPane.getColumnIndex(mediumShipPane) + " Y: " + userGridPane.getRowIndex(mediumShipPane));

                }
                else{
                    mediumShipStack.getChildren().add(mediumShipPane);
                }

            });
            mediumShipStack.getChildren().add(mediumShipPane);
        }
        for(int i = 0; i < 2; i++){
            Pane submarinePane = shipDrawer.drawSubmarine();
            submarinePane.setStyle("-fx-border-color: red; -fx-border-width: 2;");
            submarinePane.setOnMousePressed(e -> {

                //Calculate the position deltas
                dragDeltax = e.getSceneX() - submarinePane.localToScene(0, 0).getX();
                dragDeltay = e.getSceneY() - submarinePane.localToScene(0, 0).getY();

                // Remove from StackPane
                Pane parent = (Pane) submarinePane.getParent();
                parent.getChildren().remove(submarinePane);

                // Add to game board or placement layer
                baseAnchorPane.getChildren().add(submarinePane);

                submarinePane.setLayoutX(e.getSceneX() - dragDeltax);
                submarinePane.setLayoutY(e.getSceneY() - dragDeltay);

                // Bring it to front
                submarinePane.toFront();

            });

            submarinePane.setOnMouseDragged(e -> {
                submarinePane.setLayoutX(e.getSceneX() - dragDeltax);
                submarinePane.setLayoutY(e.getSceneY() - dragDeltay);
            });

            submarinePane.setOnMouseReleased(e -> {
                int cellSize = 40;
                double width = submarinePane.getWidth();
                double height = submarinePane.getHeight();

                double centerX = submarinePane.localToScene(0,0).getX();
                double centerY = submarinePane.localToScene(0,0).getY() + height/2;

                double gridX = userGridPane.localToScene(0,0).getX();
                double gridY = userGridPane.localToScene(0,0).getY();

                Bounds boundsInScene = userGridPane.localToScene(userGridPane.getBoundsInLocal());

                Pane parent = (Pane) submarinePane.getParent();
                parent.getChildren().remove(submarinePane);

                if(boundsInScene.contains(centerX, centerY)){
                    int cellX = (int) ((centerX - gridX) / cellSize);
                    int cellY = (int) ((centerY - gridY) / cellSize);
                    GridPane.setColumnSpan(submarinePane, 3);
                    GridPane.setRowSpan(submarinePane, 1);
                    userGridPane.add(submarinePane, cellX, cellY);

                    System.out.println("X: " + userGridPane.getColumnIndex(submarinePane) + " Y: " + userGridPane.getRowIndex(submarinePane));

                    /*
                    int cellSize = 40; // adjust based on your GridPane cell size
                    double snappedX = Math.round(centerX / cellSize) * cellSize;
                    double snappedY = Math.round(centerY / cellSize) * cellSize;

                    smallShipStack.setLayoutX(snappedX);
                    smallShipPane.setLayoutY(snappedY);

                     */
                }
                else{
                    submarineStack.getChildren().add(submarinePane);
                }

            });
            submarineStack.getChildren().add(submarinePane);
        }

        Pane carrierPane = shipDrawer.drawCarrier();
        carrierPane.setStyle("-fx-border-color: red; -fx-border-width: 2;");
        carrierPane.setOnMousePressed(e -> {

            //Calculate the position deltas
            dragDeltax = e.getSceneX() - carrierPane.localToScene(0, 0).getX();
            dragDeltay = e.getSceneY() - carrierPane.localToScene(0, 0).getY();

            // Remove from StackPane
            Pane parent = (Pane) carrierPane.getParent();
            parent.getChildren().remove(carrierPane);

            // Add to game board or placement layer
            baseAnchorPane.getChildren().add(carrierPane);

            carrierPane.setLayoutX(e.getSceneX() - dragDeltax);
            carrierPane.setLayoutY(e.getSceneY() - dragDeltay);

            // Bring it to front
            carrierPane.toFront();

        });

        carrierPane.setOnMouseDragged(e -> {
            carrierPane.setLayoutX(e.getSceneX() - dragDeltax);
            carrierPane.setLayoutY(e.getSceneY() - dragDeltay);
        });

        carrierPane.setOnMouseReleased(e -> {
            int cellSize = 40;
            double width = carrierPane.getWidth();
            double height = carrierPane.getHeight();

            double centerX = carrierPane.localToScene(0,0).getX();
            double centerY = carrierPane.localToScene(0,0).getY() + height/2;

            double gridX = userGridPane.localToScene(0,0).getX();
            double gridY = userGridPane.localToScene(0,0).getY();

            Bounds boundsInScene = userGridPane.localToScene(userGridPane.getBoundsInLocal());

            Pane parent = (Pane) carrierPane.getParent();
            parent.getChildren().remove(carrierPane);

            if(boundsInScene.contains(centerX, centerY)){
                int cellX = (int) ((centerX - gridX) / cellSize);
                int cellY = (int) ((centerY - gridY) / cellSize);
                GridPane.setColumnSpan(carrierPane, 4);
                GridPane.setRowSpan(carrierPane, 1);
                userGridPane.add(carrierPane, cellX, cellY);

                System.out.println("X: " + userGridPane.getColumnIndex(carrierPane) + " Y: " + userGridPane.getRowIndex(carrierPane));

                    /*
                    int cellSize = 40; // adjust based on your GridPane cell size
                    double snappedX = Math.round(centerX / cellSize) * cellSize;
                    double snappedY = Math.round(centerY / cellSize) * cellSize;

                    smallShipStack.setLayoutX(snappedX);
                    smallShipPane.setLayoutY(snappedY);

                     */
            }
            else{
                submarineStack.getChildren().add(carrierPane);
            }

        });
        carrierStack.getChildren().add(carrierPane);

    }
}
