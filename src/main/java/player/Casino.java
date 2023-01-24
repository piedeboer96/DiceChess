package player;

import game.DiceChess;
import learningagent.deep4j.NeuralNetwork;
import montecarlo.MCTS;
import simulation.Player;

import java.io.IOException;

public class Casino implements Player {

    NeuralNetwork nn = new NeuralNetwork();

    private final MCTS HEURISTIC = new MCTS(nn, 10000, 100);
    public String play(int roll, DiceChess game) throws IOException {
        return HEURISTIC.solve(roll, game);
    }


}
