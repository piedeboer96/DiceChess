package zzz.framework.game;

/**
 * Defines how units moves through the coordinate system.
 **/
public interface Movement {
    /**
     * Applies the movement step on a location to obtain the movement step destination.
     * @param from The location from where the movement should step from.
     * @return The destination the movement results in by stepping from the location.
     * @exception NullPointerException Thrown when null is provided as a location to step from.
     * @exception IllegalArgumentException Thrown when step destinations falls outside the coordinate system.
     **/
    Location apply(Location from);

    /**
     * The delta column the movement step causes.
     * @return An integer between -7 and 7.
     **/
    int deltaColumn();

    /**
     * The delta row the movement step causes.
     * @return An integer between -7 and 7.
     **/
    int deltaRow();
}
