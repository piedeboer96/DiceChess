import player.*;
import simulation.GraphicalSimulator;
import simulation.Player;

import java.io.FileNotFoundException;

public final class RunGame {
    public static void main(String[] args) throws FileNotFoundException {
        Player[] AI = new Player[8];
        AI[0] = new Casino();                   // Monte-Carlo Tree Search
        AI[1] = new CasinoDistanceWise();       // Monte-Carlo Tree Search Variant 1
        AI[2] = new CasinoNeural();             // Monte-Carlo Tree Search Variant 2
        AI[3] = new Darwin();                   // Min-Max Algorithm with Genetic Integration for Evaluation
        AI[4] = new Gambler();                  // Expected Min-Max
        AI[5] = new HappyBeam(1);    // Beam Search
        AI[6] = new Lunatic();                  // Random
        AI[7] = new MinimizingNeural();         // Neural Network

        Human h = new Human();
        GraphicalSimulator s = new GraphicalSimulator();
        s.simulate(AI[4], h);
    }
}
