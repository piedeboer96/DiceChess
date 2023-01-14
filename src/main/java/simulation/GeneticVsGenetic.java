package simulation;
import player.Darwin;

import java.io.FileNotFoundException;

public class GeneticVsGenetic {
    public static void main(String[] args) throws FileNotFoundException {
        var simulator = new DefaultSimulator();
        Player p1 = new Darwin();
        Player p2 = new Darwin();
        int[] winCounts = new int[2];
        for (int i = 0; i < 10000; i++) {
            Player winner = simulator.simulate(p1, p2);
            if (winner == p1) { winCounts[0]++; }
            else { winCounts[1]++; }
        }
        System.out.println("Black won: " + winCounts[0]);
        System.out.println("White won: " + winCounts[1]);
    }
}
