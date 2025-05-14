package org.example.miniproyecto_3.Model;

import java.io.Serializable;
import java.util.*;

public class Machine implements Serializable {
    private final Random random = new Random();
    private final Set<Coordinate> usedShots = new HashSet<>();

    // Métodos públicos ya existentes: placeShips y selectTarget.

    public void placeShips(Board board) {
        int[] sizes = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1}; // portaaviones, submarinos, destructores, fragatas
        int boardSize = board.getSize();

        for (int size : sizes) {
            boolean placed = false;
            while (!placed) {
                int row = random.nextInt(boardSize);
                int col = random.nextInt(boardSize);
                boolean horizontal = random.nextBoolean();

                List<Coordinate> coords = new ArrayList<>();
                if (horizontal) {
                    // Verificar que el barco quepa horizontalmente
                    if (col + size <= boardSize) {
                        for (int i = 0; i < size; i++) {
                            coords.add(new Coordinate(row, col + i));
                        }
                    }
                } else {
                    // Verificar que el barco quepa verticalmente
                    if (row + size <= boardSize) {
                        for (int i = 0; i < size; i++) {
                            coords.add(new Coordinate(row + i, col));
                        }
                    }
                }
                if (coords.size() == size) {
                    int prevShipCount = board.getShips().size();
                    Ship ship = new Ship(size, null); // Se puede asignar representación gráfica si se tiene
                    try {
                        board.placeShip(ship, coords);
                        // Si el barco se colocó correctamente (sin solapamientos), se incrementa la cantidad
                        placed = board.getShips().size() > prevShipCount;
                    } catch (Exception e) {
                        // En caso de solapamiento u otro error, se reintenta
                    }
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
        } while (usedShots.contains(target) || !playerBoard.canShoot(target));
        usedShots.add(target);
        return target;
    }
}

