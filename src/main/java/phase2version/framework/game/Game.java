package phase2version.framework.game;

import phase2version.framework.chess.Opportunities;
import phase2version.framework.chess.Permissions;
import phase2version.framework.utility.Die;
import phase2version.framework.utility.Recordable;
import phase2version.framework.utility.Recoverable;

public interface Game extends Die, Recordable, Recoverable {
    /**
     * Gets the team whose turn to play has arrived.
     * @return An integer that is either 0 [team black] or 1 [team white].
     **/
    int getActiveTeam();

    /**
     * Gets the number of full moves that have been registered in the current state of the game.
     * @return An integer >= 1.
     **/
    int getFullMoves();

    /**
     * Gets the number of half moves that have been registered in the current state of the game.
     * @return An integer >= 0.
     **/
    int getHalfMoves();

    /**
     * Moves a unit from one location to another location.
     * @param from The location occupied by a unit.
     * @param to   The destination the unit is moving to.
     * @exception NullPointerException Thrown when null is provided as a location to read,
     *                                 or as a destination to move to.
     * @exception IllegalArgumentException Thrown when the read location does not yield to a unit.
     **/
    void move(Location from, Location to);

    /**
     * Obtains the en-passant opportunities the current state of the game has.
     * @return The en-passant opportunities for both players.
     **/
    Opportunities opportunities();

    /**
     * Obtains the castle permissions recorded in the game.
     * @return The castle permissions for both players.
     **/
    Permissions permissions();

    /**
     * Promotes a unit to another unit.
     * @param from The location occupied by a unit.
     * @param to   The new unit that will occupy the location in the old unit's stead.
     * @exception NullPointerException Thrown when null is provided for either the read location or
     *                                 the substituting unit.
     * @exception IllegalArgumentException Thrown when the read location does not yield a unit.
     **/
    void promote(Location from, Unit to);

    /**
     * Obtains the unit setup the game has.
     * @return The setup describing the game "world" state.
     **/
    Setup world();
}
