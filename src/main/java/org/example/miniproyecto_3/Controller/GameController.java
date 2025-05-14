package org.example.miniproyecto_3.Controller;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.geometry.*;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.example.miniproyecto_3.Model.*;
import org.example.miniproyecto_3.View.Assets.ShipDrawer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GameController {
    @FXML
    private AnchorPane baseAnchorPane;
    @FXML
    private StackPane smallShipStack;
    @FXML
    private StackPane mediumShipStack;
    @FXML
    private StackPane submarineStack;
    @FXML
    private StackPane carrierStack;
    @FXML
    private GridPane userGridPane;
    @FXML
    private GridPane machineGridPane;
    @FXML
    private Button playButton;
    // Botones para guardar y visualizar la flota del oponente
    @FXML
    private Button saveButton;
    @FXML
    private Button viewButton;

    private double dragDeltax, dragDeltay;
    private Game game;
    private Board playerBoard;
    private Board machineBoard;

    // Definir constante para el tamaño de celda (tablero 10x10; cada celda de 40px)
    private static final int CELL_SIZE = 40;
    // Nombre del archivo de guardado
    private static final String SAVE_FILE = "savegame.ser";

    @FXML
    public void initialize() {
        // Si existe un archivo guardado, se carga la partida
        if (new File(SAVE_FILE).exists()){
            loadGameState();
            if (game == null) {
                game = new Game();
            }
        } else {
            game = new Game();
        }

        playerBoard = game.getPlayerBoard();
        machineBoard = game.getMachineBoard();
        drawShips();
        handleButtons();
    }

    public void handleButtons(){
        handlePlayButton();
    }

    public void handlePlayButton() {
        playButton.setOnAction(e -> {
            System.out.println("Play button pressed");
            // Configurar el tablero de la máquina (10x10)
            for (int i = 0; i < machineBoard.getSize(); i++){
                for (int j = 0; j < machineBoard.getSize(); j++){
                    final int row = i;
                    final int col = j;
                    StackPane cellPane = new StackPane();
                    // Asignar evento de click: al disparar se procesa el tiro del jugador
                    cellPane.setOnMouseClicked(ev -> {
                        try {
                            // Intenta disparar en la celda
                            Cell.CellState result = game.fire(new Coordinate(row, col), machineBoard);
                            if (result == Cell.CellState.HIT) {
                                cellPane.setStyle("-fx-background-color: orange;");
                            } else if (result == Cell.CellState.MISS) {
                                cellPane.setStyle("-fx-background-color: lightblue;");
                            }
                            // Si el disparo es MISS, cambio de turno e invoco el turno de la máquina
                            if (result == Cell.CellState.MISS) {
                                machineTurn();
                            }
                            // Autosave: Guardar automáticamente el estado del juego tras cada jugada
                            saveGame();
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                        }
                    });
                    machineGridPane.add(cellPane, i, j);
                    machineBoard.getCell(i, j).setClickChecker(cellPane);
                }
            }
            // Desactivar eventos de arrastre de los barcos del jugador una vez iniciada la partida
            for (Ship ship : playerBoard.getShips()){
                ship.getPane().setOnMousePressed(null);
                ship.getPane().setOnMouseDragged(null);
                ship.getPane().setOnMouseReleased(null);
            }
        });
    }
    //Draws the ships and places them in the stackPanes
    public void drawShips(){
        ShipDrawer shipDrawer = new ShipDrawer();

        //----Small ships size 1-------
        for (int i = 0; i < 4; i++){
            Pane smallShipPane = shipDrawer.drawSmallShip();
            Ship smallShip = new Ship(1, smallShipPane);
            smallShipStack.getChildren().add(smallShipPane);

            shipMouseMovement(smallShip, smallShipStack);
        }

        // ---------- Medianos (tamaño 2) ----------
        for (int i = 0; i < 3; i++){
            Pane mediumShipPane = shipDrawer.drawMediumShip();
            Ship mediumShip = new Ship(2, mediumShipPane);
            mediumShipStack.getChildren().add(mediumShipPane);

            shipMouseMovement(mediumShip, mediumShipStack);


        }

        // ---------- Submarinos (tamaño 3) ----------
        for (int i = 0; i < 2; i++){
            Pane submarinePane = shipDrawer.drawSubmarine();
            Ship submarine = new Ship(3, submarinePane);
            submarineStack.getChildren().add(submarinePane);

            shipMouseMovement(submarine, submarineStack);
        }

        // ---------- Portaaviones (carrier, tamaño 4) ----------
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

    // Metodo para ejecutar el turno de la máquina
    private void machineTurn() {
        if (game.isGameOver()) {
            System.out.println("Game Over! " + (game.playerWon() ? "Player wins!" : "Machine wins!"));
            return;
        }
        PauseTransition pause = new PauseTransition(Duration.seconds(1)); // Retardo simula "pensar"
        pause.setOnFinished(e -> {
            try {
                // La máquina selecciona un objetivo en el tablero del jugador
                Coordinate target = game.getMachine().selectTarget(playerBoard);
                Cell.CellState result = playerBoard.fireAt(target);
                System.out.println("Machine fired at: (" + target.getRow() + ", " + target.getCol() + ") Result: " + result);
                // Si el disparo impacta (HIT), permite seguir disparando
                if (result != Cell.CellState.MISS && !game.isGameOver()) {
                    machineTurn();
                }
                // Autosave tras la jugada de la máquina
                saveGame();
            } catch (Exception ex) {
                System.out.println("Error in machine turn: " + ex.getMessage());
            }
        });
        pause.play();
    }

    // Metodo para guardar el estado del juego en un archivo serializable
    private void saveGame() {
        try (FileOutputStream fos = new FileOutputStream(SAVE_FILE);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(game);
            System.out.println("Game saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Metodo para cargar el estado guardado del juego
    private void loadGameState() {
        try (FileInputStream fis = new FileInputStream(SAVE_FILE);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            Game loadedGame = (Game) ois.readObject();
            if (loadedGame != null) {
                game = loadedGame;
                playerBoard = game.getPlayerBoard();
                machineBoard = game.getMachineBoard();
                System.out.println("Game loaded successfully.");
                updateMachineBoardView();
                updatePlayerBoardView();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            // Si ocurre un error en la carga, asignamos un juego nuevo
            game = new Game();
            System.out.println("Error loading game. A new game is started.");
        }
    }

    // Métodos muy básicos para actualizar las vistas (puedes expandirlos según tus necesidades)
    private void updateMachineBoardView() {
        // Reinicia la vista del tablero de la máquina
        machineGridPane.getChildren().clear();
        for (int i = 0; i < machineBoard.getSize(); i++){
            for (int j = 0; j < machineBoard.getSize(); j++){
                final int row = i;
                final int col = j;
                StackPane cellPane = new StackPane();
                cellPane.setOnMouseClicked(ev -> {
                    try {
                        Cell.CellState result = game.fire(new Coordinate(row, col), machineBoard);
                        if (result == Cell.CellState.HIT) {
                            cellPane.setStyle("-fx-background-color: orange;");
                        } else if (result == Cell.CellState.MISS) {
                            cellPane.setStyle("-fx-background-color: lightblue;");
                        }
                        if (result == Cell.CellState.MISS) {
                            machineTurn();
                        }
                        saveGame();
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                });
                machineGridPane.add(cellPane, i, j);
                machineBoard.getCell(i, j).setClickChecker(cellPane);
            }
        }
    }

    private void updatePlayerBoardView() {
        // Dependiendo de tu implementación, podrías actualizar la vista del tablero del jugador aquí.
        // Por ejemplo, repoblar el GridPane con los barcos ya posicionados.
        // Esto es opcional si la vista del jugador no se vuelve a cargar.
    }

    // Metodo para visualizar el tablero de posición del oponente (mostrar la flota de la máquina)
    private void viewEnemyShips() {
        for (int i = 0; i < machineBoard.getSize(); i++){
            for (int j = 0; j < machineBoard.getSize(); j++){
                Cell cell = machineBoard.getCell(i, j);
                if (cell.getShip() != null) {
                    StackPane sp = cell.getClickChecker();
                    if (sp != null) {
                        // Agrega un estilo que muestre la posición del barco (por ejemplo, fondo verde semitransparente)
                        sp.setStyle(sp.getStyle() + "-fx-background-color: rgba(0,255,0,0.5);");
                    }
                }
            }
        }
        System.out.println("Enemy ships are now visible.");
    }
}
