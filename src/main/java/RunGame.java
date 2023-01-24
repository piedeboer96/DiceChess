import player.*;
import simulation.GraphicalSimulator;
import simulation.Player;

import java.io.FileNotFoundException;
import java.io.IOException;

public final class RunGame {
    public static void main(String[] args) throws IOException {
        Player[] AI = new Player[7];
        AI[0] = new Casino();                   // Monte-Carlo Tree Search
        AI[1] = new Darwin();                   // Min-Max Algorithm with Genetic Integration for Evaluation
        AI[2] = new Gambler();                  // Expected Min-Max
        AI[3] = new HappyBeam(1);    // Beam Search
        AI[4] = new Lunatic();                  // Random
        AI[5] = new MinimizingNeural();         // Neural Network
        AI[6] = new AgentQ2();                  // Reinforcement Agent

        Human h = new Human();
        GraphicalSimulator s = new GraphicalSimulator();
        s.simulate(AI[2], h);
    }
}
