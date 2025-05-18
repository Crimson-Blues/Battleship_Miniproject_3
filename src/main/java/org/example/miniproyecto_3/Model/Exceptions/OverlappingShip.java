package org.example.miniproyecto_3.Model.Exceptions;

public class OverlappingShip extends RuntimeException {
    public OverlappingShip(){
        super();
    }

    public OverlappingShip(String msg){
        super(msg);
    }

    public OverlappingShip(String msg, Throwable cause){
        super(msg, cause);
    }

    public OverlappingShip(Throwable cause){
        super(cause);
    }
}
