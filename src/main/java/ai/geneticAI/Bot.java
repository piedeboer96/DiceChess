package ai.geneticAI;
import java.util.ArrayList;
import java.util.List;

import ai.easyrules.BoardAction;
import ai.easyrules.LFacts;
import ai.easyrules.ResultAI;
import ai.easyrules.Utils;
import ai.easyrules.rules.EscapeFromEatRule;
import ai.easyrules.rules.MoveForwardRule;
import chess.ChessMatch;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.RuleListener;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.core.DefaultRulesEngine;
import ai.easyrules.rules.AttackRule;
import ai.easyrules.rules.KingDeadRule;
import ai.easyrules.rules.LongCastleRule;
import ai.easyrules.rules.MoveByPositionRule;
import ai.easyrules.rules.MoveToMateRule;
import ai.easyrules.rules.NewBestActionRule;
import ai.easyrules.rules.NotSuicideRule;
import ai.easyrules.rules.PromoteRule;
import ai.easyrules.rules.ShortCastleRule;
import chess.interfaces.IChessMatch;
import chess.interfaces.IChessMove;
import chess.interfaces.IChessPiece;
import chess.interfaces.IChessboardSquare;
import chess.units.ChessPiece;
import chess.units.King;
import chess.units.Queen;
import chess.utility.ChessMove;
import chess.utility.ChessboardSquare;


public class Bot implements Comparable<Bot> {
    Chromosome chromosome;
    public int wins;
    int currentPlayer=0;

    public  Bot(){
        chromosome = new Chromosome();
        wins = 0;
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
                match.playMove(move.owner(), square);
                int value = evaluateBoardState(match);
                list2.add(value);
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
        if (moves.size() == 0) { return null; }
        IChessMove foundMaximumMove = moves.get(indexOfBestMove);
        List<IChessboardSquare> bestMoveDestinations = new ArrayList<>();
        bestMoveDestinations.add(foundMaximumMove.possibilities().get(indexOfBestDestination));
        return new ChessMove(foundMaximumMove.owner(), bestMoveDestinations);
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
