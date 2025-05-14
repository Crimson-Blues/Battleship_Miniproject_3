package org.example.miniproyecto_3.Model;

import org.example.miniproyecto_3.Model.Exceptions.NonShootableCell;
import org.example.miniproyecto_3.Model.Exceptions.OverlappingShip;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Board implements Serializable {

    private final int SIZE = 10;
    private ArrayList<ArrayList<Cell>> grid;
    private ArrayList<Ship> ships;

    public Board() {
        grid = new ArrayList<ArrayList<Cell>>(SIZE);
        ships = new ArrayList<Ship>();
        for (int i = 0; i < SIZE; i++) {
            //Agrega el arraylist de cellState
            grid.add(new ArrayList<Cell>(SIZE));

            for (int j = 0; j < SIZE; j++) {
                grid.get(i).add(new Cell(new Coordinate(i, j)));
            }
        }

    }
    public int getSize() {
        return SIZE;
    }

    public Cell getCell(int row, int col) {
        return grid.get(row).get(col);
    }

    public List<Ship> getShips() {
        return ships;
    }

    private boolean isValidCoordinate(Coordinate coord) {
        int row = coord.getRow();
        int column = coord.getCol();

        return row >= 0 && row < SIZE && column >= 0 && column < SIZE;
    }

    public boolean shipsSunk() {
        for (Ship ship : ships) {
            if (!ship.isSunk()) {
                return false;
            }
        }
        return true;
    }

    public boolean canShoot(Coordinate coor) {
        if (!isValidCoordinate(coor)) {
            return false;
        }
        Cell cell = grid.get(coor.getRow()).get(coor.getCol());
        // if(cell == CellState.MISS || cell == CellState.HIT){
        //   return false;
        // }
        // return true;

        return (cell.getState() == Cell.CellState.EMPTY || cell.getState() == Cell.CellState.SHIP);

    }

    public void placeShip(Ship ship, List<Coordinate> coords) {
        // Primero, se verifica que todas las celdas estén disponibles
        for (Coordinate cord : coords) {
            int row = cord.getRow();
            int column = cord.getCol();
            Cell cell = grid.get(row).get(column);
            if (cell.getState() == Cell.CellState.SHIP)
                throw new OverlappingShip("Barco posicionado sobre otro");
        }
        // Si todas las celdas están libres, se asigna el barco a cada celda
        for (Coordinate cord : coords) {
            int row = cord.getRow();
            int column = cord.getCol();
            Cell cell = grid.get(row).get(column);
            cell.setShip(ship);
            cell.setState(Cell.CellState.SHIP);
            System.out.println("Barco posicionado en " + row + ", " + column);
        }
        // Se agrega el barco solo una vez a la lista
        ships.add(ship);
    }

    public void removeShip(Ship ship) {
        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++){
                Cell cell = grid.get(i).get(j);
                if(cell.getShip() == ship){
                    cell.setShip(null);
                    cell.setState(Cell.CellState.EMPTY);
                }
            }
        }
    }

    public Cell.CellState fireAt(Coordinate coor) throws NonShootableCell {
        if (!isValidCoordinate(coor)) {
            throw new IllegalArgumentException("La coordenada no puede salirse del tablero");
        }
        Cell cell = grid.get(coor.getRow()).get(coor.getCol());
        if (!canShoot(coor)) {
            throw new NonShootableCell("Celda (" + coor.getRow() + ", " + coor.getCol() + ") ya fue golpeada");
        }
        cell.hit();
        return cell.getState();
    }
}