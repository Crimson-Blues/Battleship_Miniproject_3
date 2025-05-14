package org.example.miniproyecto_3.Controller;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.example.miniproyecto_3.Model.Board;
import org.example.miniproyecto_3.Model.Cell;
import org.example.miniproyecto_3.Model.Coordinate;
import org.example.miniproyecto_3.Model.Game;
import org.example.miniproyecto_3.Model.Ship;
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

        // Configurar botón Save para guardar el estado del juego manualmente
        if(saveButton != null) {
            saveButton.setOnAction(e -> {
                saveGame();
            });
        }
        // Configurar botón viewShips para visualizar la flota del oponente
        if(viewButton != null) {
            viewButton.setOnAction(e -> {
                viewEnemyShips();
            });
        }
    }

    public void handleButtons() {
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

    public void drawShips() {
        ShipDrawer shipDrawer = new ShipDrawer();

        // ---------- Pequeños (fragatas, tamaño 1) ----------
        for (int i = 0; i < 4; i++){
            Pane smallShipPane = shipDrawer.drawSmallShip();
            Ship smallShip = new Ship(1, smallShipPane);
            smallShipPane.setStyle("-fx-border-color: red; -fx-border-width: 2;");
            smallShipPane.setOnMousePressed(e -> {
                dragDeltax = e.getSceneX() - smallShipPane.localToScene(0, 0).getX();
                dragDeltay = e.getSceneY() - smallShipPane.localToScene(0, 0).getY();
                Pane parent = (Pane) smallShipPane.getParent();
                parent.getChildren().remove(smallShipPane);
                playerBoard.removeShip(smallShip);
                baseAnchorPane.getChildren().add(smallShipPane);
                smallShipPane.setLayoutX(e.getSceneX() - dragDeltax);
                smallShipPane.setLayoutY(e.getSceneY() - dragDeltay);
                smallShipPane.toFront();
            });
            smallShipPane.setOnMouseDragged(e -> {
                smallShipPane.setLayoutX(e.getSceneX() - dragDeltax);
                smallShipPane.setLayoutY(e.getSceneY() - dragDeltay);
            });
            smallShipPane.setOnMouseReleased(e -> {
                double width = smallShipPane.getWidth();
                double height = smallShipPane.getHeight();
                double centerX = smallShipPane.localToScene(0, 0).getX() + width / 2;
                double centerY = smallShipPane.localToScene(0, 0).getY() + height / 2;
                double gridX = userGridPane.localToScene(0, 0).getX();
                double gridY = userGridPane.localToScene(0, 0).getY();
                Bounds boundsInScene = userGridPane.localToScene(userGridPane.getBoundsInLocal());
                Pane parent = (Pane) smallShipPane.getParent();
                parent.getChildren().remove(smallShipPane);
                if (boundsInScene.contains(centerX, centerY)) {
                    int cellX = (int) ((centerX - gridX) / CELL_SIZE);
                    int cellY = (int) ((centerY - gridY) / CELL_SIZE);
                    userGridPane.add(smallShipPane, cellX, cellY);
                    List<Coordinate> coords = new ArrayList<>();
                    coords.add(new Coordinate(cellX, cellY));
                    playerBoard.placeShip(smallShip, coords);
                    System.out.println("Small ship placed at: (" + cellX + "," + cellY + ")");
                } else {
                    smallShipStack.getChildren().add(smallShipPane);
                }
            });
            smallShipStack.getChildren().add(smallShipPane);
        }

        // ---------- Medianos (tamaño 2) ----------
        for (int i = 0; i < 3; i++){
            Pane mediumShipPane = shipDrawer.drawMediumShip();
            Ship mediumShip = new Ship(2, mediumShipPane);
            mediumShipPane.setStyle("-fx-border-color: red; -fx-border-width: 2;");
            mediumShipPane.setOnMousePressed(e -> {
                dragDeltax = e.getSceneX() - mediumShipPane.localToScene(0, 0).getX();
                dragDeltay = e.getSceneY() - mediumShipPane.localToScene(0, 0).getY();
                Pane parent = (Pane) mediumShipPane.getParent();
                parent.getChildren().remove(mediumShipPane);
                playerBoard.removeShip(mediumShip);
                baseAnchorPane.getChildren().add(mediumShipPane);
                mediumShipPane.setLayoutX(e.getSceneX() - dragDeltax);
                mediumShipPane.setLayoutY(e.getSceneY() - dragDeltay);
                mediumShipPane.toFront();
            });
            mediumShipPane.setOnMouseDragged(e -> {
                mediumShipPane.setLayoutX(e.getSceneX() - dragDeltax);
                mediumShipPane.setLayoutY(e.getSceneY() - dragDeltay);
            });
            mediumShipPane.setOnMouseReleased(e -> {
                double width = mediumShipPane.getWidth();
                double height = mediumShipPane.getHeight();
                double centerX = mediumShipPane.localToScene(0, 0).getX() + width / 2;
                double centerY = mediumShipPane.localToScene(0, 0).getY() + height / 2;
                double gridX = userGridPane.localToScene(0, 0).getX();
                double gridY = userGridPane.localToScene(0, 0).getY();
                Bounds boundsInScene = userGridPane.localToScene(userGridPane.getBoundsInLocal());
                Pane parent = (Pane) mediumShipPane.getParent();
                parent.getChildren().remove(mediumShipPane);
                if (boundsInScene.contains(centerX, centerY)) {
                    int cellX = (int) ((centerX - gridX) / CELL_SIZE);
                    int cellY = (int) ((centerY - gridY) / CELL_SIZE);
                    GridPane.setColumnSpan(mediumShipPane, 2);
                    GridPane.setRowSpan(mediumShipPane, 1);
                    userGridPane.add(mediumShipPane, cellX, cellY);
                    List<Coordinate> coords = new ArrayList<>();
                    for (int j = 0; j < mediumShip.getLength(); j++){
                        coords.add(new Coordinate(cellX + j, cellY));
                    }
                    playerBoard.placeShip(mediumShip, coords);
                    System.out.println("Medium ship placed at: (" + cellX + "," + cellY + ")");
                } else {
                    mediumShipStack.getChildren().add(mediumShipPane);
                }
            });
            mediumShipStack.getChildren().add(mediumShipPane);
        }

        // ---------- Submarinos (tamaño 3) ----------
        for (int i = 0; i < 2; i++){
            Pane submarinePane = shipDrawer.drawSubmarine();
            Ship submarine = new Ship(3, submarinePane);
            submarinePane.setStyle("-fx-border-color: red; -fx-border-width: 2;");
            submarinePane.setOnMousePressed(e -> {
                dragDeltax = e.getSceneX() - submarinePane.localToScene(0, 0).getX();
                dragDeltay = e.getSceneY() - submarinePane.localToScene(0, 0).getY();
                Pane parent = (Pane) submarinePane.getParent();
                parent.getChildren().remove(submarinePane);
                playerBoard.removeShip(submarine);
                baseAnchorPane.getChildren().add(submarinePane);
                submarinePane.setLayoutX(e.getSceneX() - dragDeltax);
                submarinePane.setLayoutY(e.getSceneY() - dragDeltay);
                submarinePane.toFront();
            });
            submarinePane.setOnMouseDragged(e -> {
                submarinePane.setLayoutX(e.getSceneX() - dragDeltax);
                submarinePane.setLayoutY(e.getSceneY() - dragDeltay);
            });
            submarinePane.setOnMouseReleased(e -> {
                double width = submarinePane.getWidth();
                double height = submarinePane.getHeight();
                double centerX = submarinePane.localToScene(0, 0).getX() + width / 2;
                double centerY = submarinePane.localToScene(0, 0).getY() + height / 2;
                double gridX = userGridPane.localToScene(0, 0).getX();
                double gridY = userGridPane.localToScene(0, 0).getY();
                Bounds boundsInScene = userGridPane.localToScene(userGridPane.getBoundsInLocal());
                Pane parent = (Pane) submarinePane.getParent();
                parent.getChildren().remove(submarinePane);
                if (boundsInScene.contains(centerX, centerY)) {
                    int cellX = (int) ((centerX - gridX) / CELL_SIZE);
                    int cellY = (int) ((centerY - gridY) / CELL_SIZE);
                    GridPane.setColumnSpan(submarinePane, 3);
                    GridPane.setRowSpan(submarinePane, 1);
                    userGridPane.add(submarinePane, cellX, cellY);
                    List<Coordinate> coords = new ArrayList<>();
                    for (int j = 0; j < submarine.getLength(); j++){
                        coords.add(new Coordinate(cellX + j, cellY));
                    }
                    playerBoard.placeShip(submarine, coords);
                    System.out.println("Submarine placed at: (" + cellX + "," + cellY + ")");
                } else {
                    submarineStack.getChildren().add(submarinePane);
                }
            });
            submarineStack.getChildren().add(submarinePane);
        }

        // ---------- Portaaviones (carrier, tamaño 4) ----------
        Pane carrierPane = shipDrawer.drawCarrier();
        Ship carrier = new Ship(4, carrierPane);
        carrierPane.setStyle("-fx-border-color: red; -fx-border-width: 2;");
        carrierPane.setOnMousePressed(e -> {
            dragDeltax = e.getSceneX() - carrierPane.localToScene(0, 0).getX();
            dragDeltay = e.getSceneY() - carrierPane.localToScene(0, 0).getY();
            Pane parent = (Pane) carrierPane.getParent();
            parent.getChildren().remove(carrierPane);
            playerBoard.removeShip(carrier);
            baseAnchorPane.getChildren().add(carrierPane);
            carrierPane.setLayoutX(e.getSceneX() - dragDeltax);
            carrierPane.setLayoutY(e.getSceneY() - dragDeltay);
            carrierPane.toFront();
        });
        carrierPane.setOnMouseDragged(e -> {
            carrierPane.setLayoutX(e.getSceneX() - dragDeltax);
            carrierPane.setLayoutY(e.getSceneY() - dragDeltay);
        });
        carrierPane.setOnMouseReleased(e -> {
            double width = carrierPane.getWidth();
            double height = carrierPane.getHeight();
            double centerX = carrierPane.localToScene(0, 0).getX() + width / 2;
            double centerY = carrierPane.localToScene(0, 0).getY() + height / 2;
            double gridX = userGridPane.localToScene(0, 0).getX();
            double gridY = userGridPane.localToScene(0, 0).getY();
            Bounds boundsInScene = userGridPane.localToScene(userGridPane.getBoundsInLocal());
            Pane parent = (Pane) carrierPane.getParent();
            parent.getChildren().remove(carrierPane);
            if (boundsInScene.contains(centerX, centerY)) {
                int cellX = (int) ((centerX - gridX) / CELL_SIZE);
                int cellY = (int) ((centerY - gridY) / CELL_SIZE);
                GridPane.setColumnSpan(carrierPane, 4);
                GridPane.setRowSpan(carrierPane, 1);
                userGridPane.add(carrierPane, cellX, cellY);
                List<Coordinate> coords = new ArrayList<>();
                for (int j = 0; j < carrier.getLength(); j++){
                    coords.add(new Coordinate(cellX + j, cellY));
                }
                playerBoard.placeShip(carrier, coords);
                System.out.println("Carrier placed at: (" + cellX + "," + cellY + ")");
            } else {
                carrierStack.getChildren().add(carrierPane);
            }
        });
        carrierStack.getChildren().add(carrierPane);
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
