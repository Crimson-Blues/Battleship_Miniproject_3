package org.example.miniproyecto_3.Model;

public class ShipPart extends ShipAdapter {
    private Ship parentShip;

    public ShipPart() {
        super();
        this.parentShip = null;
    }

    public ShipPart(Ship ship) {
        super();
        this.parentShip = ship;
    }

    public Ship getParentShip() {
        return parentShip;
    }

    public void setParentShip(Ship ship) {
        this.parentShip = ship;
    }
}
