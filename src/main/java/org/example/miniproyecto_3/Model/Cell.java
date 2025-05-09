package org.example.miniproyecto_3.Model;

public class Cell implements ICell {
    ShipPart shipPart;
    boolean state;

    public Cell() {
        shipPart = null;
        state = true;
    }

    @Override
    public boolean hasShip() {
        return shipPart != null;
    }

    @Override
    public void getShot() {
        shipPart.getHit();
        state = false;
    }

    @Override
    public boolean getState() {
        return state;
    }

    @Override
    public void setState(boolean state) {
        this.state = state;
    }

    @Override
    public void setShipPart(ShipPart shipPart) {
        this.shipPart = shipPart;
    }

    @Override
    public ShipPart getShipPart() {
        return shipPart;
    }

}
