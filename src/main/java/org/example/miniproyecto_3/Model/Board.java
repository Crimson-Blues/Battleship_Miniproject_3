package org.example.miniproyecto_3.Model;

import org.example.miniproyecto_3.Model.Exceptions.NonShootableCell;
import org.example.miniproyecto_3.Model.Exceptions.OverlappingShip;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a game board in Battleship.
 * <p>
 * The board consists of a 10x10 grid of {@link Cell} objects, where ships can be placed
 * and attacks can be made. It handles all logic related to ship placement,
 * attacks, and the state of the game board.
 * </p>
 */
public class Board implements Serializable {
    /**
     * Size of the board (10x10).
     */
    private final int SIZE = 10;
    /**
     * 2D grid of cells representing the board.
     */
    private ArrayList<ArrayList<Cell>> grid;
    /**
     * List of all ships currently placed on the board.
     */
    private ArrayList<Ship> ships;

    /**
     * Constructs an empty board and initializes the grid.
     */
    public Board() {
        grid = new ArrayList<ArrayList<Cell>>(SIZE);
        ships = new ArrayList<Ship>();
        for (int i = 0; i < SIZE; i++) {
            //Agrega el arraylist de cellState
            grid.add(new ArrayList<Cell>(SIZE));
            for (int j = 0; j < SIZE; j++) {
                grid.get(i).add(new Cell(new Coordinate(j, i)));
            }
        }

    }
    /**
     * Gets the board size (always 10).
     *
     * @return the size of the board
     */
    public int getSize() {
        return SIZE;
    }

    /**
     * Returns the {@link Cell} at the specified coordinates.
     *
     * @param col the column index
     * @param row the row index
     * @return the cell at the given position
     */
    public Cell getCell(int col, int row) {
        return grid.get(col).get(row);
    }

    /**
     * Returns the list of ships currently placed on the board.
     *
     * @return list of placed ships
     */
    public List<Ship> getShips() {
        return ships;
    }

    /**
     * Checks whether the given coordinate is within the bounds of the board.
     *
     * @param coord the coordinate to validate
     * @return true if the coordinate is valid, false otherwise
     */
    private boolean isValidCoordinate(Coordinate coord) {
        int row = coord.getRow();
        int column = coord.getCol();

        return row >= 0 && row < SIZE && column >= 0 && column < SIZE;
    }

    /**
     * Checks if all ships on the board have been sunk.
     *
     * @return true if all ships are sunk, false otherwise
     */
    public boolean shipsSunk() {
        for (Ship ship : ships) {
            if (!ship.isSunk()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks whether the given cell can be targeted by a shot.
     *
     * @param coor the coordinate to check
     * @return true if the cell is shootable, false otherwise
     */
    public boolean canShoot(Coordinate coor) {
        if (!isValidCoordinate(coor)) {
            return false;
        }
        Cell cell = grid.get(coor.getCol()).get(coor.getRow());

        return (cell.getState() == Cell.CellState.EMPTY || cell.getState() == Cell.CellState.SHIP);

    }

    /**
     * Places a ship on the board at the given list of coordinates.
     * <p>
     * Each coordinate is assigned to the ship, and the ship is added to the board's ship list.
     * </p>
     *
     * @param ship the ship to place
     * @param coords the list of coordinates where the ship will be placed
     */
    public void placeShip(Ship ship, List<Coordinate> coords) {
        //Verifies that all coordinates are valid
        for (Coordinate cord : coords) {
            int row = cord.getRow();
            int column = cord.getCol();
            try{
                Cell cell = grid.get(column).get(row);
                if (cell.getState() == Cell.CellState.SHIP)
                    throw new OverlappingShip("Barco posicionado sobre otro");
            }catch(OverlappingShip e){
                System.out.println(e.getMessage());
            }

        }
        //If all cells are available assigns the ship to the corresponding cells
        for (Coordinate cord : coords) {
            int row = cord.getRow();
            int column = cord.getCol();
            Cell cell = grid.get(column).get(row);
            cell.setShip(ship);
            cell.setState(Cell.CellState.SHIP);
            System.out.println("Barco posicionado en " + column + ", " + row);
        }
        // Se agrega el barco solo una vez a la lista
        ship.setHeadCoord(new Coordinate(coords.get(0).getRow(), coords.get(0).getCol()));
        ships.add(ship);
    }

    /**
     * Removes a ship from the board and clears its associated cells.
     *
     * @param ship the ship to remove
     */
    public void removeShip(Ship ship) {
        ships.remove(ship);
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

    /**
     * Fires at the specified coordinate and updates the cell's state.
     *
     * @param coor the coordinate to fire at
     * @return the resulting state of the cell (HIT, MISS, etc.)
     * @throws NonShootableCell if the cell has already been targeted
     * @throws IllegalArgumentException if the coordinate is out of bounds
     */
    public Cell.CellState fireAt(Coordinate coor) throws NonShootableCell {
        if (!isValidCoordinate(coor)) {
            throw new IllegalArgumentException("La coordenada no puede salirse del tablero");
        }
        Cell cell = grid.get(coor.getCol()).get(coor.getRow());
        if (!canShoot(coor)) {
            throw new NonShootableCell("Celda (" + coor.getCol() + ", " + coor.getRow() + ") ya fue golpeada");
        }
        cell.hit();
        return cell.getState();
    }

    /**
     * Checks if a ship of the given size can be placed starting from the head coordinate
     * in the specified orientation without overlapping existing ships.
     *
     * @param size       the size of the ship
     * @param headCoord  the starting coordinate of the ship
     * @param orientation the orientation of the ship (HORIZONTAL or VERTICAL)
     * @return true if the ship can be placed, false otherwise
     */
    public boolean canPlaceShip(int size, Coordinate headCoord, IShip.Orientation orientation) {
        int initialX = headCoord.getCol();
        int initialY = headCoord.getRow();

        if(orientation == IShip.Orientation.HORIZONTAL){
            for(int i = 0; i < size; i++){
                if(grid.get(initialX + i).get(initialY).getState() == Cell.CellState.SHIP){
                    return false;
                }
            }
            return true;
        }

        if(orientation == IShip.Orientation.VERTICAL){
            for(int i = 0; i < size; i++){
                if(grid.get(initialX).get(initialY + i).getState() == Cell.CellState.SHIP){
                    return false;
                }
            }
            return true;
        }

        return false;
    }
}