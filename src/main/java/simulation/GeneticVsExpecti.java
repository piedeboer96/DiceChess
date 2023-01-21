package simulation;

import player.CasinoNeural;
import player.Darwin;
import player.Gambler;
import player.Human;
import simulation.GraphicalSimulator;
import simulation.Player;
import player.HappyBeam;

import java.io.FileNotFoundException;

public class GeneticVsExpecti {
    public static void main(String[] args) throws FileNotFoundException {
        var simulator = new GraphicalSimulator(1000);
        Player p1 = new Gambler();
        Player p2 = new Human();
        simulator.simulate(p1, p2);
    }
}
