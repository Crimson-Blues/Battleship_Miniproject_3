package org.example.miniproyecto_3.Model;

import java.io.Serializable;

interface ICell extends Serializable {
    boolean hasShip();
    void getShot();
    boolean getState();
    void setState(boolean state);
    void setShipPart(ShipPart shipPart);
    ShipPart getShipPart();
}
