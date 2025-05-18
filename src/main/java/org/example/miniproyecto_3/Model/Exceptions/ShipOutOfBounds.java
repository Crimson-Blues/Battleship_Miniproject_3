package org.example.miniproyecto_3.Model.Exceptions;

public class ShipOutOfBounds extends Exception {
    public ShipOutOfBounds(){
        super();
    }

    public ShipOutOfBounds(String msg){
        super(msg);
    }

    public ShipOutOfBounds(String msg, Throwable cause){
        super(msg, cause);
    }

    public ShipOutOfBounds(Throwable cause){
        super(cause);
    }
}
