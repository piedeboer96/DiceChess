package ai.geneticAI;
import chess.ChessMatch;
import chess.interfaces.IChessMove;
import gui.die.Die;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;



public class TrainBots {

    static List<Bot> bots; // chromosomes
    static final int population = 20; // population
    static final int generations = 100; // generation
    static final double mutateFactor = 0.007; // mutation factor
    static int gameNumber = 1;



    /**
     *
     */
    private static void train() {
        for(int i = 0; i < generations; i++) {
            nextGeneration();
        }
    }



    /**
     *
     */
    static void nextGeneration() {
        selection();
        crossover();
        mutation();
    }



    /**
     *
     */
    private static void selection() {
        bots = new ArrayList<>();
        for(int x =0; x < population; x++){
            bots.add(new Bot());
        }
        for (int i = 0; i < bots.size(); i++){
            for(int j = i + 1; j < bots.size(); j++){
                if(i != j) {
                    matchBots(bots.get(i), bots.get(j)).wins++;
                }
            }
        }
        // sort bots with the highest value as first ( Decreasing order) via Comparable
        Collections.sort(bots);
    }



    /**
     * @param bot1 first player (Black player)
     * @param bot2 second player (White player)
     * @return the winner between these two players
     */
    private static Bot matchBots(Bot bot1, Bot bot2) {
        String startPos = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        ChessMatch match = new ChessMatch(startPos);
        match.loadKings();
        Bot[] bots = {bot1, bot2};
        Die die = new Die();
        int iterations=0;
        while (iterations++ < 250) {
            char rollOne = die.roll(match.getPlayer());
            char rollTwo = die.roll(match.getPlayer());
            IChessMove decision = bots[match.getPlayer()].bestMove(match, rollOne, rollTwo);
            if(decision == null){
                continue;
            }
            if(decision.owner().team() == match.getPlayer()) {
                match.playMove(decision.owner(), decision.possibilities().get(0));
            }
        }
        System.out.println("GAME NUMBER "+ gameNumber++ +" IS OVER!");
        if (match.getPlayer() == bots[0].currentPlayer){
            System.out.println("Black bot won");
            return bots[0];}
        else if(match.getPlayer() == bots[1].currentPlayer) {
            System.out.println("White bot won");
            return bots[1];}
        else {
            System.out.println(" there is no winner!");
            return bots[0];
        }
    }



    /**
     *
     */
    static void crossover() {
        Random random = new Random();
        for (int i=0; i <= 9; i++){
            Bot first = bots.get(random.nextInt(10));
            Bot second = bots.get(random.nextInt(10));
            if (first != second) {
                first.chromosome.crossoverWith(second.chromosome);
            }
        }
    }




    /**
     *
     */
    static void mutation() {
        for(Bot bot : bots) {
            bot.chromosome.mutate(mutateFactor);
        }
    }



    /**
     *
     * @return
     */
    public static Bot bestBot() {
        bots = new ArrayList<>();
        for(int x =0; x < population; x++) {
            bots.add(new Bot());
        }
            for (int i = 0; i < bots.size(); i++) {
                for (int j = i + 1; j < bots.size(); j++) {
                    if (i != j) {
                        matchBots(bots.get(i), bots.get(j)).wins++;
                    }
                }
            }
        Bot max = Collections.max(bots);
        int bestBot = bots.indexOf(max);
        Bot bot = bots.get(bestBot);
        return bot;
    }



    /**
     * @param args
     */
    // let the bots play against each other!
    public static void main(String[] args) throws IOException {

        train();

        File file = new File("bestChromosomeData.txt");
        FileWriter fileWriter = new FileWriter(file);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.println(bestBot().chromosome.toString());
        printWriter.close();
    }



}
