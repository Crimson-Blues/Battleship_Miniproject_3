package org.example.miniproyecto_3.Model.Exceptions;

/**
 * RuntimeException thrown when a ship is attempted to be placed
 * on a board cell that is already occupied by another ship.
 * <p>
 * This exception helps enforce placement rules in the game logic.
 * </p>
 */
public class OverlappingShip extends RuntimeException {
    /**
     * Constructs a new {@code OverlappingShip} exception with no detail message.
     */
    public OverlappingShip(){
        super();
    }

    /**
     * Constructs a new {@code OverlappingShip} exception with the specified detail message.
     *
     * @param msg the detail message
     */
    public OverlappingShip(String msg){
        super(msg);
    }

    /**
     * Constructs a new {@code OverlappingShip} exception with the specified detail message and cause.
     *
     * @param msg   the detail message
     * @param cause the cause of the exception
     */
    public OverlappingShip(String msg, Throwable cause){
        super(msg, cause);
    }

    /**
     * Constructs a new {@code OverlappingShip} exception with the specified cause.
     *
     * @param cause the cause of the exception
     */
    public OverlappingShip(Throwable cause){
        super(cause);
    }
}
