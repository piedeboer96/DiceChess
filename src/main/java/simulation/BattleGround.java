package simulation;
import player.*;

import java.io.FileNotFoundException;

public class BattleGround {
    public static void main(String[] args) throws FileNotFoundException {

//        var simulator = new GraphicalSimulator(100);
        var simulator = new DefaultSimulator();

        Player p1 =  new Lunatic();                    // BLACK
        Player p2 =  new CasinoNeural();               // WHITE

        int[] winCounts = new int[2];
        for (int i = 0; i < 100; i++) {
            Player winner = simulator.simulate(p1, p2);
            if (winner == p1) { winCounts[0]++; }
            else { winCounts[1]++; }

            System.out.println("Black won: " + winCounts[0] + " white won: " + winCounts[1]);
        }
        System.out.println("Black won: " + winCounts[0]);
        System.out.println("White won: " + winCounts[1]);
    }
}
