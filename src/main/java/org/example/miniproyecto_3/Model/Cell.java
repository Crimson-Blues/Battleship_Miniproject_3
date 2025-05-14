package org.example.miniproyecto_3.Model;

import javafx.scene.layout.StackPane;

public class Cell {
    public enum CellState {
        EMPTY,
        SHIP,
        HIT,
        MISS
    }

    private CellState state;
    private Ship ship;
    private Coordinate coordinate;
    private StackPane clickChecker;

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

    public StackPane getClickChecker() {
        return clickChecker;
    }

    public void setClickChecker(StackPane clickChecker) {
        this.clickChecker = clickChecker;
        clickChecker.setOnMouseClicked(e -> handleClick());
    }
    public void  handleClick(){
        System.out.println("Clicked on: " + coordinate.getRow() + ", " + coordinate.getCol());
        clickChecker.setStyle("-fx-background-color: red");

    }
    public void hit(){
        if (this.state == Cell.CellState.SHIP) {
            setState(Cell.CellState.HIT);
            System.out.println("Le diste a la nave en (" + this.coordinate.getRow() + ", " + this.coordinate.getCol() + ")");
        } else if (this.state == Cell.CellState.EMPTY) {
            setState(Cell.CellState.SHIP);
        }
    }
}
