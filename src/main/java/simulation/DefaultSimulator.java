package simulation;

import dice.Die;
import game.DiceChess;
import game.GameState;
import player.Human;

import java.io.IOException;

/**
 * Defines a simulator to run simulations without visualizing the simulation.
 * @version 1.0
 **/
public final class DefaultSimulator implements Simulator {
    @Override
    public Player simulate(Player p1, Player p2) throws IOException {
        if (p1 instanceof Human || p2 instanceof Human) {
            throw new IllegalArgumentException("Not allowed to do back-end simulation with a human player.");
        }
        Player[] players = {p1, p2};
        DiceChess game = new DiceChess();
        Die d = game.getDie();
        while (game.getState() == GameState.ONGOING) {
            String nextState = players[game.getActiveColor()].play(d.roll(), game);
            game = new DiceChess(nextState);
        }

//        System.out.println("game won by " + game.getState());
        return switch (game.getState()) {
            case DRAW      -> null;
            case BLACK_WON -> p1;
            case WHITE_WON -> p2;
            default        -> throw new IllegalStateException("Not supposed to here while the game has not ended.");
        };
    }
}
