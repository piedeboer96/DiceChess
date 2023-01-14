package utility;
import player.Lunatic;
import simulation.DefaultSimulator;
import simulation.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public final class AvsBNOgui {
    public static void main(String[] args) throws FileNotFoundException {
        var simulator = new DefaultSimulator();
        Player lub = new Lunatic();
        Player dardiechess = new DarwinDieChess();
        Player darchess = new DarwinChess();
        double blackWins = 0, whiteWins = 0, tie = 0, index = 0;
        double [] save = new double[4];
        for (int i = 0; i < 1000; i++) {
            save[0] = blackWins;
            save[1] = whiteWins;
            save[2] = tie;
            save[3] = index;
            writeCSV(save, "GAvs_Random");
            Player winner = simulator.simulate(dardiechess, darchess);
            if (winner==dardiechess){
                blackWins = blackWins+1;
                index = index + 1;
            } else if (winner==darchess){
                whiteWins = whiteWins+1;
                index = index + 1;
            } else {
                tie = tie+1;
                index = index + 1;
            }
        }
        System.out.println("Running:) ");
    }
    public static void writeCSV(double[] data, String file) {

        try (PrintWriter writer = new PrintWriter(new File(file))) {

            StringBuilder sb = new StringBuilder();

            for(int i = 0; i < data.length; i++){

                sb.append(data[i]);
                sb.append('\n');

            }
            writer.write(sb.toString());
        }

        catch (FileNotFoundException e) {

            System.out.println(e.getMessage());
        }
    }
}
