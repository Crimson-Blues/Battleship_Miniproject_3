package org.example.miniproyecto_3.Model;

/**
 * Abstract adapter class for ships in the game.
 * <p>
 * Provides default implementations for common ship behaviors such as orientation handling
 * and sunk state. Subclasses are expected to override methods like {@link #isSunk()} as needed.
 * </p>
 *
 * <p>This class implements the {@link IShip} interface and is extended
 * by concrete ship classes.</p>
 */
public abstract class ShipAdapter implements IShip {
    /**
     * The sinking state of the ship.
     */
    protected State state;

    /**
     * The orientation of the ship (horizontal or vertical).
     */
    protected Orientation orientation;

    /**
     * Constructs a new ship with default orientation ({@link Orientation#HORIZONTAL})
     * and state ({@link State#NOT_SUNK}).
     */
    public ShipAdapter(){

        this.state = State.NOT_SUNK;
        this.orientation = Orientation.HORIZONTAL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Orientation getOrientation() {
        return orientation;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void flip(){
        System.out.println("Rotando barco");

        if(orientation == Orientation.HORIZONTAL){
            orientation = Orientation.VERTICAL;
        }
        else if (orientation == Orientation.VERTICAL){
            orientation = Orientation.HORIZONTAL;}
    }
    /**
     * Returns whether the ship has been sunk.
     * <p>
     * By default, this implementation always returns {@code false}.
     * Subclasses should override this method to provide actual sinking logic.
     * </p>
     *
     * @return {@code false} always, unless overridden
     */
    @Override
    public boolean isSunk() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSunk(State state) {
        this.state = state;
    }

}

