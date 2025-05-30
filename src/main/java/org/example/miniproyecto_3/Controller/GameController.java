package org.example.miniproyecto_3.Controller;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.miniproyecto_3.Model.*;
import org.example.miniproyecto_3.Model.Exceptions.IncompleteBoard;
import org.example.miniproyecto_3.Model.Exceptions.NonShootableCell;
import org.example.miniproyecto_3.Model.Exceptions.OverlappingShip;
import org.example.miniproyecto_3.Model.Exceptions.ShipOutOfBounds;
import org.example.miniproyecto_3.Model.FileHandlers.PlainTextFileHandler;
import org.example.miniproyecto_3.Model.FileHandlers.SerializableFileHandler;
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
    private GridPane playerGridPane;
    @FXML
    private GridPane machineGridPane;
    @FXML
    private Button playButton;
    @FXML
    private Button restartButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button viewButton;
    @FXML
    private Label turnLabel;
    @FXML
    private Label errorLabel;

    private double dragDeltax, dragDeltay;
    private Game game;
    private Board playerBoard;
    private Board machineBoard;
    private Boolean machineShipsVisible;
    private PlainTextFileHandler plainTextFileHandler;
    private Boolean continueGame;
    private SerializableFileHandler serializableFileHandler;
    private ArrayList<ArrayList<StackPane>> playerStackPanes;
    private ArrayList<ArrayList<StackPane>> machineStackPanes;
    private ArrayList<Pane> playerShipPanes = new ArrayList<Pane>();
    private ArrayList<Pane> machineShipPanes  = new ArrayList<Pane>();

    // Definir constante para el tamaño de celda (tablero 10x10; cada celda de 40px)
    private static final int CELL_SIZE = 40;
    // Nombre del archivo de guardado
    private static final String SAVE_FILE = "savegame.ser";

    @FXML
    public void initialize() {

    }

    public void setUpController() {
        // Si existe un archivo guardado, se carga la partida
        if(continueGame) {
            System.out.println("Loading Game");
            loadGameState();
        }else{
            System.out.println("Starting new game");
            game = new Game();
            playerBoard = game.getPlayerBoard();
            machineBoard = game.getMachineBoard();
            drawInitialShips();
            plainTextFileHandler = new PlainTextFileHandler();
            handleLabels();
        }

        handleButtons();
    }

    public void handleLabels(){
        try{
            String nick = plainTextFileHandler.readFromFile("nickname.csv")[0];
            game.getPlayer().setNickname(nick);

            turnLabel.setText("Posiciona tus barcos " + nick);
            turnLabel.setVisible(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        errorLabel.setVisible(false);
    }

    public void handleButtons(){
        handlePlayButton();
        handleViewButton();
        handleRestartButton();
        handleSaveButton();
    }

    public void handleSaveButton(){
        saveButton.setOnAction(e ->{
            saveGame();
        });
    }
    public void handleRestartButton(){
        restartButton.setOnAction(e -> {
            try {
                File savedGame = new File(SAVE_FILE);
                if (savedGame.exists()) {
                    savedGame.delete();
                }

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/miniproyecto_3/GameView.fxml"));
                Scene scene = new Scene(loader.load());

                Stage stage = (Stage) restartButton.getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException ex) {
                showError(errorLabel, "Error al reiniciar el juego: " + ex.getMessage());
            }
        });
    }
    public void handlePlayButton() {
        playButton.setOnAction(e -> {
            try{
                setPlayState();
            } catch (Exception ex) {
                showError(errorLabel, ex.getMessage());
            }

        });
    }

    public void setPlayState() throws IncompleteBoard{
        if(playerBoard.getShips().size() < 10){
            //throw new IncompleteBoard("¡Posiciona todos tus barcos antes de jugar!");
        }

        if(game.getTurn() == Game.Turn.PLAYER){
            turnLabel.setText("Turno: " + game.getPlayer().getNickname());
        }else if(game.getTurn() == Game.Turn.MACHINE){
            turnLabel.setText("Turno: Máquina");
        }

        placeShips(machineBoard, machineShipPanes, machineGridPane);
        hideEnemyShips();

        playerStackPanes = new ArrayList<>();
        machineStackPanes = new ArrayList<>();

        for (int i = 0; i < machineBoard.getSize(); i++){
            playerStackPanes.add(new ArrayList<StackPane>());
            machineStackPanes.add(new ArrayList<StackPane>());

            for (int j = 0; j < machineBoard.getSize(); j++){
                final int col = i;
                final int row = j;

                StackPane machineCellPane = new StackPane();
                StackPane playerCellPane = new StackPane();

                // Asignar evento de click: al disparar se procesa el tiro del jugador
                machineCellPane.setOnMouseClicked(ev -> {
                    try {
                        // Intenta disparar en la celda
                        shootCell(new Coordinate(row, col), machineBoard, machineStackPanes);

                        // Si el disparo es MISS, cambio de turno e invoco el turno de la máquina
                        if (game.getTurn() == Game.Turn.MACHINE) {
                            PauseTransition pause = new PauseTransition(Duration.seconds(1));
                            pause.setOnFinished(event ->{machineTurn();});
                            pause.play();

                        }
                        // Autosave: Guardar automáticamente el estado del juego tras cada jugada
                    } catch (Exception ex) {
                        showError(errorLabel, ex.getMessage());
                    }
                });
                machineGridPane.add(machineCellPane, i, j);
                machineStackPanes.get(i).add(machineCellPane);

                playerGridPane.add(playerCellPane, i, j);
                playerStackPanes.get(i).add(playerCellPane);
            }
        }
        // Desactivar eventos de arrastre de los barcos del jugador una vez iniciada la partida
        for (Pane pane : playerShipPanes){
            pane.setOnMousePressed(null);
            pane.setOnMouseDragged(null);
            pane.setOnMouseReleased(null);
        }
    }

   //Draws the ships and places them in the stackPanes
    public void drawInitialShips(){
         playerShipPanes = new ArrayList<>();
        ShipDrawer shipDrawer = new ShipDrawer();

        //----Small ships (size 1)-------
        for (int i = 0; i < 4; i++){
            Pane smallShipPane = shipDrawer.drawSmallShip();
            Ship smallShip = new Ship(1);
            smallShipStack.getChildren().add(smallShipPane);

            handleShipMovement(smallShip, smallShipStack, smallShipPane);
            playerShipPanes.add(smallShipPane);

        }

        // ---------- Medium ships (size 2) ----------
        for (int i = 0; i < 3; i++){
            Pane mediumShipPane = shipDrawer.drawMediumShip();
            Ship mediumShip = new Ship(2);
            mediumShipStack.getChildren().add(mediumShipPane);

            handleShipMovement(mediumShip, mediumShipStack, mediumShipPane);
            playerShipPanes.add(mediumShipPane);

        }

        // ---------- Submarines (size 3) ----------
        for (int i = 0; i < 2; i++){
            Pane submarinePane = shipDrawer.drawSubmarine();
            Ship submarine = new Ship(3);
            submarineStack.getChildren().add(submarinePane);

            handleShipMovement(submarine, submarineStack,submarinePane);
            playerShipPanes.add(submarinePane);
        }

        // ---------- Carriers (size 4) ----------
        Pane carrierPane = shipDrawer.drawCarrier();
        Ship carrier =  new Ship(4);
        carrierStack.getChildren().add(carrierPane);

        handleShipMovement(carrier, carrierStack, carrierPane);
        playerShipPanes.add(carrierPane);

    }

    public void handleShipMovement(Ship ship, StackPane stack, Pane pane){
        ImageView shipImageView = (ImageView) pane.getChildren().get(0);

        playerGridPane.setHalignment(pane, HPos.CENTER);
        playerGridPane.setValignment(pane, VPos.CENTER);

        double width = shipImageView.getImage().getWidth();
        double height = shipImageView.getImage().getHeight();

        double xDisplace = (height + width)/2;
        double yDisplace = (width - height)/2;

        //System.out.println("Width: " + width + " Height: " + height);

        //mediumShipPane.setStyle("-fx-border-color: red; -fx-border-width: 2;");
        pane.setOnMousePressed(e -> {

            if(e.getButton() == MouseButton.SECONDARY){
                flipShip(ship, pane);
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

            double gridX = playerGridPane.localToScene(0,0).getX();
            double gridY = playerGridPane.localToScene(0,0).getY();

            double centerX = pane.localToScene(0,0).getX();
            double centerY = pane.localToScene(0,0).getY();

            if(ship.getOrientation() == IShip.Orientation.HORIZONTAL){
                centerX = pane.localToScene(0,0).getX() + width/2;
                centerY = pane.localToScene(0,0).getY() + height/2;
            }else if(ship.getOrientation() == IShip.Orientation.VERTICAL){
                centerX = pane.localToScene(0,0).getX() - width/2;
                centerY = pane.localToScene(0,0).getY() + height/2;
            }

            Bounds gridPaneBounds = playerGridPane.localToScene(playerGridPane.getBoundsInLocal());
            Bounds shipPaneBounds = pane.localToScene(pane.getBoundsInLocal());

            Pane parent = (Pane) pane.getParent();
            parent.getChildren().remove(pane);

            try{
                if(!(gridPaneBounds.contains(shipPaneBounds.getMinX(), shipPaneBounds.getMinY()) &&
                        gridPaneBounds.contains(shipPaneBounds.getMaxX(), shipPaneBounds.getMaxY()))) {
                    throw new ShipOutOfBounds("Barco por fuera del tablero de juego");
                }

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

                playerGridPane.add(pane, cellX, cellY);

                List<Coordinate> coords = new ArrayList<Coordinate>();
                if(ship.getOrientation() == IShip.Orientation.HORIZONTAL){
                    for(int k = 0; k < ship.getLength(); k++){
                        coords.add(new Coordinate(cellY, cellX + k));
                    }
                } else if (ship.getOrientation() == IShip.Orientation.VERTICAL){
                    for(int k = 0; k < ship.getLength(); k++){
                        coords.add(new Coordinate(cellY + k, cellX));
                    }

                }

                if(!playerBoard.canPlaceShip(ship.getLength(),coords.get(0), ship.getOrientation())){
                    throw new OverlappingShip("No se puede poner un barco sobre otro");
                }

                playerBoard.placeShip(ship, coords);
                System.out.println("X: " + playerGridPane.getColumnIndex(pane) + " Y: " + playerGridPane.getRowIndex(pane));

            } catch(ShipOutOfBounds|OverlappingShip i) {

                System.out.println(i.getMessage());
                stack.getChildren().add(pane);
                playerBoard.removeShip(ship);
                showError(errorLabel, i.getMessage());
            }

        });
    }

    // Metodo para ejecutar el turno de la máquina
    private void machineTurn() {

        PauseTransition pause = new PauseTransition(Duration.seconds(1)); // Retardo simula "pensar"
        pause.setOnFinished(e -> {
            // La máquina selecciona un objetivo en el tablero del jugador
            Coordinate target = game.getMachine().selectTarget(playerBoard);
            shootCell(target, playerBoard, playerStackPanes);
            System.out.println("Machine fired at: (" + target.getCol() + ", " + target.getRow() + ")");
            // Si el disparo impacta (HIT), permite seguir disparando
            if (game.getTurn() == Game.Turn.MACHINE) {
                PauseTransition smallPause = new PauseTransition(Duration.seconds(1));
                smallPause.setOnFinished(event ->{machineTurn();});
                smallPause.play();
            }
            // Autosave tras la jugada de la máquina
        });
        pause.play();

    }

    private void shootCell(Coordinate target, Board board, ArrayList<ArrayList<StackPane>> stackPanes) {
        try{
            Cell.CellState result = game.fire(target, board);
            int x = target.getCol();
            int y = target.getRow();
            String turnMessage = (game.getTurn() == Game.Turn.PLAYER) ? "Turno: " + game.getPlayer().getNickname() : "Turno: Máquina";

            if(result == Cell.CellState.MISS){
                stackPanes.get(x).get(y).setStyle("-fx-background-color: lightblue; -fx-opacity: 0.5;");
                showTempMessage(turnLabel, "Tiro al agua en: (" + target.getCol() + ", " + target.getRow() + ")",turnMessage, 1);

            } else if (result == Cell.CellState.HIT){
                stackPanes.get(x).get(y).setStyle("-fx-background-color: orange; -fx-opacity: 0.5;");
                if (game.isGameOver()) {
                    System.out.println("Game Over! " + (game.playerWon() ? "Player wins!" : "Machine wins!"));
                    showEndScreen(game.playerWon());
                    return;
                }
                if(board.getCell(x, y).getShip().isSunk()){
                    showTempMessage(turnLabel, board.getCell(x, y).getShip().getKind()+ " hundido!",  turnMessage, 1);
                } else{
                    showTempMessage(turnLabel, "Barco golpeado en: (" + target.getCol() + ", " + target.getRow() + ")", turnMessage ,1);
                }
            }

        }catch(NonShootableCell ex) {
            showError(errorLabel, ex.getMessage());
        }

        saveGame();
    }

    // Metodo para guardar el estado del juego en un archivo serializable
    private void saveGame() {
        serializableFileHandler = new SerializableFileHandler();
        serializableFileHandler.serialize(SAVE_FILE, game);
    }

    // Metodo para cargar el estado guardado del juego
    private void loadGameState() {
        serializableFileHandler = new SerializableFileHandler();
        try{
            Game loadedGame = (Game) serializableFileHandler.deserialize(SAVE_FILE);
            if(loadedGame== null){
                System.out.println("Error loading game. Starting a new one.");
                setContinueGame(false);
                initialize();
            }
            else{
                game = loadedGame;
                playerBoard = game.getPlayerBoard();
                machineBoard = game.getMachineBoard();
                placeShips(playerBoard, playerShipPanes, playerGridPane);
                setPlayState();
                updateBoard(playerBoard, playerStackPanes);
                updateBoard(machineBoard, machineStackPanes);
                errorLabel.setVisible(false);
                System.out.println("Game loaded successfully.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Si ocurre un error en la carga, asignamos un juego nuevo
            setContinueGame(false);
            initialize();
            System.out.println("Error loading game. A new game is started.");
            showError(errorLabel, "Error loading game. A new game is started.");
        }
    }


    //UpdateMachineBoard view onto MachineGridPane
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
            }
        }
    }

    //Sets machine ships panes onto machineGridPane
    private void placeShips(Board board, ArrayList<Pane> paneList, GridPane gridPane) {
        paneList.clear();
        for(Ship ship : board.getShips()){

            int cellX = ship.getHeadCoord().getCol();
            int cellY = ship.getHeadCoord().getRow();
            Pane pane = drawShip(ship.getLength());
            paneList.add(pane);

            gridPane.setHalignment(pane, HPos.CENTER);
            gridPane.setValignment(pane, VPos.CENTER);

            if(ship.getOrientation() == IShip.Orientation.HORIZONTAL){
                pane.setRotate(0);
                gridPane.setColumnSpan(pane, ship.getLength());
                gridPane.setRowSpan(pane, 1);

            }else if (ship.getOrientation() == IShip.Orientation.VERTICAL){
                pane.setRotate(90);
                gridPane.setColumnSpan(pane, 1);
                gridPane.setRowSpan(pane, ship.getLength());
            }

            gridPane.add(pane, cellX, cellY);
            System.out.println("Boat at: (" + cellX + ", " + cellY + ")");

        }
    }

    private void updatePlayerBoardView() {

    }

    private void handleViewButton(){
        viewButton.setOnAction(e ->{
            if(machineShipsVisible){
                hideEnemyShips();
                machineShipsVisible = false;
            } else {
                viewEnemyShips();
                machineShipsVisible = true;
            }
        });
    }
    //Makes enemy ships visible
    private void viewEnemyShips() {
        machineShipsVisible = true;
        for(Pane pane: machineShipPanes){
            pane.setVisible(true);
        }
    }

    //Makes enemy ships visible
    private void hideEnemyShips() {
        machineShipsVisible = false;
        for(Pane pane: machineShipPanes){
            pane.setVisible(false);
        }
    }

    public void showError(Label label, String message) {
        label.setText(message);
        label.setOpacity(0);
        label.setVisible(true);

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), label);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        PauseTransition pause = new PauseTransition(Duration.seconds(1)); //Label stays visible for a second

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), label);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setOnFinished(e -> label.setVisible(false)); //Leave label hidden after fade out

        SequentialTransition sequence = new SequentialTransition(fadeIn, pause, fadeOut);
        sequence.play();
    }

    public void showTempMessage(Label label, String tempMessage, String defaultMessage, double seconds) {
        label.setText(tempMessage);
        PauseTransition pause = new PauseTransition(Duration.seconds(seconds));
        pause.setOnFinished(e -> label.setText(defaultMessage));
        pause.play();
    }

    public Pane drawShip(int length){
        ShipDrawer drawer = new ShipDrawer();
        switch(length){
            case 1:
                return drawer.drawSmallShip();

            case 2:
                return drawer.drawMediumShip();

            case 3:
                return drawer.drawSubmarine();

            case 4:
                return drawer.drawCarrier();

            default:
                return new Pane();

        }
    }

    public void flipShip(Ship ship, Pane pane){
        ship.flip();

        if(ship.getOrientation() == Ship.Orientation.HORIZONTAL){
            pane.setRotate(0);
        } else if (ship.getOrientation() == Ship.Orientation.VERTICAL){
            pane.setRotate(90);
        }

        pane.applyCss();
        pane.layout();

    }

    public void showEndScreen(boolean playerWon) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/miniproyecto_3/EndGameView.fxml"));
            Scene scene = new Scene(loader.load());

            EndGameController controller = loader.getController();
            controller.setResult(playerWon);

            Stage stage = (Stage) baseAnchorPane.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateBoard (Board board, ArrayList<ArrayList<StackPane>> stackPanes){
        for(int i = 0; i < board.getSize(); i++){
            for(int j = 0; j < board.getSize(); j++){
                Cell cell = board.getCell(i, j);
                StackPane cellPane = stackPanes.get(i).get(j);
                if (cell.getState() == Cell.CellState.HIT) {
                    cellPane.setStyle("-fx-background-color: orange; -fx-opacity: 0.5;");
                } else if (cell.getState() == Cell.CellState.MISS) {
                    cellPane.setStyle("-fx-background-color: lightblue; -fx-opacity: 0.5;");
                }

            }
        }
    }

    public void setContinueGame(Boolean continueGame) {
        this.continueGame = continueGame;
    }
}
