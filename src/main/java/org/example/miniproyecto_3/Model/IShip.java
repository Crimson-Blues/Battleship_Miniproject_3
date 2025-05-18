package org.example.miniproyecto_3.Model;

import java.io.Serializable;

public interface IShip extends Serializable {
    public enum Orientation {
        HORIZONTAL, VERTICAL
    }
    public enum State {
        SUNK, NOT_SUNK
    }
    boolean isSunk();
    void setSunk(State state);
    Orientation getOrientation();
    void flip();
}
