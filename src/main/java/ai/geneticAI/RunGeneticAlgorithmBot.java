package ai.geneticAI;
import chess.ChessMatch;
import chess.interfaces.IChessMove;
import chess.interfaces.IChessboardSquare;
import gui.DiceChessWindow;
import gui.interfaces.IHighlighter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;



public class RunGeneticAlgorithmBot {


    public static void main(String[] args) throws InterruptedException, FileNotFoundException {

        RunGeneticAlgorithmBot runGame = new RunGeneticAlgorithmBot();
        runGame.game();

    }

    public void game() throws InterruptedException, FileNotFoundException {
        // Creating a new window.
        DiceChessWindow window = new DiceChessWindow(800, 640, false);
        // full pieces
        String startPos = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        // Creating a new match.
        ChessMatch match = new ChessMatch(startPos);
        match.loadKings();
        // Displays the match
        window.display(match);
        // initialize the best chromosome with the values from the file bestChromosome
        int[] data = readFile("bestChromosomeData.txt");
        Chromosome bestChromosome = new Chromosome(data);
        Bot bot = new Bot(bestChromosome);
        //play the game
        int iteration = 0;
        while (iteration++ < 2000) {
            int currentPlayer = match.getPlayer();
            // first we need fire the roll
            char rollOne = window.getDice().roll(currentPlayer)[0];
            char rollTwo = window.getDice().roll(currentPlayer)[0];
            sleep(500);
            // assign the object bot to the best bot in the GA population
            IChessMove decision = bot.bestMove(match, rollOne, rollTwo);
            if (decision == null) {
                continue;
            }
            if (decision.owner().team() == match.getPlayer()) {
                match.playMove(decision.owner(), decision.possibilities().get(0));
            }
            window.refresh();
            sleep(800);
            window.display(match);
        }
    }

    /**
     * @param sec
     */
    private void sleep(int sec) {
        try {
            TimeUnit.MILLISECONDS.sleep(sec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * @param fileToread
     * @return
     * @throws FileNotFoundException
     */
    public static int[] readFile(String fileToread) throws FileNotFoundException {
        File file = new File(fileToread);
        Scanner scanner = new Scanner(file);
        int count = 0;
        while (scanner.hasNextInt()) {
            count++;
            scanner.nextInt();
        }
        int[] arr = new int[count];
        Scanner scanner1 = new Scanner(file);
        for (int i = 0; i < arr.length; i++) {
            arr[i] = scanner1.nextInt();
        }
        return arr;
    }



}
