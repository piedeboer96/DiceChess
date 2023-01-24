package simulation;

import player.*;
import simulation.GraphicalSimulator;
import simulation.Player;

import java.io.FileNotFoundException;
import java.io.IOException;

public class GeneticVsExpecti {
    public static void main(String[] args) throws IOException {
        var simulator = new GraphicalSimulator(1000);
        Player p1 = new Gambler();
        Player p2 = new MinimizingNeural();
        simulator.simulate(p1, p2);
    }
}
