package ai.geneticAI;
import ai.easyrules.BoardAction;
import chess.ChessMatch;
import gui.die.Die;
import java.util.*;



public class Trainer{

    static List<Bot> bots; // chromosomes
    static final int population = 20; // population
    static final int generations = 100; // generation
    static final double mutateFactor = 0.007; // mutation factor


    public Trainer() {}


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
     *
     * @param bot1
     * @param bot2
     * @return
     */
    private static Bot matchBots(Bot bot1, Bot bot2) {
        // switch between the bots and see who is the current one to organise the move turn between them
        Bot[] botNum = {bot1, bot2};
        // full pieces
        String startPos = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        //
        Die die = new Die();
        // Creating a new match.
        ChessMatch match = new ChessMatch(startPos);
        match.loadKings();
        //play the game
        int currentPlayer;
        int i = 0;
        int maxMove = 1000;
        try {
            while (i++ < maxMove) {
                currentPlayer= match.getPlayer();
                char rollOne = die.roll(currentPlayer);
                char rollTwo = die.roll(currentPlayer);
                bot1 = botNum[0];
                bot2= botNum[1];
                BoardAction play1 = bot1.play().action;
                BoardAction play2 = bot2.play().action;
                if(play1 == BoardAction.FINISH_MATCH || play2 == BoardAction.FINISH_MATCH ){
                    break;
                }
            }
        } catch(IllegalArgumentException e){System.out.println("GAME IS OVER!!");}
        // check out which player stays till the end
        if(match.getPlayer() == 0){
            return bot1;
        }else{
            return bot2;
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
            for (int i = 0; i < bots.size(); i++) {
                for (int j = i + 1; j < bots.size(); j++) {
                    if (i != j) {
                        matchBots(bots.get(i), bots.get(j)).wins++;
                    }
                }
            }
        // select top bot (chromosome)
        Bot max = Collections.max(bots);
        int bestBot = bots.indexOf(max);
        return bots.get(bestBot);
    }



    /**
     *
     * @param args
     */
    // let the bots play against each other!
    public static void main(String[] args) {
        train();
    }



}
