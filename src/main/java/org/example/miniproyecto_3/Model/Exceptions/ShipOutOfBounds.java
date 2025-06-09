package org.example.miniproyecto_3.Model.Exceptions;

/**
 * Exception thrown when a ship placement goes outside the boundaries of the board.
 * <p>
 * This exception helps to ensure ships are placed within valid coordinates.
 * </p>
 */
public class ShipOutOfBounds extends Exception {
    /**
     * Constructs a new {@code ShipOutOfBounds} exception with no detail message.
     */
    public ShipOutOfBounds(){
        super();
    }

    /**
     * Constructs a new {@code ShipOutOfBounds} exception with the specified detail message.
     *
     * @param msg the detail message
     */
    public ShipOutOfBounds(String msg){
        super(msg);
    }

    /**
     * Constructs a new {@code ShipOutOfBounds} exception with the specified detail message and cause.
     *
     * @param msg   the detail message
     * @param cause the cause of the exception
     */
    public ShipOutOfBounds(String msg, Throwable cause){
        super(msg, cause);
    }

    /**
     * Constructs a new {@code ShipOutOfBounds} exception with the specified cause.
     *
     * @param cause the cause of the exception
     */
    public ShipOutOfBounds(Throwable cause){
        super(cause);
    }
}
