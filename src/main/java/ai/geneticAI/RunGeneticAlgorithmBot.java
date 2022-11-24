package ai.geneticAI;
import chess.ChessMatch;
import chess.interfaces.IChessMove;
import gui.DiceChessWindow;
import java.io.File;
import java.io.FileNotFoundException;
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
        int[] data = new int[]{-635905676,-1240317172,-69408920,1922620828,1750874928,1543847188,1563878404,1520025588,-1467867512,-660880356,
                1913333096,-27847748,761270876,-398238436,1815001324,786660816,-275824428,1497654488,1620462048,-1377927316,-2067614428,-2110487464
        ,-1169846808,-2021274444,1535554924,1092210256,718919520,-464597412,-1897612592,682982004,857083444,-1581611588};

        Chromosome bestChromosome = new Chromosome(data);
        Bot bot = new Bot(bestChromosome);
        //play the game
        int iteration = 0;
        while (iteration++ < 1000) {
            int currentPlayer = match.getPlayer();
            // first we need fire the roll
            char rollOne = window.getDice().roll(currentPlayer)[0];
            char rollTwo = window.getDice().roll(currentPlayer)[0];
            sleep(10);
            // assign the object bot to the best bot in the GA population
            IChessMove decision = bot.bestMove(match, rollOne, rollTwo);
            if (decision == null) {
                continue;
            }
            if (decision.owner().team() == match.getPlayer()) {
                match.playMove(decision.owner(), decision.possibilities().get(0));
            }
            window.refresh();
            sleep(500);
            System.out.println("move number "+ iteration);
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
