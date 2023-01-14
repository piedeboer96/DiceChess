package simulation;
import player.Darwin;
import player.Lunatic;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public final class botAVSbotBnoGUI {
    public static void main(String[] args) throws FileNotFoundException {
        var simulator = new DefaultSimulator();
        Player lub = new Lunatic();
        Player dar = new Darwin();
        double blackWins = 0, whiteWins = 0, tie = 0, index = 0;
        double [] save = new double[4];
        for (int i = 0; i < 1000; i++) {
            save[0] = whiteWins;
            save[1] = blackWins;
            save[2] = tie;
            save[3] = index;
            writeCSV(save, "GAvs_Random");
            Player winner = simulator.simulate(dar,lub);
            if (winner==dar){
                blackWins = blackWins+1;
                index = index + 1;
            } else if (winner==lub){
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
