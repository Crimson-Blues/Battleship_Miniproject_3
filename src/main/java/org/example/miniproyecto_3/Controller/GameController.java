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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.miniproyecto_3.Model.*;
import org.example.miniproyecto_3.Model.Exceptions.IncompleteBoard;
import org.example.miniproyecto_3.Model.Exceptions.NonShootableCell;
import org.example.miniproyecto_3.Model.Exceptions.OverlappingShip;
import org.example.miniproyecto_3.Model.Exceptions.ShipOutOfBounds;
import org.example.miniproyecto_3.Model.FileHandlers.PlainTextFileHandler;
import org.example.miniproyecto_3.Model.FileHandlers.SerializableFileHandler;
import org.example.miniproyecto_3.View.Assets.ShapeDrawer;
import org.example.miniproyecto_3.View.Assets.ShipDrawer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Controller class for the main game screen in the Battleship game.
 * <p>
 * Manages user interaction, game logic integration, grid rendering,
 * drag-and-drop ship placement, and turn-based gameplay.
 * </p>
 */
public class GameController {
    /** Root layout container for the game screen. */
    @FXML
    private AnchorPane baseAnchorPane;
    /** StackPane for the small ship in the player's inventory. */
    @FXML
    private StackPane smallShipStack;
    /** StackPane for the medium ship in the player's inventory. */
    @FXML
    private StackPane mediumShipStack;
    /** StackPane for the submarine in the player's inventory. */
    @FXML
    private StackPane submarineStack;
    /** StackPane for the aircraft carrier in the player's inventory. */
    @FXML
    private StackPane carrierStack;
    /** GridPane representing the player's board. */
    @FXML
    private GridPane playerGridPane;
    /** GridPane representing the machine's board. */
    @FXML
    private GridPane machineGridPane;
    /** Button to start the game once ships are placed. */
    @FXML
    private Button playButton;
    /** Button to restart the current game. */
    @FXML
    private Button restartButton;
    /** Button to save the current game state. */
    @FXML
    private Button saveButton;
    /** Button to toggle visibility of machine's ships. */
    @FXML
    private Button viewButton;
    /** Label showing whose turn it is (player or machine). */
    @FXML
    private Label turnLabel;
    /** Label for showing error messages related to ship placement or gameplay. */
    @FXML
    private Label errorLabel;
    /** Label displaying the current player's score. */
    @FXML
    private Label scoreLabel;

    /** Stores the X-axis offset during drag events. */
    private double dragDeltax;

    /** Stores the Y-axis offset during drag events. */
    private double dragDeltay;
    /** Main game model object holding game state and logic. */
    private Game game;
    /** The player's board object. */
    private Board playerBoard;
    /** The machine's board object. */
    private Board machineBoard;
    /** Whether the machine's ships are visible (for debugging or viewing). */
    private Boolean machineShipsVisible;
    /** Handler for reading/writing plain text files (nickname/score). */
    private PlainTextFileHandler plainTextFileHandler;
    /** Flag indicating whether the game is loading a previously saved state. */
    private Boolean continueGame;
    /** Handler for serializing and deserializing game objects. */
    private SerializableFileHandler serializableFileHandler;
    /** Matrix-like structure for accessing player cell StackPanes. */
    private ArrayList<ArrayList<StackPane>> playerStackPanes;
    /** Matrix-like structure for accessing machine cell StackPanes. */
    private ArrayList<ArrayList<StackPane>> machineStackPanes;
    /** List of Pane objects representing all player ships on the board. */
    private ArrayList<Pane> playerShipPanes = new ArrayList<Pane>();
    /** List of Pane objects representing all machine ships on the board. */
    private ArrayList<Pane> machineShipPanes  = new ArrayList<Pane>();
    /** Matrix to track ship PANE positions on the machine's board. */
    private Pane[][] machineShipPanesPositions = new Pane[10][10];
    /** Matrix to track ship PANE positions on the player's board. */
    private Pane[][] playerShipPanesPositions = new Pane[10][10];
    /** Name of the file used to save the game state. */
    private static final String SAVE_FILE = "savegame.ser";

    /**
     * Initializes controller dependencies after FXML injection.
     * Instantiates file handlers used throughout the controller.
     */
    @FXML
    public void initialize() {
        plainTextFileHandler = new PlainTextFileHandler();
        serializableFileHandler = new SerializableFileHandler();
    }

    /**
     * Configures the controller based on whether the game should load
     * a saved state or start a new one. Initializes game data, boards, and UI.
     */
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
            handleLabels();
        }
        plainTextFileHandler = new PlainTextFileHandler();
        handleButtons();
    }

    /**
     * Reads the player's nickname from file, sets initial score,
     * and updates label elements for display.
     */
    public void handleLabels(){
        try{
            String nick = plainTextFileHandler.readFromFile("nickname.csv")[0];
            int score = 0;

            game.getPlayer().setNickname(nick);
            game.getPlayer().setScore(score);

            turnLabel.setText("Posiciona tus barcos " + nick);
            scoreLabel.setText(Integer.toString(score));

            scoreLabel.setVisible(true);
            turnLabel.setVisible(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        errorLabel.setVisible(false);
    }

    /**
     * Sets up event handlers for all primary buttons.
     */
    public void handleButtons(){
        handlePlayButton();
        handleViewButton();
        handleRestartButton();
        handleSaveButton();
    }

    /**
     * Sets the action for the "Save" button to persist the current game state.
     */
    public void handleSaveButton(){
        saveButton.setOnAction(e ->{
            saveGame();
        });
    }
    /**
     * Sets the action for the "Restart" button to reload the game view
     * and reset the controller to a new game state.
     */
    public void handleRestartButton(){
        restartButton.setOnAction(e -> {
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/miniproyecto_3/GameView.fxml"));
                Scene scene = new Scene(loader.load());

                GameController gameController = loader.getController();
                gameController.setContinueGame(false);
                gameController.setUpController();

                Stage stage = (Stage) restartButton.getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException ex) {
                showError(errorLabel, "Error al reiniciar el juego: " + ex.getMessage());
            }
        });
    }
    /**
     * Sets the action for the "Play" button to validate board setup
     * and transition to gameplay mode.
     */
    public void handlePlayButton() {
        playButton.setOnAction(e -> {
            try{
                setPlayState();
            } catch (Exception ex) {
                showError(errorLabel, ex.getMessage());
            }

        });
    }

    /**
     * Transitions the game to play mode:
     * Validates ship placement, initializes boards with clickable cells,
     * assigns events to handle player shots and machine turn logic.
     *
     * @throws IncompleteBoard if the player hasn't placed all ships.
     */
    public void setPlayState() throws IncompleteBoard{
        if(playerBoard.getShips().size() < 10){
            throw new IncompleteBoard("¡Posiciona todos tus barcos antes de jugar!");
        }

        if(game.getTurn() == Game.Turn.PLAYER){
            turnLabel.setText("Turno: " + game.getPlayer().getNickname());
        }else if(game.getTurn() == Game.Turn.MACHINE){
            turnLabel.setText("Turno: Máquina");
        }

        placeShips(machineBoard, machineShipPanes, machineGridPane, machineShipPanesPositions);
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
                        shootCell(new Coordinate(row, col), machineBoard, machineStackPanes, machineShipPanesPositions);

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

    /**
     * Draws the player's ships of different sizes and adds them to the corresponding ship stacks.
     * Also sets up drag and rotate behavior for each ship.
     */
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

    /**
     * Assigns the drag-and-drop behavior and placement validation to a ship.
     * Allows rotation with right-click and removes the ship from the board when picked up.
     *
     * @param ship the Ship object being moved
     * @param stack the original stack the ship was placed in
     * @param pane the visual representation of the ship as a Pane
     */
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

    /**
     * Simulates the machine's turn in the game.
     * After a short delay to mimic thinking time, the machine selects a target on the player's board
     * and fires at it. If the shot is a hit and the game is not over, the machine shoots again.
     * Automatically saves the game state after each move.
     */
    private void machineTurn() {
        PauseTransition pause = new PauseTransition(Duration.seconds(1)); // Retardo simula "pensar"
        pause.setOnFinished(e -> {
            // La máquina selecciona un objetivo en el tablero del jugador
            Coordinate target = game.getMachine().selectTarget(playerBoard);
            shootCell(target, playerBoard, playerStackPanes, playerShipPanesPositions);
            System.out.println("Machine fired at: (" + target.getCol() + ", " + target.getRow() + ")");
            // Si el disparo impacta (HIT), permite seguir disparando
            if (game.getTurn() == Game.Turn.MACHINE) {
                PauseTransition smallPause = new PauseTransition(Duration.seconds(1));
                smallPause.setOnFinished(event ->{machineTurn();});
                smallPause.play();
            }
            // Autosave tras la jugada de la máquina
            saveGame();
        });
        pause.play();

    }

    /**
     * Executes a shot on the specified target coordinate of a given board.
     * Updates the UI based on the result of the shot (hit, miss, or sunk),
     * manages score updates, end-game condition, and saves the game state.
     *
     * @param target         the coordinate to fire at
     * @param board          the board (player or machine) being targeted
     * @param stackPanes     the stack pane matrix representing the visual grid
     * @param panePositions  the pane matrix containing ship visuals for interaction
     */
    private void shootCell(Coordinate target, Board board, ArrayList<ArrayList<StackPane>> stackPanes, Pane[][] panePositions) {
        try{
            Cell.CellState result = game.fire(target, board);
            int x = target.getCol();
            int y = target.getRow();
            formatShotCell(board.getCell(x, y), stackPanes, panePositions);
            String turnMessage = (game.getTurn() == Game.Turn.PLAYER) ? "Turno: " + game.getPlayer().getNickname() : "Turno: Máquina";

            if(result == Cell.CellState.MISS){
                showTempMessage(turnLabel, "Tiro al agua en: (" + target.getCol() + ", " + target.getRow() + ")",turnMessage, 1);

            } else if (result == Cell.CellState.HIT){
                if (game.isGameOver()) {
                    File savedGame = new File(SAVE_FILE);
                    if (savedGame.exists()) {
                        savedGame.delete();
                    }
                    System.out.println("Game Over! " + (game.playerWon() ? "Player wins!" : "Machine wins!"));
                    showEndScreen(game.playerWon());
                    return;
                }
                if(board.getCell(x, y).getShip().isSunk()){
                    showTempMessage(turnLabel, board.getCell(x, y).getShip().getKind()+ " hundido!",  turnMessage, 1);
                    if(game.isPlayerTurn()){
                        game.getPlayer().plusOneScore();
                        scoreLabel.setText(Integer.toString(game.getPlayer().getScore()));
                    }
                } else{
                    showTempMessage(turnLabel, "Barco golpeado en: (" + target.getCol() + ", " + target.getRow() + ")", turnMessage ,1);
                }
            }

        }catch(NonShootableCell ex) {
            showError(errorLabel, ex.getMessage());
        }

        saveGame();
    }

    /**
     * Saves the current game state using both a serializable binary format and a plain text file.
     * The plain text file stores the player's nickname and score for quick access.
     */
    private void saveGame() {
        serializableFileHandler = new SerializableFileHandler();
        serializableFileHandler.serialize(SAVE_FILE, game);
        plainTextFileHandler.writeToFile("nickname.csv",  game.getPlayer().getNickname() + "," + game.getPlayer().getScore());
    }

    /**
     * Loads a previously saved game from file. If successful, restores the player's and machine's boards,
     * updates the visual components, and resumes play state. If loading fails, starts a new game
     * and displays an error message.
     */
    private void loadGameState() {
        serializableFileHandler = new SerializableFileHandler();
        try{
            Game loadedGame = (Game) serializableFileHandler.deserialize(SAVE_FILE);
            if (loadedGame != null) {
                game = loadedGame;
                System.out.println("Score: " + game.getPlayer().getScore());
                playerBoard = game.getPlayerBoard();
                machineBoard = game.getMachineBoard();
                placeShips(playerBoard, playerShipPanes, playerGridPane, playerShipPanesPositions);
                setPlayState();
                updateBoard(playerBoard, playerStackPanes, playerShipPanesPositions);
                updateBoard(machineBoard, machineStackPanes, machineShipPanesPositions);

                scoreLabel.setText(Integer.toString(game.getPlayer().getScore()));
                scoreLabel.setVisible(true);
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



    /**
     * Places the ships visually on the grid pane by creating pane representations
     * and aligning them based on the model Ships orientation and coordinates.
     *
     * @param board            the board containing the ships to place
     * @param paneList         the list that will hold the ship panes
     * @param gridPane         the grid pane where ships are to be displayed
     * @param panesPositions   the matrix storing the position of each ship pane
     */
    private void placeShips(Board board, ArrayList<Pane> paneList, GridPane gridPane, Pane[][] panesPositions) {
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
            panesPositions[cellX][cellY] = pane;
            System.out.println("Boat at: (" + cellX + ", " + cellY + ")");

        }
    }


    /**
     * Sets up the event handler for the view button, allowing the player to toggle
     * the visibility of the enemy ships. If the player hasn't placed their ships,
     * it shows an error message.
     */
    private void handleViewButton(){
        viewButton.setOnAction(e ->{
            if(machineShipsVisible == null){
                showError(errorLabel, "Debes posicionar tus barcos primero!");
            }else{
                if(machineShipsVisible){
                    hideEnemyShips();
                    machineShipsVisible = false;
                } else {
                    viewEnemyShips();
                    machineShipsVisible = true;
                }
            }
        });
    }

    /**
     * Makes all enemy ship panes visible on the machine's grid.
     * Used for debugging when the player chooses to reveal the enemy ships.
     */
    private void viewEnemyShips() {
        machineShipsVisible = true;
        for(Pane pane: machineShipPanes){
            pane.setVisible(true);
        }
    }

    /**
     * Hides all enemy ship panes from view on the machine's grid.
     * This is used to conceal the enemy ships again after being shown.
     */
    private void hideEnemyShips() {
        machineShipsVisible = false;
        for(Pane pane: machineShipPanes){
            pane.setVisible(false);
        }
    }

    /**
     * Displays an error message using a fading label animation.
     * The message fades in, stays visible for a short time, then fades out.
     *
     * @param label   the label to display the message on
     * @param message the error message to show
     */
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

    /**
     * Temporarily displays a message in the given label for a specified duration,
     * then reverts to the default message.
     *
     * @param label          the Label to update
     * @param tempMessage    the temporary message to show
     * @param defaultMessage the message to revert back to after the delay
     * @param seconds        how long (in seconds) to display the temporary message
     */
    public void showTempMessage(Label label, String tempMessage, String defaultMessage, double seconds) {
        label.setText(tempMessage);
        PauseTransition pause = new PauseTransition(Duration.seconds(seconds));
        pause.setOnFinished(e -> label.setText(defaultMessage));
        pause.play();
    }

    /**
     * Creates a Pane representing a ship of the specified length.
     *
     * @param length the length of the ship (1 to 4)
     * @return a Pane visually representing the ship; an empty Pane if length is invalid
     */
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

    /**
     * Flips the orientation of the given ship and rotates its visual pane accordingly.
     *
     * @param ship the Ship object to flip orientation
     * @param pane the Pane representing the ship that will be rotated
     */
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

    /**
     * Displays the end game screen, indicating whether the player has won or lost.
     *
     * @param playerWon true if the player won the game, false otherwise
     */
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

    /**
     * Updates the visual representation of the entire board based on cell states.
     *
     * @param board       the Board to update
     * @param stackPanes  the nested list of StackPanes representing the board cells
     * @param panePositions the matrix of Panes representing ship positions
     */
    public void updateBoard (Board board, ArrayList<ArrayList<StackPane>> stackPanes, Pane[][] panePositions){
        for(int i = 0; i < board.getSize(); i++){
            for(int j = 0; j < board.getSize(); j++){
                Cell cell = board.getCell(i, j);
                formatShotCell(cell,  stackPanes, panePositions);
            }
        }
    }

    /**
     * Formats a single cell's visual representation based on its state (HIT or MISS).
     * If a ship is sunk it makes it visible and marks the whole ship with skull icons.
     *
     * @param cell          the Cell to format
     * @param stackPanes    the nested list of StackPanes representing the board cells
     * @param panePositions the matrix of Panes representing ship positions
     */
    public void formatShotCell (Cell cell, ArrayList<ArrayList<StackPane>> stackPanes, Pane[][] panePositions){
        ShapeDrawer shapeDrawer = new ShapeDrawer();
        int X = cell.getCoordinate().getCol();
        int Y = cell.getCoordinate().getRow();
        StackPane pane = stackPanes.get(X).get(Y);
        // Semi-transparent background: red with 50% opacity
        BackgroundFill hitBackgroundFill = new BackgroundFill(Color.rgb(251, 172, 20, 0.3), CornerRadii.EMPTY, Insets.EMPTY);
        BackgroundFill missBackgroundFill = new BackgroundFill(Color.rgb(58, 227, 239, 0.3), CornerRadii.EMPTY, Insets.EMPTY);

        if (cell.getState() == Cell.CellState.HIT) {
            if(cell.getShip().isSunk()){

                Ship ship = cell.getShip();
                int initialX = ship.getHeadCoord().getCol();
                int initialY = ship.getHeadCoord().getRow();
                panePositions[initialX][initialY].setVisible(true);
                for(int i = 0; i < ship.getLength(); i++){
                    if(ship.getOrientation() == Ship.Orientation.HORIZONTAL){
                        StackPane sunkPane = stackPanes.get(initialX + i).get(initialY);
                        sunkPane.getChildren().clear();
                        sunkPane.getChildren().add(shapeDrawer.drawSkull());
                        sunkPane.setBackground(new Background(hitBackgroundFill));
                    }else if(ship.getOrientation() == Ship.Orientation.VERTICAL){
                        StackPane sunkPane = stackPanes.get(initialX).get(initialY + i);
                        sunkPane.getChildren().clear();
                        sunkPane.getChildren().add(shapeDrawer.drawSkull());
                        sunkPane.setBackground(new Background(hitBackgroundFill));
                    }
                }
            }else{
                pane.getChildren().clear();
                pane.getChildren().add(shapeDrawer.drawExplosion());
                pane.setBackground(new Background(hitBackgroundFill));

            }

        } else if (cell.getState() == Cell.CellState.MISS) {
            pane.getChildren().clear();
            pane.getChildren().add(shapeDrawer.drawX());
            pane.setBackground(new Background(missBackgroundFill));
        }
    }

    /**
     * Sets the flag indicating whether the current game session should continue.
     *
     * @param continueGame true to continue the game, false otherwise
     */
    public void setContinueGame(Boolean continueGame) {
        this.continueGame = continueGame;
    }
}
