package org.example.miniproyecto_3.Model.Exceptions;

/**
 * Exception thrown to indicate that an attempt was made to use a board
 * that is incomplete that is where not all ships have been placed.
 * <p>
 * This exception is used to signal issues during game initialization
 * or validation steps before starting gameplay.
 * </p>
 */
public class IncompleteBoard extends Exception {
    /**
     * Constructs a new {@code IncompleteBoard} exception with no detail message.
     */
    public IncompleteBoard() {
        super();
    }

    /**
     * Constructs a new {@code IncompleteBoard} exception with the specified detail message.
     *
     * @param msg the detail message
     */
    public IncompleteBoard(String msg) {
        super(msg);
    }

    /**
     * Constructs a new {@code IncompleteBoard} exception with the specified detail message and cause.
     *
     * @param msg   the detail message
     * @param cause the cause of the exception
     */
    public IncompleteBoard(String msg, Throwable cause) {
        super(msg, cause);
    }

    /**
     * Constructs a new {@code IncompleteBoard} exception with the specified cause.
     *
     * @param cause the cause of the exception
     */
    public IncompleteBoard(Throwable cause) {
        super(cause);
    }

}
