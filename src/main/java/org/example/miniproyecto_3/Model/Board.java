package org.example.miniproyecto_3.Model;

import java.util.ArrayList;

public class Board {
    private Cell[][] board;
    private int width;
    private int height;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        board = new Cell[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                board[i][j] = new Cell();
            }
        }
    }

    public Cell getCell(int row, int col) {
        return board[row][col];
    }

    public boolean isAlive(){
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if(board[i][j].getState()){
                    return true;
                }

            }
        }

        return false;
    }
}
