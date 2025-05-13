package org.example.miniproyecto_3.Model;

import javafx.scene.layout.Pane;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Ship extends ShipAdapter implements Serializable {
    private int length;
    private Set<Coordinate> hits;
    private Pane view;

    public Ship(int length, Pane view){
        try{
            if(length<= 0)
                throw new IllegalArgumentException("La longitud del barco tiene que ser positiva");
            this.length = length;
            this.hits = new HashSet <Coordinate>();
            this.view = view;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

    }

    public Pane getPane() {
        return view;
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