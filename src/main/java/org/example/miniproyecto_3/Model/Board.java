package org.example.miniproyecto_3.Model;

import java.util.ArrayList;

public class Board {
    public enum CellState {
        EMPTY,
        SHIP,
        HIT,
        MISS
    }
    private final int SIZE = 10;
    private ArrayList<ArrayList<CellState>>grid;
    private ArrayList<Ship>ships;

    public Board(){
        grid = new  ArrayList<ArrayList<CellState>>(SIZE);
        ships = new ArrayList<Ship>();
        for(int i = 0; i < SIZE; i++){
            //Agrega el arraylist de cellState
            grid.add(new ArrayList <CellState>(SIZE));

            for (int j = 0; j < SIZE; j++) {
                grid.get(i).add(CellState.EMPTY);
            }
        }

    }

    private boolean isValidCoordinate(Coordinate coord){
        int row = coord.getRow();
        int column = coord.getCol();

        return row >= 0 && row <SIZE && column >=0 && column < SIZE;
    }

    public boolean shipsSunk(){
        for(Ship ship : ships){
            if(!ship.isSunk()){
                return false;
            }
        }
        return true;
    }

    public boolean canShot(){

    }


}