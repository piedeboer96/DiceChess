package framework.game;

import framework.chess.Opportunities;
import framework.chess.Permissions;

/**
 * Defines a game object that can be controlled by a player.
 **/
public interface Unit {
    /**
     * Obtains the destinations the unit can reach from a location given the circumstances described by a setup.
     * @param from The location where the unit should obtain its destinations from.
     * @param s The setup describing the circumstances the unit is in.
     * @return An array of locations with a size >= 0.
     * @exception NullPointerException Thrown when null is provided as either the location to read from or
     *                                 the setup describing the circumstances.
     **/
    Location[] destinations(Location from, Setup s);

    /**
     * Obtains the destinations the unit can reach from a location given the circumstances described by a setup.
     * @param from The location where the unit should obtain its destinations from.
     * @param s The setup describing the circumstances the unit is in.
     * @param o The opportunities the unit should consider in its determination of the destinations.
     * @return An array of locations with a size >= 0.
     * @exception UnsupportedOperationException Thrown when the unit type does not support considering opportunities.
     * @exception NullPointerException Thrown when null is provided as either the location to read from,
     *                                 the setup describing the circumstances or the opportunities to consider.
     **/
    Location[] destinations(Location from, Setup s, Opportunities o);

    /**
     * Obtains the destinations the unit can reach from a location given the circumstances described by a setup.
     * @param from The location where the unit should obtain its destinations from.
     * @param s The setup describing the circumstances the unit is in.
     * @param p The permissions the unit should consider in its determination of the destinations.
     * @return An array of locations with a size >= 0.
     * @exception UnsupportedOperationException Thrown when the unit type does not support considering permissions.
     * @exception NullPointerException Thrown when null is provided as either the location to read from,
     *                                 the setup describing the circumstances or the permissions to consider.
     **/
    Location[] destinations(Location from, Setup s, Permissions p);

    /**
     * Gets the notation that describes the unit type in combination with the team the unit belongs to.
     * @return An agreed character that describes both the unit type and the team unit in one.
     **/
    char getNotation();

    /**
     * Gets the team the unit belongs to.describes
     * @return An integer that is either 0 [team black] or 1 [team white].
     **/
    int getTeam();

    /**
     * Gets the integer that is mapped to the unit type.
     * @return An integer between 1 and 6.
     **/
    int getType();
}
