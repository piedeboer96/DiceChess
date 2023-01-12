package player;

import game.DiceChess;
import montecarlo.MonteCarloTreeSearch;
import simulation.Player;

/**
 * Defines an AI that uses the Monte-Carlo Tree Search to play a game of dice chess.
 * @version 1.0
 **/
public final class Casino implements Player {
    private final MonteCarloTreeSearch HEURISTIC = new MonteCarloTreeSearch();
    @Override
    public String play(int roll, DiceChess game) {
        return HEURISTIC.solve(roll, game);
    }
}
