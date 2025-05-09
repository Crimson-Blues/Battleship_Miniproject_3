package org.example.miniproyecto_3.Model;

import java.io.Serializable;

public interface ICell extends Serializable {
    public boolean hasShip();
    public void getShot();
    public boolean getState();
    public void setState(boolean state);
    public void setShipPart(ShipPart shipPart);
    public ShipPart getShipPart();
}
