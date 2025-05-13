package org.example.miniproyecto_3.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Ship extends ShipAdapter implements Serializable {
    private final int length;
    private Set<Coordinate> hits;

    public Ship(int length){
        if(length<= 0)
            throw new IllegalArgumentException("Error! La longitud del barco tiene que ser positiva");
        this.length = length;
        this.hits = new HashSet <Coordinate>();
    }

    public int getLength(){
        return length;
    }

    public Set<Coordinate> getHits(){
        return hits;
    }

    public boolean isSunk(){
        return hits.size() == length;
    }

    public void hit(Coordinate h){
        hits.add(h);
    }
}