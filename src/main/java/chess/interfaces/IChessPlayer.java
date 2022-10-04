package chess.interfaces;

/**
 * Represents a chess player that is either a human or an AI.
 **/
public interface IChessPlayer
{
    /**
     * Lets the player make a play; Moves one chess piece to another square.
     **/
    void play();

    /**
     * Sets the team the player is in and the match the player is playing.
     * @param team The team the player is in.
     * @param match The match the player is playing.
     **/
    void playIn(int team, IChessMatch match);
}
