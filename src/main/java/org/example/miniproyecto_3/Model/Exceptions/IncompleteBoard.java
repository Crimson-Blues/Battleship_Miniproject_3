package org.example.miniproyecto_3.Model.Exceptions;

public class IncompleteBoard extends Exception {

    public IncompleteBoard() {
        super();
    }

    public IncompleteBoard(String msg) {
        super(msg);
    }

    public IncompleteBoard(String msg, Throwable cause) {
        super(msg, cause);
    }

    public IncompleteBoard(Throwable cause) {
        super(cause);
    }

}
