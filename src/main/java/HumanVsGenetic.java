import player.Darwin;
import player.Human;
import simulation.GraphicalSimulator;
import simulation.Player;

import java.io.FileNotFoundException;

public final class HumanVsGenetic {
    public static void main(String[] args) throws FileNotFoundException {
        var simulator = new GraphicalSimulator(1000);
        Player h = new Human();
        Player ai = new Darwin();
        simulator.simulate(ai, h);
    }
}
