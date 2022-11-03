package phase2version.framework.game;

/**
 * Defines that which is in charge of controlling units and their displacements.
 **/
public interface Player {
    /**
     * Lets the player join a team in a game.
     * @param team The team the player should belong to. <br>
     *             This should be either 0 [team black] or 1 [team white].
     * @param g The game the player should play.
     * @exception IllegalArgumentException Thrown when team is neither 0 [team black] nor 1 [team white].
     * @exception NullPointerException Thrown when null is provided as a game.
     **/
    void join(int team, Game g);

    /**
     * Lets the player leave the team and the game.
     **/
    void leave();

    /**
     * Informs the player to make his/her/its move.
     **/
    void play();
}
