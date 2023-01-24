package player;

import expecti.ExpectiMiniMaxTree;
import game.DiceChess;
import simulation.Player;

/**
 * Defines an AI that uses the expected mini-max tree search to play a game of dice chess.
 **/
public final class Gambler implements Player {
    @Override
    public String play(int roll, DiceChess game) {
        ExpectiMiniMaxTree tree = new ExpectiMiniMaxTree(game.toString(), roll, 300);
        return tree.STATE;
    }
}
