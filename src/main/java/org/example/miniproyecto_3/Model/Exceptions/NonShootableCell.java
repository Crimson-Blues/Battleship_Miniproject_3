package org.example.miniproyecto_3.Model.Exceptions;

public class NonShootableCell extends Exception {
    public NonShootableCell() {
        super();
    }

    public NonShootableCell(String msg){
        super(msg);
    }

    public NonShootableCell(String msg, Throwable cause){
        super(msg, cause);
    }

    public NonShootableCell(Throwable cause){
        super(cause);
    }
}
