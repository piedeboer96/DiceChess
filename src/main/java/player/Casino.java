package player;

import game.DiceChess;
import montecarlo.MonteCarloTreeSearch;
import simulation.Player;

import java.io.IOException;

public class Casino implements Player {

    private final MonteCarloTreeSearch HEURISTIC = new MonteCarloTreeSearch();

    @Override
    public String play(int roll, DiceChess game) throws IOException {
        return HEURISTIC.solve(roll, game);
    }
}
