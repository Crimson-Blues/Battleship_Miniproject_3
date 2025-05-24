package org.example.miniproyecto_3.Model;

import org.example.miniproyecto_3.Model.Exceptions.NonShootableCell;

import java.io.Serializable;

public class Game implements Serializable {
    public enum Turn {
        PLAYER, MACHINE
    }
    private final Board playerBoard;
    private final Board machineBoard;
    private final Machine machine;
    private Turn turn;
    private final Player player;

    public Game() {
        this.playerBoard = new Board();
        this.machineBoard = new Board();
        this.machine = new Machine();
        // La máquina coloca sus barcos en su tablero
        this.machine.placeShips(machineBoard);
        this.turn = Turn.PLAYER;
        this.player = new Player();
    }

    public Board getPlayerBoard() {
        return playerBoard;
    }

    public Board getMachineBoard() {
        return machineBoard;
    }

    // Nuevo getter para la máquina, esencial para la jugabilidad desde el controlador
    public Machine getMachine() {
        return machine;
    }

    public boolean isPlayerTurn() {
        return (turn == Turn.PLAYER);
    }

    private void toggleTurn() {
        if (turn == Turn.PLAYER) {
            turn = Turn.MACHINE;
        } else if (turn == Turn.MACHINE) {
            turn = Turn.PLAYER;
        }
    }

    // Se dispara sobre el tablero indicado y se cambia de turno si el disparo resulta en MISS
    public Cell.CellState fire(Coordinate coord, Board board) throws NonShootableCell {

        Cell.CellState hit = board.fireAt(coord);
        if (hit == Cell.CellState.MISS) {
            toggleTurn();
        }

        return hit;

    }

    public Turn getTurn() {
        return turn;
    }

    // El juego se da por terminado si se han hundido todos los barcos de uno de los bandos
    public boolean isGameOver() {
        return playerBoard.shipsSunk() || machineBoard.shipsSunk();
    }

    // El jugador gana si se han hundido todos los barcos de la máquina
    public boolean playerWon() {
        return machineBoard.shipsSunk();
    }

    // La máquina gana si se han hundido todos los barcos del jugador
    public boolean machineWon() {
        return playerBoard.shipsSunk();
    }

    public Player getPlayer(){
        return player;
    }
}
