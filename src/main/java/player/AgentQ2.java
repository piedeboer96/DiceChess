package player;

import game.DiceChess;
import genetic.Chromosome;
import learningagent.reinforcement.QLearningAgent;
import simulation.Player;

import java.io.IOException;


public class AgentQ2 implements Player {
    @Override
    public String play(int roll, DiceChess game) throws IOException {

        QLearningAgent agent = new QLearningAgent(new Chromosome());
        return agent.getAgentQMove(game, roll);
    }
}
