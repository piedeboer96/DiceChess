import player.Gambler;
import player.Human;
import simulation.GraphicalSimulator;
import simulation.Player;

public class HumanVsExpecti {
    public static void main(String[] args) {
        var simulator = new GraphicalSimulator(500);
        Player h = new Human();
        Player ai = new Gambler();
        simulator.simulate(ai, h);
    }
}
