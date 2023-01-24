package simulation;
import player.*;

import javax.swing.*;
import java.io.IOException;

public class BattleGround {
    public static void main(String[] args) throws IOException {

       //var simulator = new GraphicalSimulator(500);
        var simulator = new DefaultSimulator();

        Player p1 =   new MinimizingNeural();      // BLACK
        Player p2 =   new AgentQ2();      // WHITE

        int[] winCounts = new int[2];
        for (int i = 0; i < 25; i++) {
            Player winner = simulator.simulate(p1, p2);
            if (winner == p1) { winCounts[0]++; }
            else { winCounts[1]++; }

            System.out.println("Black won: " + winCounts[0] + " white won: " + winCounts[1]);
        }
        System.out.println("Black won: " + winCounts[0]);
        System.out.println("White won: " + winCounts[1]);
    }




}
