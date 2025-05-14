package org.example.miniproyecto_3.Controller;

import javafx.fxml.FXML;
import javafx.geometry.*;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import org.example.miniproyecto_3.Model.*;
import org.example.miniproyecto_3.View.Assets.ShipDrawer;

import java.util.ArrayList;
import java.util.List;

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
    GridPane machineGridPane;
    @FXML
    Button playButton;
    private double dragDeltax,dragDeltay;
    private Game game;
    private Board playerBoard;
    private Board machineBoard;


    @FXML
    public void initialize(){
        game = new Game();
        playerBoard = game.getPlayerBoard();
        machineBoard = game.getMachineBoard();
        drawShips();
        handleButtons();

    }

    public void handleButtons(){
        handlePlayButton();
    }

    public void handlePlayButton(){
        playButton.setOnAction(e -> {
            System.out.println("Play button pressed");

            for(int i = 0; i < machineBoard.getSize(); i++){
                for(int j = 0; j < machineBoard.getSize(); j++){
                    StackPane stackPane = new StackPane();
                    machineGridPane.add(stackPane, i, j);
                    machineBoard.getCell(i, j).setClickChecker(stackPane);
                }
            }

            for(Ship ship : playerBoard.getShips()){
                ship.getPane().setOnMousePressed(null);
                ship.getPane().setOnMouseDragged(null);
                ship.getPane().setOnMouseReleased(null);
            }
        });
    }
    //Draws the ships and places them in the stackPanes
    public void drawShips(){
        ShipDrawer shipDrawer = new ShipDrawer();

        //Draws the small ships
        for(int i = 0; i < 4; i++){
            Pane smallShipPane = shipDrawer.drawSmallShip();
            Ship smallShip = new Ship(1, smallShipPane);
            smallShipStack.getChildren().add(smallShipPane);

            shipMouseMovement(smallShip, smallShipStack);
        }

        for(int i = 0; i < 3; i++){
            Pane mediumShipPane = shipDrawer.drawMediumShip();
            Ship mediumShip = new Ship(2, mediumShipPane);
            mediumShipStack.getChildren().add(mediumShipPane);

            shipMouseMovement(mediumShip, mediumShipStack);


        }
        for(int i = 0; i < 2; i++){
            Pane submarinePane = shipDrawer.drawSubmarine();
            Ship submarine = new Ship(3, submarinePane);
            submarineStack.getChildren().add(submarinePane);

            shipMouseMovement(submarine, submarineStack);
        }

        Pane carrierPane = shipDrawer.drawCarrier();
        Ship carrier =  new Ship(4, carrierPane);
        carrierStack.getChildren().add(carrierPane);

        shipMouseMovement(carrier, carrierStack);

    }

    public void shipMouseMovement(Ship ship, StackPane stack){
        Pane pane = ship.getPane();
        ImageView shipImageView = (ImageView) pane.getChildren().get(0);

        userGridPane.setHalignment(pane, HPos.CENTER);
        userGridPane.setValignment(pane, VPos.CENTER);

        double width = shipImageView.getImage().getWidth();
        double height = shipImageView.getImage().getHeight();

        double xDisplace = (height + width)/2;
        double yDisplace = (width - height)/2;

        System.out.println("Width: " + width + " Height: " + height);

        //mediumShipPane.setStyle("-fx-border-color: red; -fx-border-width: 2;");
        pane.setOnMousePressed(e -> {


            if(e.getButton() == MouseButton.SECONDARY){
                ship.flip();
            }

            // Convert the mouse click to the local coordinate space of the shipPane
            Point2D clickPoint = new Point2D(e.getSceneX(), e.getSceneY());
            Point2D localPoint = pane.localToScene(0,0);

            // Calculate the deltas
            dragDeltax = clickPoint.subtract(localPoint).getX();
            dragDeltay = clickPoint.subtract(localPoint).getY();

            // Remove from StackPane
            Pane parent = (Pane) pane.getParent();
            parent.getChildren().remove(pane);
            playerBoard.removeShip(ship);

            // Add to game board or placement layer
            baseAnchorPane.getChildren().add(pane);

            double desiredSceneX = e.getSceneX() - dragDeltax;
            double desiredSceneY = e.getSceneY() - dragDeltay;


            if(ship.getOrientation() == IShip.Orientation.HORIZONTAL){
                pane.setLayoutX(desiredSceneX);
                pane.setLayoutY(desiredSceneY);

            } else if (ship.getOrientation() == IShip.Orientation.VERTICAL){
                System.out.println(xDisplace + " " + yDisplace);
                pane.setLayoutX(desiredSceneX - xDisplace);
                pane.setLayoutY(desiredSceneY + yDisplace);
            }

            // Bring it to front
            pane.toFront();

        });

        pane.setOnMouseDragged(e -> {
            double desiredX = 0;
            double desiredY = 0;
            if(ship.getOrientation() == IShip.Orientation.HORIZONTAL){
                desiredX = e.getSceneX() - dragDeltax;
                desiredY = e.getSceneY() - dragDeltay;
            } else if(ship.getOrientation() == IShip.Orientation.VERTICAL){
                desiredX = e.getSceneX() - dragDeltax - xDisplace;
                desiredY = e.getSceneY() - dragDeltay + yDisplace;
            }

            pane.setLayoutX(desiredX);
            pane.setLayoutY(desiredY);

        });

        pane.setOnMouseReleased(e -> {
            int cellSize = 40;

            double gridX = userGridPane.localToScene(0,0).getX();
            double gridY = userGridPane.localToScene(0,0).getY();

            double centerX = pane.localToScene(0,0).getX() + width/2;
            double centerY = pane.localToScene(0,0).getY() + height/2;

            Bounds boundsInScene = userGridPane.localToScene(userGridPane.getBoundsInLocal());

            Pane parent = (Pane) pane.getParent();
            parent.getChildren().remove(pane);

            if(boundsInScene.contains(centerX, centerY)){

                if(ship.getOrientation() == IShip.Orientation.HORIZONTAL){
                    centerX = pane.localToScene(0,0).getX();
                    centerY = pane.localToScene(0,0).getY() + height/2;
                    GridPane.setColumnSpan(pane, ship.getLength());
                    GridPane.setRowSpan(pane, 1);

                } else if (ship.getOrientation() == IShip.Orientation.VERTICAL) {
                    centerX = pane.localToScene(0,0).getX() - height/2;
                    centerY = pane.localToScene(0,0).getY();
                    GridPane.setColumnSpan(pane, 1);
                    GridPane.setRowSpan(pane, ship.getLength());
                }

                int cellX = (int) ((centerX - gridX) / cellSize);
                int cellY = (int) ((centerY - gridY) / cellSize);

                userGridPane.add(pane, cellX, cellY);
                List<Coordinate> coords = new ArrayList<Coordinate>();
                if(ship.getOrientation() == IShip.Orientation.HORIZONTAL){
                    for(int k = 0; k < ship.getLength(); k++){
                        coords.add(new Coordinate(cellX + k, cellY));
                    }
                } else if (ship.getOrientation() == IShip.Orientation.VERTICAL){
                    for(int k = 0; k < ship.getLength(); k++){
                        coords.add(new Coordinate(cellX, cellY + k));
                    }

                }

                playerBoard.placeShip(ship, coords);

                System.out.println("X: " + userGridPane.getColumnIndex(pane) + " Y: " + userGridPane.getRowIndex(pane));
            }
            else{
                stack.getChildren().add(pane);
            }

        });
    }
}
