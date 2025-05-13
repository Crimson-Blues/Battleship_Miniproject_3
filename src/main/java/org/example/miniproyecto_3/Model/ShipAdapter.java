package org.example.miniproyecto_3.Model;

public abstract class ShipAdapter implements IShip {
    private State state;
    private Orientation orientation;

    public ShipAdapter(){
        this.state = State.NOT_SUNK;
    }

    @Override
    public Orientation getOrientation() {
        return orientation;
    }

    @Override
    public void flip(){
        if(orientation == Orientation.HORIZONTAL){
            orientation = Orientation.VERTICAL;
        }
        else if (orientation == Orientation.VERTICAL){
            orientation = Orientation.HORIZONTAL;}
    }

    @Override
    public boolean isSunk() {
        return false;
    }

    @Override
    public void setSunk(State state) {
        this.state = state;
    }
}

