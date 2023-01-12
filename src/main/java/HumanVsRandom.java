import player.Gambler;
import player.Human;
import player.Lunatic;
import simulation.GraphicalSimulator;
import simulation.Player;

public class HumanVsRandom {
    public static void main(String[] args) {
        var simulator = new GraphicalSimulator(1000);
        Player h = new Human();
        Player ai = new Lunatic();
        simulator.simulate(ai, h);
    }
}
