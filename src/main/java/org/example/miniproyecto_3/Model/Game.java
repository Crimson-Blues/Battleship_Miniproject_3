package org.example.miniproyecto_3.Model;

import java.io.Serializable;

public class Game implements Serializable {

        private final Board playerBoard;
        private final Board machineBoard;
        private final Machine machine;
        private boolean playerTurn;

        public Game() {
            this.playerBoard = new Board();
            this.machineBoard = new Board();
            this.machine = new Machine();
            this.machine.placeShips(machineBoard);
            this.playerTurn = true; // El jugador humano empieza
        }

        public Board getPlayerBoard() {
            return playerBoard;
        }

        public Board getMachineBoard() {
            return machineBoard;
        }

        public boolean isPlayerTurn() {
            return playerTurn;
        }

        private void toggleTurn() {
            playerTurn = !playerTurn;
        }

        /**
         * Disparo del jugador humano al tablero de la máquina.
         * @param coor coordenada objetivo
         * @return true si fue tocado, false si fue agua
         */
        public boolean fireAtMachine(Coordinate coor) {
            if (!playerTurn || !machineBoard.canShot(coor)) return false;

            boolean hit = machineBoard.fireAt(coor);

            if (!hit) {
                toggleTurn(); // pasa turno si fue agua
            }

            return hit;
        }

        /**
         * Disparo de la máquina al tablero del jugador.
         * return true si fue tocado, false si fue agua
         */
        public boolean fireAtPlayer() {
            if (playerTurn) return false; // No debe disparar si no es su turno

            Coordinate target = machine.selectTarget(playerBoard);
            boolean hit = playerBoard.fireAt(target);

            if (!hit) {
                toggleTurn(); // pasa turno si fue agua
            }

            return hit;
        }

        public boolean isGameOver() {
            return playerBoard.shipsSunk() || machineBoard.shipsSunk();
        }

        public boolean playerWon() {
            return machineBoard.shipsSunk();
        }

        public boolean machineWon() {
            return playerBoard.shipsSunk();
        }
    }

