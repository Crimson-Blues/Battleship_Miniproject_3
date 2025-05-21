package org.example.miniproyecto_3.Model;

import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Ship extends ShipAdapter implements Serializable {
    private int length;
    private Set<Coordinate> hits;

    private Coordinate headCoord;

    public Ship(int length){
        try{
            if(length<= 0)
                throw new IllegalArgumentException("La longitud del barco tiene que ser positiva");
            this.length = length;
            this.hits = new HashSet <Coordinate>();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

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

    public void setHeadCoord(Coordinate h){
        headCoord = h;
    }

    public Coordinate getHeadCoord(){
        return headCoord;
    }

    @Override
    public void flip(){
        System.out.println("Rotando barco");

        if(orientation == Orientation.HORIZONTAL){
            orientation = Orientation.VERTICAL;
        }
        else if (orientation == Orientation.VERTICAL){
            orientation = Orientation.HORIZONTAL;}


    }

    public void setOrientation(Orientation o){
        orientation = o;
        if(orientation == Orientation.HORIZONTAL){
        } else if(orientation == Orientation.VERTICAL){
        }
    }
}