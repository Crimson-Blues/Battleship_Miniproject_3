package org.example.miniproyecto_3.Model;

import java.io.Serializable;
import java.util.*;

public class Machine implements Serializable {

    private final Random random = new Random();
    private final Set<Coordinate> usedShots = new HashSet<>();

    public void placeShips(Board board) {
        int[] sizes = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1}; // portaaviones, submarinos, destructores, fragatas
        for (int size : sizes) {
            boolean placed = false;
            while (!placed) {
                int row = random.nextInt(10);
                int col = random.nextInt(10);
                boolean horizontal = random.nextBoolean();

                Ship ship = new Ship(size);
                List<Coordinate> coords = new ArrayList<Coordinate>();
                board.placeShip(ship, coords);
            }
        }
    }

    public Coordinate selectTarget(Board playerBoard) {
        Coordinate target;
        do {
            int row = random.nextInt(10);
            int col = random.nextInt(10);
            target = new Coordinate(row, col);
        } while (usedShots.contains(target) || !playerBoard.canShoot(target));

        usedShots.add(target);
        return target;
    }
}
