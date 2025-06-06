package org.example.miniproyecto_3.Model.Exceptions;

/**
 * Exception thrown when an attempt is made to fire at a cell
 * that cannot be shot, that is one that has already been hit.
 * <p>
 * This exception helps enforce game rules to prevent invalid actions.
 * </p>
 */
public class NonShootableCell extends Exception {
    /**
     * Constructs a new {@code NonShootableCell} exception with no detail message.
     */
    public NonShootableCell() {
        super();
    }
    /**
     * Constructs a new {@code NonShootableCell} exception with the specified detail message.
     *
     * @param msg the detail message
     */
    public NonShootableCell(String msg){
        super(msg);
    }

    /**
     * Constructs a new {@code NonShootableCell} exception with the specified detail message and cause.
     *
     * @param msg   the detail message
     * @param cause the cause of the exception
     */
    public NonShootableCell(String msg, Throwable cause){
        super(msg, cause);
    }

    /**
     * Constructs a new {@code NonShootableCell} exception with the specified cause.
     *
     * @param cause the cause of the exception
     */
    public NonShootableCell(Throwable cause){
        super(cause);
    }
}
