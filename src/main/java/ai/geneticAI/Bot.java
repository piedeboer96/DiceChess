package ai.geneticAI;
import java.util.ArrayList;
import java.util.List;

import chess.interfaces.IChessMatch;
import chess.interfaces.IChessMove;
import chess.interfaces.IChessboardSquare;
import chess.utility.ChessMove;


public class Bot implements Comparable<Bot> {
    Chromosome chromosome;
    public int wins;
    int currentPlayer;
    public  Bot(){
        this.chromosome = new Chromosome();
        this.wins = 0;
        this.currentPlayer=0;
    }

    public  Bot(Chromosome bestChromosome){
        this.chromosome = bestChromosome;
        this.wins = 0;
        this.currentPlayer=0;
    }


    /**
     * @param match is the state if the game
     * @param rollOne of the die
     * @param rollTwo of the die
     * @return all possible moves for this current bot
     */
    private List<IChessMove> generateAllPossibleMoves(IChessMatch match, char rollOne, char rollTwo){
        currentPlayer = match.getPlayer();
        List<IChessMove> moves;
        char[] rolls = new char[2];
        rolls[0] = rollOne;
        rolls[1] = rollTwo;
        moves = match.legalMovesOf(currentPlayer, rolls);
        return moves;
    }



    /**
     * @param match is  the satae of the game
     * @return the value of this board match state
     */
    // use the fitness function to evaluate and return the total value of this state
    private int evaluateBoardState(IChessMatch match)
    {
        return FitnessFunction.evaluate(match);
    }



    /**
     * @param match is the state if the game
     * @param rollOne of the die
     * @param rollTwo of the die
     * @return the best move
     */
    // return best move
    public ChessMove bestMove(IChessMatch match, char rollOne, char rollTwo) {
        List<IChessMove> moves;
        List<List<Integer>> list = new ArrayList<>();
        moves = generateAllPossibleMoves(match, rollOne, rollTwo);
        for ( IChessMove move : moves) {
            List<Integer> list2 = new ArrayList<>();
            for (IChessboardSquare square : move.possibilities()) {
                if(move.owner().team() == match.getPlayer()) {
                    match.playMove(move.owner(), square);
                    int value = evaluateBoardState(match);
                    list2.add(value);
                }
            }
            list.add(list2);
        }
        int max = 0;
        int indexOfBestMove = 0;
        int indexOfBestDestination = 0;
        for (int i = 0; i < list.size(); i++) {
            List<Integer> list2 = list.get(i);
            for(int j =0; j < list2.size(); j++){
                if(list2.get(j) > max){
                    max = list2.get(j);
                    indexOfBestMove = i;
                    indexOfBestDestination = j;
                }
            }
        }
        if (moves.size() == 0) {return null;}
        IChessMove foundMaximumMove = moves.get(indexOfBestMove);
        List<IChessboardSquare> bestMoveDestinations = new ArrayList<>();
        bestMoveDestinations.add(foundMaximumMove.possibilities().get(indexOfBestDestination));
        return new ChessMove(foundMaximumMove.owner(), bestMoveDestinations);
    }



    /**
     * @return
     */
    @Override
    public String toString() {
        return this.chromosome.toString();
    }


    /**
     * @param object the object to be compared.
     * @return return the sort of best objects in a decreasing order
     */
    @Override
    public int compareTo(Bot object) {
        return object.wins-wins;
    }



}
