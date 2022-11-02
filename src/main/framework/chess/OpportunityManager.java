package framework.chess;

import framework.game.Location;

/**
 * Defines a manager to manage en-passant opportunities.
 **/
public interface OpportunityManager extends Opportunities {
    /**
     * Sets the target square a team has towards the opponent. In other words, it sets a location the opponent
     * is allowed to use to perform an en-passant.
     * @param team The team for which the target square will be set.
     *             This should be either 0 [team black] or 1 [team white].
     * @param targetSquare The target square the team has towards the opponent.
     *                     Null can be passed to clear the target square that a team has registered.
     * @exception IllegalArgumentException Thrown when team is neither 0 [team black] nor 1 [team white].
     **/
    void set(int team, Location targetSquare);
}
