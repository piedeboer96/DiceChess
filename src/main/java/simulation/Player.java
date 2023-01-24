package simulation;

import game.DiceChess;

import java.io.IOException;

/**
 * Defines a game player.
 * @version 1.0
 **/
public interface Player {
    /**
     * Requests the player make a move.
     *
     * @param roll The die roll the player should use to determine his/her move.
     * @param game The game the player should play.
     * @return The next state of the game after the player has made a move.
     **/
    String play(int roll, DiceChess game) throws IOException;
}
