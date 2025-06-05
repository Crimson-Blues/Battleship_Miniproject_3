package org.example.miniproyecto_3.Model;

import javafx.scene.layout.Pane;
import org.example.miniproyecto_3.Model.Exceptions.OverlappingShip;
import org.example.miniproyecto_3.View.Assets.ShipDrawer;

import java.io.Serializable;
import java.util.*;

/**
 * Represents the machine (AI) opponent in the game.
 * <p>
 * The Machine is responsible for placing its ships randomly on the board
 * and selecting target coordinates to attack the player's board.
 * </p>
 */
public class Machine implements Serializable {
    /**
     * Random number generator for AI decisions.
     */
    private final Random random = new Random();

    /**
     * Places a predefined set of ships on the provided board.
     * <p>
     * The ships are placed at random locations and orientations,
     * ensuring they fit within the board and do not overlap.
     * </p>
     *
     * @param board the board where the ships will be placed
     */
    public void placeShips(Board board) {
        int[] sizes = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1}; // portaaviones, submarinos, destructores, fragatas
        int boardSize = board.getSize();

        for (int size : sizes) {
            Ship ship = new Ship(size); //Unplaced ship

            boolean placed = false;
            while (!placed) {
                int row = random.nextInt(boardSize);
                int col = random.nextInt(boardSize);
                boolean horizontal = random.nextBoolean();

                List<Coordinate> coords = new ArrayList<>();
                if (horizontal) {
                    ship.setOrientation(Ship.Orientation.HORIZONTAL);
                    // Verificar que el barco quepa horizontalmente
                    if (col + size <= boardSize) {
                        for (int i = 0; i < size; i++) {
                            coords.add(new Coordinate(row, col + i));
                        }
                    }
                } else {
                    ship.setOrientation(Ship.Orientation.VERTICAL);
                    // Verificar que el barco quepa verticalmente
                    if (row + size <= boardSize) {
                        for (int i = 0; i < size; i++) {
                            coords.add(new Coordinate(row + i, col));
                        }
                    }
                }

                int prevShipCount = board.getShips().size();
                try {
                    if(!board.canPlaceShip(size, coords.get(0), ship.getOrientation())){
                        throw new OverlappingShip("Se intentó colocar una nave sobre otra");
                    }
                    board.placeShip(ship, coords);

                    // Si el barco se colocó correctamente (sin solapamientos), se incrementa la cantidad
                    ship.setHeadCoord(new Coordinate(row, col));
                    placed = board.getShips().size() > prevShipCount;
                } catch (Exception e) {
                    // En caso de solapamiento u otro error, se reintenta
                }
            }
        }
    }

    /**
     * Selects a random coordinate on the given board to attack.
     * <p>
     * Ensures that the selected coordinate has not been previously attacked.
     * </p>
     *
     * @param playerBoard the player's board to target
     * @return a valid coordinate to attack
     */
    public Coordinate selectTarget(Board playerBoard) {
        int boardSize = playerBoard.getSize();
        Coordinate target;
        do {
            int row = random.nextInt(boardSize);
            int col = random.nextInt(boardSize);
            target = new Coordinate(row, col);
        } while (!playerBoard.canShoot(target));
        return target;
    }
}

