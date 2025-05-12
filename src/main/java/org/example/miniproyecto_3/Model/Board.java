package org.example.miniproyecto_3.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Board implements Serializable {

    public enum CellState {
        EMPTY,
        SHIP,
        HIT,
        MISS
    }

    private final int SIZE = 10;
    private ArrayList<ArrayList<CellState>> grid;
    private ArrayList<Ship> ships;

    public Board() {
        grid = new ArrayList<ArrayList<CellState>>(SIZE);
        ships = new ArrayList<Ship>();
        for (int i = 0; i < SIZE; i++) {
            //Agrega el arraylist de cellState
            grid.add(new ArrayList<CellState>(SIZE));

            for (int j = 0; j < SIZE; j++) {
                grid.get(i).add(CellState.EMPTY);
            }
        }

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

    public boolean canShot(Coordinate coor) {
        if (!isValidCoordinate(coor)) {
            return false;
        }
        CellState cell = grid.get(coor.getRow()).get(coor.getCol());
        // if(cell == CellState.MISS || cell == CellState.HIT){
        //   return false;
        // }
        // return true;

        return !(cell == CellState.MISS || cell == CellState.HIT);

    }

    public boolean placeShip(Ship ship, Coordinate coor, boolean horizontal) {
        if (!isValidCoordinate(coor)) {
            return false;
        }
        int length = ship.getLength();
        int row = coor.getRow();
        int col = coor.getCol();
        if (horizontal) {
            if (col + length > SIZE) {
                return false;
            }
        } else {
            if (row + length > SIZE) {
                return false;
            }
        }

        // for (int i = 0; i < length; i++) {
        //   int r = coor.getRow() + (horizontal ? 0 : 1);
        //   int c = coor.getCol() + (horizontal ? 1 : 0);

        //   if (grid.get(r).get(c) != CellState.EMPTY)
        //     return false;
        // }
        if (horizontal) {
            for (int i = 0; i < length; i++) {
                CellState cell = grid.get(coor.getRow()).get(coor.getCol() + i);
                if (cell != CellState.EMPTY) {
                    return false;
                }
            }
        } else {
            for (int i = 0; i < length; i++) {
                CellState cell = grid.get(coor.getRow() + i).get(coor.getCol());
                if (cell != CellState.EMPTY) {
                    return false;
                }
            }
        }
        ArrayList<Coordinate> positions = new ArrayList<Coordinate>(length);
        if (horizontal) {
            for (int i = 0; i < length; i++) {
                grid.get(coor.getRow()).set(coor.getCol() + i, CellState.SHIP);
                positions.add(new Coordinate(coor.getRow(), coor.getCol() + i));
            }
        } else {
            for (int i = 0; i < length; i++) {
                grid.get(coor.getRow() + i).set(coor.getCol(), CellState.SHIP); // grid[coor.getRow() + i][coor.getCol()] = CellState.SHIP
                positions.add(new Coordinate(coor.getRow() + i, coor.getCol()));
            }
        }
        ship.setCoodinate(positions);
        ships.add(ship);

        return true;

    }

    public boolean fireAt(Coordinate coor) {
        if (!isValidCoordinate(coor)) {
            throw new IllegalArgumentException("Error! La coordenada no puede salirse del tablero");
        }
        if (!canShot(coor)) {
            return false;
        }
        CellState cell = grid.get(coor.getRow()).get(coor.getCol());
        if (cell == CellState.SHIP) {
            grid.get(coor.getRow()).set(coor.getCol(), CellState.HIT);
            for (Ship ship : ships) {
                if (ship.contains(coor)) {
                    ship.hit(coor);
                    break;
                }
            }
            return true;
        } else {
            grid.get(coor.getRow()).set(coor.getCol(), CellState.MISS);
            return false;
        }
    }
}