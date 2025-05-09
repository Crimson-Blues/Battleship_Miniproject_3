package org.example.miniproyecto_3.Model;

import java.util.ArrayList;

public class Ship extends ShipAdapter {
    private int size;
    private ArrayList<ShipPart> shipParts;

    public Ship(int size){
        super();
        this.size = size;
        this.shipParts = new ArrayList<>();
        for(int i = 0; i < size; i++){
            shipParts.add(new ShipPart(this));
        }
    }

    public int getSize() {
        return size;
    }
}
