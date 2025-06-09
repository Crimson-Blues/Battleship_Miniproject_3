package org.example.miniproyecto_3.Model;

import java.io.Serializable;

/**
 * Represents a coordinate on the game board.
 * <p>
 * A coordinate is defined by its row and column values. This class is immutable.
 * </p>
 */
public class Coordinate implements Serializable {
    /**
     * The row index of the coordinate (0-based).
     */
    private final int row;
    /**
     * The column index of the coordinate (0-based).
     */
    private final int col;

    /**
     * Constructs a coordinate with the specified row and column.
     *
     * @param row the row index
     * @param col the column index
     */
    public Coordinate (int row, int col){
        this.row = row;
        this.col = col;
    }
    /**
     * Returns the column index of the coordinate.
     *
     * @return the column index
     */
    public int getCol() {
        return col;
    }

    /**
     * Returns the row index of the coordinate.
     *
     * @return the row index
     */
    public int getRow() {
        return row;
    }

    /**
     * Indicates whether this coordinate is equal to another object.
     * <p>
     * Two coordinates are considered equal if they have the same row and column values.
     * </p>
     *
     * @param obj the object to compare with
     * @return {@code true} if the objects are equal; {@code false} otherwise
     */
    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Coordinate other = (Coordinate) obj;
        return row == other.getRow() && col == other.getCol();
    }

    /**
     * Returns a hash code value for this coordinate.
     * <p>
     * Combines the row and column using a standard formula to ensure good distribution
     * when used in hash-based collections.
     * </p>
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        // 31 is just a commonly used prime multiplier
        return 31 * row + col;
    }

}
