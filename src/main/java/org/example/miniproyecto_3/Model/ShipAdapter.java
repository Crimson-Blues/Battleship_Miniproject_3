package org.example.miniproyecto_3.Model;

public abstract class ShipAdapter implements IShip {
    private boolean state;

    public ShipAdapter(){
        this.state = true;
    }

    @Override
    public boolean isHit() {
        return !state;
    }

    @Override
    public void getHit() {
        this.state = false;
    }
}
