package org.example.miniproyecto_3.Model;

import java.io.Serializable;

public class Game implements Serializable {
    public enum Turn{
        PLAYER, MACHINE
    }
        private final Board playerBoard;
        private final Board machineBoard;
        private final Machine machine;
        private Turn turn;

        public Game() {
            this.playerBoard = new Board();
            this.machineBoard = new Board();
            this.machine = new Machine();
            //this.machine.placeShips(machineBoard);
            this.turn = Turn.PLAYER;
        }

        public Board getPlayerBoard() {
            return playerBoard;
        }

        public Board getMachineBoard() {
            return machineBoard;
        }

        public boolean isPlayerTurn() {
            return(turn == Turn.PLAYER);
        }

        private void toggleTurn() {
            if (turn == Turn.PLAYER) {
                turn = Turn.MACHINE;
            } else if (turn == Turn.MACHINE) {
                turn = Turn.PLAYER;
            }
        }

        public void fire(Coordinate coord, Board board) {
            Cell.CellState hit = board.fireAt(coord);

            if(hit == Cell.CellState.MISS){
                toggleTurn();
            }
        }

        public Turn getTurn() {
            return turn;
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

