package player;

import game.DiceChess;
import montecarlo.MctsNeural;
import montecarlo.MonteCarloTreeSearch;
import simulation.Player;

public class CasinoNeural implements Player {

    private final MctsNeural HEURISTIC = new MctsNeural();
    public String play(int roll, DiceChess game) {
        return HEURISTIC.solve(roll, game);
    }
}
