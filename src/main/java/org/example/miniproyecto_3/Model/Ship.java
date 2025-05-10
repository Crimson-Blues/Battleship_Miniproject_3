package org.example.miniproyecto_3.Model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Ship {
    private final int length;
    private ArrayList<Coordinate>positions;
    private Set<Coordinate>hits;

    public Ship(int length){
        if(length<= 0)
            throw new IllegalArgumentException("Error! La longitud del barco tiene que ser positiva");
        this.length = length;
        this.hits = new HashSet <Coordinate>();
    }

    public int getLength(){
        return length;
    }

    public void setCoodinate(ArrayList<Coordinate> pos){
        if (pos == null || pos.size() != length)
            throw new IllegalArgumentException("Error! Ingrese una lista de coordenadas correcta");
        positions = pos;
    }

    public ArrayList<Coordinate>getPositions(){
        return positions;
    }
    public Set<Coordinate>getHits(){
        return hits;
    }

    public boolean contains(Coordinate coor){
        return positions.contains(coor);
    }

    public boolean isSunk(){
        return hits.size() == length;
    }

    public void hit(Coordinate h){
        if(!contains(h)){
            throw new IllegalArgumentException("Error! No puedes hundir una posicion que no le pertenece al barco");
        }
        hits.add(h);
    }
}