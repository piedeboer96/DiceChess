package phase2version.framework.chess;

import phase2version.framework.utility.Recoverable;

/**
 * Defines a manager to manage castle permissions.
 **/
public interface PermissionManager extends Permissions, Recoverable {
    /**
     * Sets the castle permission a team has for a side.
     * @param team The team for which the permission should be set.
     *             This should be either 0 [team black] or 1 [team white].
     * @param side The side for which the permission should be set.
     *             This should be either 0 [king side] or 1 [queen side].
     * @param b    The castle permission the team should have.
     * @exception IllegalArgumentException Thrown when team is neither 0 [team black] or 1 [team white]
     *                                     -or- when side is neither 0 [king side] or 1 [queen side].
     **/
    void set(int team, int side, boolean b);
}
