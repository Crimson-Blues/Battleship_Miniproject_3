package org.example.miniproyecto_3.Model;

import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a ship in the Battleship game.
 * <p>
 * Each ship has a specific length, type (e.g., Fragata, Destructor), orientation,
 * and position on the board. It also tracks the coordinates where it has been hit.
 * </p>
 */
public class Ship extends ShipAdapter implements Serializable {
    /**
     * The length of the ship (number of cells it occupies).
     */
    private int length;
    /**
     * The set of coordinates where the ship has been hit.
     */
    private Set<Coordinate> hits;
    /**
     * The starting coordinate (head) of the ship on the board.
     */
    private Coordinate headCoord;
    /**
     * The type of ship (e.g., Fragata, Destructor).
     */

    private String kind;

    /**
     * Constructs a ship with the specified length.
     * <p>
     * Sets the ship type based on its length:
     * <ul>
     *     <li>1 - Fragata</li>
     *     <li>2 - Destructor</li>
     *     <li>3 - Submarino</li>
     *     <li>4 - Portaaviones</li>
     * </ul>
     * </p>
     *
     * @param length the number of cells this ship occupies; must be positive
     * @throws IllegalArgumentException if the length is not positive
     */
    public Ship(int length){
        try{
            if(length<= 0)
                throw new IllegalArgumentException("La longitud del barco tiene que ser positiva");
            this.length = length;
            this.hits = new HashSet <Coordinate>();
            switch (length){
                case 1: kind = "Frágata"; break;
                case 2: kind = "Destructor" ; break;
                case 3: kind = "Submarino"; break;
                case 4: kind = "Portaaviones"; break;
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Returns the length of the ship.
     *
     * @return the length of the ship
     */
    public int getLength(){
        return length;
    }
    /**
     * Returns the set of coordinates where the ship has been hit.
     *
     * @return a set of {@link Coordinate} objects representing hits
     */
    public Set<Coordinate> getHits(){
        return hits;
    }
    /**
     * Indicates whether the ship is sunk.
     * <p>
     * A ship is considered sunk when the number of hits equals its length.
     * </p>
     *
     * @return {@code true} if the ship is sunk; {@code false} otherwise
     */

    public boolean isSunk(){
        return hits.size() == length;
    }

    /**
     * Records a hit at the specified coordinate.
     *
     * @param h the coordinate of the hit
     */
    public void hit(Coordinate h){
        hits.add(h);
    }

    /**
     * Sets the head (starting point) coordinate of the ship on the board.
     *
     * @param h the head coordinate
     */
    public void setHeadCoord(Coordinate h){
        headCoord = h;
    }

    /**
     * Returns the head (starting point) coordinate of the ship.
     *
     * @return the head coordinate
     */
    public Coordinate getHeadCoord(){
        return headCoord;
    }

    /**
     * Sets the orientation of the ship.
     *
     * @param o the new orientation (horizontal or vertical)
     */
    public void setOrientation(Orientation o){
        orientation = o;
        if(orientation == Orientation.HORIZONTAL){
        } else if(orientation == Orientation.VERTICAL){
        }
    }

    /**
     * Returns the kind (type) of ship based on its length.
     *
     * @return a string representing the kind of ship
     */
    public String getKind(){
        return kind;
    }
}