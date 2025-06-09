package org.example.miniproyecto_3.Model;

import javafx.scene.layout.StackPane;

import java.io.Serializable;

/**
 * Represents a cell in the game board.
 * <p>
 * Each cell holds its {@link CellState}, an optional {@link Ship} if one is placed there,
 * and its {@link Coordinate} on the board. The cell can be empty, contain a ship, or have been hit/missed.
 * </p>
 *
 * <p>This class implements {@link java.io.Serializable} to allow saving/loading game state.</p>
 */
public class Cell implements Serializable {

    /**
     * Enumeration of possible states a {@code Cell} can be in.
     */
    public enum CellState {
        /**
         * The cell is empty (does not contain a Ship) and has not been shot.
         */
        EMPTY,
        /**
         * The cell contains a ship.
         */
        SHIP,
        /**
         * The cell has been shot and hit a ship.
         */
        HIT,
        /**
         * The cell has been shot but was empty.
         */
        MISS
    }

    /**
     * The current state of the cell.
     */
    private CellState state;
    /**
     * The {@link Ship} occupying the cell, if any. May be {@code null}.
     */
    private Ship ship;
    /**
     * The {@link Coordinate} representing the cell's location on the board.
     */
    private Coordinate coordinate;

    /**
     * Constructs a new {@code Cell} with the given coordinate.
     * Initializes the state to {@link CellState#EMPTY} and no ship.
     *
     * @param cord the coordinate of this cell on the board
     */
    public Cell(Coordinate cord){
        this.state = CellState.EMPTY;
        this.ship = null;
        this.coordinate = cord;
    }

    /**
     * Returns the current {@link CellState} of the cell.
     *
     * @return the state of the cell
     */

    public CellState getState() {
        return state;
    }

    /**
     * Sets the {@link CellState} of the cell.
     *
     * @param state the new state to set
     */
    public void setState(CellState state) {
        this.state = state;
    }

    /**
     * Returns the {@link Ship} associated with this cell, or {@code null} if none.
     *
     * @return the ship in this cell, or {@code null}
     */
    public Ship getShip() {
        return ship;
    }

    /**
     * Sets the {@link Ship} occupying this cell.
     *
     * @param ship the ship to associate with this cell
     */
    public void setShip(Ship ship) {
        this.ship = ship;
    }

    /**
     * Returns the {@link Coordinate} of this cell on the board.
     *
     * @return the coordinate of this cell
     */
    public Coordinate getCoordinate() {
        return coordinate;
    }

    /**
     * Represents a shot fired at the cell.
     * <ul>
     *     <li>If the cell contains a ship, it is marked as {@link CellState#HIT}
     *     and the ship is notified of the hit at this coordinate.</li>
     *     <li>If the cell is empty, it is marked as {@link CellState#MISS}.</li>
     * </ul>
     * Prints the result to the console.
     */
    public void hit(){
        if (this.state == Cell.CellState.SHIP) {
            setState(Cell.CellState.HIT);
            ship.hit(coordinate);
            System.out.println("Le diste a la nave en (" + this.coordinate.getCol() + ", " + this.coordinate.getRow() + ")");
        } else if (this.state == Cell.CellState.EMPTY) {
            setState(Cell.CellState.MISS);
            System.out.println("Disparo en el agua en (" + this.coordinate.getCol() + ", " + this.coordinate.getRow() + ")");
        }
    }
}
