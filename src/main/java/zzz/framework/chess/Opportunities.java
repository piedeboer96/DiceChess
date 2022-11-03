package zzz.framework.chess;

import zzz.framework.game.Location;

/**
 * Defines en-passant opportunities.
 **/
public interface Opportunities {
    /**
     * Gets the en-passant opportunities a team has.
     * @param team The team for which the opportunities are obtained. <br>
     *             This should be either 0 [team black] or 1 [team white].
     * @return The en-passant target square of the opponent. <br>
     *         Null, if the team has no opportunity to perform an en-passant.
     * @exception IllegalArgumentException Thrown when team is neither 0 [team black] nor 1 [team white]
     **/
    Location get(int team);
}
