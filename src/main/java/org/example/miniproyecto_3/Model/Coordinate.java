package org.example.miniproyecto_3.Model;

import java.io.Serializable;

public class Coordinate implements Serializable {
    private final int row;
    private final int col;
    public Coordinate (int row, int col){
        this.row = row;
        this.col = col;
    }
    public int getCol() {
        return col;
    }
    public int getRow() {
        return row;
    }
    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Coordinate other = (Coordinate) obj;
        return row == other.getRow() && col == other.getCol();
    }

    @Override
    public int hashCode() {
        // 31 is just a commonly used prime multiplier
        return 31 * row + col;
    }
}
