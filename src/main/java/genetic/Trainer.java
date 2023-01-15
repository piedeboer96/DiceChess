package genetic;

import game.ChessPiece;
import game.DiceChess;
import game.GameState;
import game.Movement;
import utility.Promotion;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static genetic.Chromosome.bounds;

public class Trainer {
    private static final double MUTATION_FACTOR = 0.007; // mutation factor
    private static int GENERATIONS = 200; // generation
    private static final int POPULATION = 20; // population
    private static final Random RANDOM = new Random();
    private List<Bot> bots = new ArrayList<>(POPULATION); // chromosomes
    private int gamesFinished = 0;
     public void train() {
        for(int i = 0; i < GENERATIONS; i++) {
            nextGeneration();
        }
     }

    private void nextGeneration() {
        selection();
        crossover();
        mutation();
    }

    private void selection() {
        if(bots.isEmpty()) {
            for (int x = 0; x < POPULATION; x++) {
                bots.add(new Bot());
            }
        }
        for (int i = 0; i < POPULATION; i++){
            for(int j = i + 1; j < POPULATION; j++){
                if(i != j) {
                    matchBots(bots.get(i), bots.get(j)).wins++;
                }
            }
        }
        // sort bots with the highest value as first ( Decreasing order) via Comparable
        Collections.sort(bots);
    }

    public Bot matchBots(Bot bot1, Bot bot2) {
        DiceChess game = new DiceChess();
        Bot[] bots = {bot1, bot2};
        while (game.getState() == GameState.ONGOING) {
            int dieRoll = RANDOM.nextInt(1, 7);
            bots[game.getActiveColor()].play(game, dieRoll);
        }
        System.out.println("Finished game " + gamesFinished++);

        return switch (game.getState()) {
            case BLACK_WON -> bot1;
            case WHITE_WON -> bot2;
            case DRAW -> bots[RANDOM.nextInt(0, 2)];
            default -> throw new IllegalStateException("Something went wrong with the game state.");
        };
    }

    private void crossover() {
        List<Bot> crossedList = new ArrayList<>();
        while(crossedList.size() != POPULATION){
            Bot first = bots.get(RANDOM.nextInt(POPULATION / 2,POPULATION));
            Bot second = bots.get(RANDOM.nextInt(POPULATION / 2,POPULATION));
            if (first != second) {
                crossedList.add(first.getChromosome().crossoverWith(second.getChromosome()));
            }
        }
        bots = crossedList;
    }

    // The best 5 also pass to the next generation
    private void elitistCrossover() {
        List<Bot> crossedList = new ArrayList<>();
        int i = 0;
        while(i < (POPULATION / 4)) {
            crossedList.add(bots.get(i));
            i++;
        }
        while(crossedList.size() != POPULATION) {
            Bot first = bots.get(RANDOM.nextInt(POPULATION / 2,POPULATION));
            Bot second = bots.get(RANDOM.nextInt(POPULATION / 2,POPULATION));
            if (first != second) {
                crossedList.add(first.getChromosome().crossoverRate(second.getChromosome(), 0.25));
            }
        }
        bots = crossedList;
    }

    private void mutation() {
        for(Bot bot : bots) {
            bot.getChromosome().mutate(MUTATION_FACTOR);
        }
    }

    public Bot bestBot() {
        selection();
        Bot max = Collections.max(bots);
        int bestBot = bots.indexOf(max);
        Bot bot = bots.get(bestBot);
        System.out.println("Best BOT IS SUCCESSFULLY SELECTED!");
        return bot;
    }

    // FOR EXPERIMENTS
    private void setGenerations(int generation) {
        GENERATIONS = generation;
    }

    public static void main(String[] args) throws IOException {
        // match all bots with each other
        Trainer t = new Trainer();
        t.train();
        // play matches between bots from last generation and choose the best bot among them
        // write the best bot data into a file to use it later when initialising new best bot
        File file = new File("bestChromosomeData.txt");
        FileWriter fileWriter = new FileWriter(file);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.println(t.bestBot().getChromosome().toString());
        printWriter.close();
    }
}
