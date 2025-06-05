package org.example.miniproyecto_3.Model;

import org.example.miniproyecto_3.Model.Exceptions.NonShootableCell;

import java.io.Serializable;

/**
 * Manages the state and logic of a Battleship game session.
 * <p>
 * This class contains the player and machine boards, tracks turns, handles firing,
 * and determines win conditions. It also manages the game loop between the player and the machine.
 * </p>
 */
public class Game implements Serializable {
    /**
     * Enumeration to represent the current turn of the game.
     */
    public enum Turn {
        PLAYER, MACHINE
    }
    private final Board playerBoard;
    private final Board machineBoard;
    private final Machine machine;
    private Turn turn;
    private final Player player;

    /**
     * Constructs a new game session. Initializes boards, places ships for the machine,
     * sets the turn to the player, and creates a new player.
     */
    public Game() {
        this.playerBoard = new Board();
        this.machineBoard = new Board();
        this.machine = new Machine();
        // La máquina coloca sus barcos en su tablero
        this.machine.placeShips(machineBoard);
        this.turn = Turn.PLAYER;
        this.player = new Player();
    }

    /**
     * Returns the board used by the player.
     *
     * @return the player's board
     */
    public Board getPlayerBoard() {
        return playerBoard;
    }

    /**
     * Returns the board used by the machine.
     *
     * @return the machine's board
     */
    public Board getMachineBoard() {
        return machineBoard;
    }

    /**
     * Returns the {@link Machine} instance for the game.
     *
     * @return the machine logic controller
     */
    public Machine getMachine() {
        return machine;
    }

    /**
     * Checks whether it is currently the player's turn.
     *
     * @return true if it is the player's turn, false otherwise
     */
    public boolean isPlayerTurn() {
        return (turn == Turn.PLAYER);
    }

    /**
     * Switches the turn between player and machine.
     */
    private void toggleTurn() {
        if (turn == Turn.PLAYER) {
            turn = Turn.MACHINE;
        } else if (turn == Turn.MACHINE) {
            turn = Turn.PLAYER;
        }
    }

    /**
     * Fires at the given coordinate on the specified board.
     * <p>
     * If the result is a {@link Cell.CellState#MISS}, the turn is toggled.
     * </p>
     *
     * @param coord the coordinate to fire at
     * @param board the board to fire on (player's or machine's)
     * @return the result of the shot (HIT, MISS, SUNK, etc.)
     * @throws NonShootableCell if the cell has already been shot
     */
    public Cell.CellState fire(Coordinate coord, Board board) throws NonShootableCell {

        Cell.CellState hit = board.fireAt(coord);
        if (hit == Cell.CellState.MISS) {
            toggleTurn();
        }

        return hit;

    }

    /**
     * Returns whose turn it is.
     *
     * @return the current turn
     */
    public Turn getTurn() {
        return turn;
    }

    /**
     * Checks if the game is over.
     * <p>
     * The game ends when all ships of either the player or the machine are sunk.
     * </p>
     *
     * @return true if the game is over, false otherwise
     */
    public boolean isGameOver() {
        return playerBoard.shipsSunk() || machineBoard.shipsSunk();
    }

    /**
     * Checks if the player has won the game.
     *
     * @return true if all machine ships are sunk, false otherwise
     */
    public boolean playerWon() {
        return machineBoard.shipsSunk();
    }

    /**
     * Returns the current {@link Player} instance.
     *
     * @return the player object
     */
    public Player getPlayer(){
        return player;
    }
}
