package ai.geneticAI;
import chess.ChessMatch;
import chess.interfaces.IChessMove;
import chess.interfaces.IChessPiece;
import chess.units.*;
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
    static int count = 0;
    static Random random = new Random();


    /**
     *
     */
    private static void train() {
        for(int i = 0; i < generations; i++) {
            nextGeneration();
            count++;
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
        if(count ==0) {
            bots = new ArrayList<>();
            for (int x = 0; x < population; x++) {
                bots.add(new Bot());
            }
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
        while (iterations++ < 500) {
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
        int blackBot =0;
        int whiteBot =0;
        List<IChessPiece> pieces = match.pieces();

        for(IChessPiece piece : pieces){
            if(piece.team() == bots[0].currentPlayer) {
                if(piece instanceof Bishop){
                    blackBot += Bishop.pointValue;
                }
                if (piece instanceof King){
                    blackBot +=King.pointValue;
                }
                if (piece instanceof Knight){
                    blackBot +=Knight.pointValue;
                }
                if (piece instanceof Pawn){
                    blackBot +=Pawn.pointValue;
                }
                if (piece instanceof Queen){
                    blackBot +=Queen.pointValue;
                }
                if (piece instanceof Rook){
                    blackBot +=Rook.pointValue;
                }
            }
            else if (piece.team() == bots[1].currentPlayer) {
                if(piece instanceof Bishop){
                    whiteBot+=Bishop.pointValue;
                }
                if (piece instanceof King){
                    whiteBot+=King.pointValue;
                }
                if (piece instanceof Knight){
                    whiteBot+=Knight.pointValue;
                }
                if (piece instanceof Pawn){
                    whiteBot+=Pawn.pointValue;
                }
                if (piece instanceof Queen){
                    whiteBot+=Queen.pointValue;
                }
                if (piece instanceof Rook){
                    whiteBot+=Rook.pointValue;
                }
            }
        }
        if(blackBot > whiteBot){
            System.out.println("black win");
            return bots[0];}
        else if (blackBot < whiteBot){
            System.out.println("white win");
            return bots[1];
            }
        else {
            System.out.println("draw!!!!!!!!!!");
            return bots[random.nextInt(2)];
        }
    }



    /**
     * @param min
     * @param max
     * @return
     */
    private static int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }



    /**
     *
     */
    static void crossover() {
        List<Bot> crossedList = new ArrayList<>();
        while(crossedList.size() != bots.size()){
            Bot first = bots.get(getRandomNumberInRange(9,19));
            Bot second = bots.get(getRandomNumberInRange(9,19));
            if (first != second) {
                crossedList.add(first.chromosome.crossoverWith(second.chromosome));
            }
        }
        bots = crossedList;
        }




    /**
     *
     */
    static void mutation() {
        for(Bot bot : bots) { //todo
            bot.chromosome.mutate(mutateFactor);
        }
    }



    /**
     * @return best bot
     */
    public static Bot bestBot() {
            selection();
            Bot max = Collections.max(bots);
            int bestBot = bots.indexOf(max);
            Bot bot = bots.get(bestBot);
            System.out.println("Best BOT IS SUCCESSFULLY SELECTED!");
            return bot;
    }



    /**
     * @param args main
     */
    public static void main(String[] args) throws IOException {
        // match all bots with each other
        train();
        // play matches between bots from last generation and choose the best bot among them
        // write the best bot data into a file to use it later when initialising new best bot
        File file = new File("bestChromosomeData.txt");
        FileWriter fileWriter = new FileWriter(file);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.println(bestBot().chromosome.toString());
        printWriter.close();
    }



}
