package hillclimbing;
import genetic.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import static genetic.Chromosome.bounds;

public class HillClimbing {
    private Bot bot = firstBot();

    int bestBot = 0;
    private double increaseRate;
    private int parameter = 0;

    private int direction = 1;

    public Bot firstBot() {
        double[] data = new double[bounds.length];
        for (int i = 0; i < bounds.length; i++) {
            data[i] = bounds[i][0] + (bounds[i][1] - bounds[i][0]) / 2;
        }
        return new Bot(new Chromosome(data));
    }

    public Bot newBot(boolean improved) {
        double[] oldData = bot.getChromosome().getData();
        double[] data = new double[bounds.length];
        System.arraycopy(oldData, 0, data, 0, bounds.length);
        if(improved) data[parameter] = nextParamValue(data[parameter]);
        return new Bot(new Chromosome(data));
    }

    public void newBestBot(boolean improved) {
        Bot newBot = newBot(improved);
        Trainer t = new Trainer();
        int games = 100;
        for (int i = 0; i < games; i++) {
            if (i < games / 2) t.matchBots(bot, newBot).wins++;
            else t.matchBots(newBot, bot).wins++;
        }
        System.out.println("Bot wins: " + bot.wins);
        System.out.println("NewBot wins: " + newBot.wins);
        List<Bot> bots = new ArrayList<>();
        bots.add(bot);
        bots.add(newBot);
        Bot max = Collections.max(bots);
        bestBot = bots.indexOf(max);
        bot = bots.get(bestBot);
        System.out.println("Therefore, the bot that has " + bot.wins + " wins is the current bot");
        bot.setWins(0);
    }

    public double nextParamValue(double currentParamValue) {
        return currentParamValue + (int) ((bounds[parameter][1] - bounds[parameter][0]) * increaseRate) * direction;
    }

    public void optimumWeight() {
        boolean[] directionCheck = {true, false};
        double[] data = bot.getChromosome().getData();
        boolean firstCheck = true;
        while((!directionCheck[0] || !directionCheck[1]) && nextParamValue(data[parameter]) >= bounds[parameter][0] && nextParamValue(data[parameter]) < bounds[parameter][1]) {
            // System.out.println("Since there is a direction that has not been explored (leftExplored: " + directionCheck[0] + ", rightExplored: " + directionCheck[1] + "), we find the newBestBot");
            // System.out.println("The next parameter value is: " + nextImprovedParamValue(data[parameter]) + ", and the current value is: " + data[parameter]);
            newBestBot(true);
            if(bestBot == 0) {
                if(direction == 1) {
                    directionCheck[1] = true;
                    direction = -1;
                    if(firstCheck) {
                        directionCheck[0] = false;
                    }
                } else {
                    directionCheck[0] = true;
                    direction = 1;
                }
            }
            data = bot.getChromosome().getData();
            if(firstCheck) firstCheck = false;
            // System.out.println("The next parameter value is: " + nextImprovedParamValue(data[parameter]));
        }
        System.out.println("The final parameter value is: " + data[parameter]);
    }

    public void trainImproved() {
        for (parameter = 0; parameter < bounds.length; parameter++) {
            optimumWeight();
            System.out.println("Parameter finished");
        }
    }

    public void trainSteps() {
        double[] increaseRates = {0.1, 0.05, 0.025, 0.0125};
        for (double rate : increaseRates) {
            increaseRate = rate;
            System.out.println("The increaseRate is: " + increaseRate);
            trainImproved();
        }
    }

    public static void main(String[] args) throws IOException {
        // match all bots with each other
        HillClimbing h = new HillClimbing();
        h.trainSteps();
        // play matches between bots from last generation and choose the best bot among them
        // write the best bot data into a file to use it later when initialising new best bot
        File file = new File("bestHillClimbingChromosomeData.txt");
        FileWriter fileWriter = new FileWriter(file);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.println(h.bot.getChromosome().toString());
        printWriter.close();
    }
}

