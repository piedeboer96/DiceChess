package phase2version.framework.chess;

import phase2version.framework.utility.Recordable;

/**
 * Defines castle permissions.
 **/
public interface Permissions extends Recordable {
    /**
     * Obtains the castle permission a team has for a side.
     * @param team The team for which the permission should be obtained.
     *             This should be either 0 [team black] or 1 [team white].
     * @param side The side for which the permission should be obtained.
     *             This should be either 0 [king side] or 1 [queen side].
     * @return True, if the team is allowed to castle for the specified side. <br>
     *         False, otherwise.
     * @exception IllegalArgumentException Thrown when team is neither 0 [team black] nor 1 [team white]
     *                                     -or- when side is neither 0 [king side] nor 1 [queen side].
     **/
    boolean has(int team, int side);
}
