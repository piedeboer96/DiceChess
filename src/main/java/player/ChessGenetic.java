package player;

import game.DiceChess;
import genetic.ChessBot;
import genetic.Chromosome;
import simulation.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Defines an AI that uses the genetic algorithm to play a game of dice chess.
 * @version 1.0
 **/
public final class ChessGenetic implements Player {
    private final ChessBot BEST_BOT;

    public ChessGenetic() throws FileNotFoundException {
        List<Integer> list = readFile("bestChromosomeData.txt");
        int[] finalList = new int[list.size()];
        for(int i=0; i<list.size(); i++){
            finalList[i] = list.get(i);
        }
        BEST_BOT = new ChessBot(new Chromosome(finalList));
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
        BEST_BOT.play(game);
        return game.toString();
    }
}
