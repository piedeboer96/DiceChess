package player;

import game.DiceChess;
import montecarlo.MonteCarloTreeSearch;
import montecarlo.MonteCarloTreeSearchDistanceWise;
import simulation.Player;

/**
 * Defines an AI that uses the Monte-Carlo Tree Search to play a game of dice chess.
 * @version 1.0
 **/
public final class CasinoDistanceWise implements Player {
    private final MonteCarloTreeSearchDistanceWise HEURISTIC = new MonteCarloTreeSearchDistanceWise();
    @Override
    public String play(int roll, DiceChess game) {
        return HEURISTIC.solve(roll, game);
    }
}
