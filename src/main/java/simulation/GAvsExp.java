package simulation;
import player.Gambler;

import java.io.FileNotFoundException;

public class GAvsExp{
    public static void main(String[] args) throws FileNotFoundException {
        var simulator = new GraphicalSimulator(1000);
        Player p1 = new Gambler();
        Player p2 = new DarwinChess();
        simulator.simulate(p1, p2);
    }
}
