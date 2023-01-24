package player;

import game.DiceChess;
import game.Movement;
import genetic.Bot;
import genetic.Chromosome;
import simulation.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Defines an AI that uses the genetic algorithm to play a game of dice chess.
 * @version 1.0
 **/
public final class Darwin implements Player {
    private final Bot BESTBOT;

    public Darwin() throws FileNotFoundException {
        List<Integer> list = readFile("bestChromosomeData.txt");
        double[] finalList = new double[list.size()];
        for(int i=0; i<list.size(); i++){
            finalList[i] = list.get(i);
        }
        BESTBOT = new Bot(new Chromosome(finalList));
    }

    public List<Integer> readFile(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);
        List<Integer> finalList = new ArrayList<>();
        while (scanner.hasNext()) {
            if (scanner.hasNextInt()) {
                finalList.add(scanner.nextInt());
            }
            else {
                scanner.next();
            }
        }
        return finalList;
    }


    @Override
    public String play(int roll, DiceChess game) {
        BESTBOT.play(game, roll);
        return game.toString();
    }
}
