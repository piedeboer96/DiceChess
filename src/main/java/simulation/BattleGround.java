package simulation;
import player.*;

import java.io.*;

public class BattleGround {
    public static void main(String[] args) throws IOException {

//        var simulator = new GraphicalSimulator(100);
        var simulator = new DefaultSimulator();



        Player p1 =  new GamblerOld();                     // BLACK
        Player p2 =  new HappyBeam(1);       // WHITE

        int[] winCounts = new int[2];
        for (int i = 0; i < 500; i++) {
            Player winner = simulator.simulate(p1, p2);
            if (winner == p1) { winCounts[0]++; }
            else { winCounts[1]++; }

        }
        File newSample = new File("src/main/java/expecti/FINAL-BeamSearchvsExpectiOld.txt");
        FileWriter fileWriter = new FileWriter(newSample);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.print("ExpectiOld: "+winCounts[0] + "\n");
        printWriter.print("Beam-Search: "+winCounts[1]);
        printWriter.close();



         p1 =  new Gambler150();                     // BLACK
         p2 =  new HappyBeam(1);       // WHITE

        winCounts = new int[2];
        for (int i = 0; i < 500; i++) {
            Player winner = simulator.simulate(p1, p2);
            if (winner == p1) { winCounts[0]++; }
            else { winCounts[1]++; }

        }
        newSample = new File("src/main/java/expecti/FINAL-BeamSearchvs150.txt");
        fileWriter = new FileWriter(newSample);
        printWriter = new PrintWriter(fileWriter);
        printWriter.print("Expecti150: "+winCounts[0] + "\n");
        printWriter.print("Beam-Search: "+winCounts[1]);
        printWriter.close();

        p1 =  new Gambler50();                     // BLACK
        p2 =  new HappyBeam(1);       // WHITE

        winCounts = new int[2];
        for (int i = 0; i < 500; i++) {
            Player winner = simulator.simulate(p1, p2);
            if (winner == p1) { winCounts[0]++; }
            else { winCounts[1]++; }

        }
        newSample = new File("src/main/java/expecti/FINAL-BeamSearchvs50.txt");
        fileWriter = new FileWriter(newSample);
        printWriter = new PrintWriter(fileWriter);
        printWriter.print("Expecti50: "+winCounts[0] + "\n");
        printWriter.print("Beam-Search: "+winCounts[1]);
        printWriter.close();
    }
}
