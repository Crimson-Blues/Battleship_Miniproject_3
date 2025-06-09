package org.example.miniproyecto_3.Model;

import java.io.Serializable;

/**
 * Represents a ship in the game.
 * <p>
 * This interface defines common operations and properties
 * that any type of ship must implement, such as orientation and sunk state.
 * </p>
 *
 * <p>This interface extends {@link java.io.Serializable} to support object persistence.</p>
 */
public interface IShip extends Serializable {
    /**
     * Represents the orientation of a ship on the board.
     */
    enum Orientation {
        /**
         * Ship is placed horizontally (left to right).
         */
        HORIZONTAL,

        /**
         * Ship is placed vertically (top to bottom).
         */
        VERTICAL
    }
    /**
     * Represents the sinking state of a ship.
     */
    enum State {
        /**
         * The ship has been sunk (all its parts have been hit).
         */
        SUNK,

        /**
         * The ship has not yet been sunk.
         */
        NOT_SUNK
    }

    /**
     * Determines whether the ship has been sunk.
     *
     * @return {@code true} if the ship is sunk, {@code false} otherwise
     */
    boolean isSunk();
    /**
     * Sets the sinking state of the ship.
     *
     * @param state the new sinking {@link State} of the ship
     */
    void setSunk(State state);
    /**
     * Returns the {@link Orientation} of the ship.
     *
     * @return the orientation of the ship
     */
    Orientation getOrientation();
    /**
     * Flips the orientation of the ship.
     * <p>
     * If the ship is currently {@link Orientation#HORIZONTAL}, it becomes {@link Orientation#VERTICAL}, and vice versa.
     * </p>
     */
    void flip();
}
