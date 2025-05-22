package org.example.miniproyecto_3.Model;

import javafx.scene.layout.StackPane;

import java.io.Serializable;

public class Cell implements Serializable {
    public enum CellState {
        EMPTY,
        SHIP,
        HIT,
        MISS
    }

    private CellState state;
    private Ship ship;
    private Coordinate coordinate;


    public Cell(Coordinate cord){
        this.state = CellState.EMPTY;
        this.ship = null;
        this.coordinate = cord;
    }

    public CellState getState() {
        return state;
    }

    public void setState(CellState state) {
        this.state = state;
    }

    public Ship getShip() {
        return ship;
    }
    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }


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
