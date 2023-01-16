package learningagent;

import player.Casino;
import player.Darwin;
import player.Lunatic;
import player.MinimizingNeural;
import simulation.DefaultSimulator;
import simulation.GraphicalSimulator;
import simulation.Player;

import java.io.FileNotFoundException;

/**
 * Simple class to investigate the performance of the minimizing neural.
 */
public class MatchTime {


    // Setup players
    MinimizingNeural neural = new MinimizingNeural();

    public static void main(String[] args) throws FileNotFoundException {
        var simulator = new DefaultSimulator();

        Player p1 =   new MinimizingNeural();
        Player p2 =   new Lunatic();

        int iterations = 100;

        for (int i=0;i<iterations; i++){

            simulator.simulate(p1, p2);

        }



    }


}
