package org.example.miniproyecto_3.Model;

import javafx.scene.layout.Pane;
import org.example.miniproyecto_3.Model.Exceptions.OverlappingShip;
import org.example.miniproyecto_3.View.Assets.ShipDrawer;

import java.io.Serializable;
import java.util.*;

public class Machine implements Serializable {
    private final Random random = new Random();

    // Métodos públicos ya existentes: placeShips y selectTarget.

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

