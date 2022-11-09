package ai.geneticAI;
import chess.interfaces.IChessMatch;
import chess.interfaces.IChessMove;
import chess.interfaces.IChessboardSquare;
import chess.utility.ChessMove;


import java.util.*;

public class Bot {

    Chromosome chromosome;

    public Bot(){
        chromosome = new Chromosome();
    }



    // find the all the legal moves
    private List<IChessMove> generateAllPossibleMoves(IChessMatch match, char rollOne, char rollTwo){
        int currentPlayer = match.getPlayer();
        List<IChessMove> moves;
        char[] rolls = new char[2];
        rolls[0] = rollOne;
        rolls[1] = rollTwo;
        moves = match.legalMovesOf(currentPlayer, rolls);
        return moves;
    }

    // use the fitness function to evaluate and return the total value of this state
    private int evaluateBoardState(IChessMatch match)
    {
        return FitnessFunction.evaluate(match);
    }


    // return best move
    public ChessMove bestMove(IChessMatch match, char rollOne, char rollTwo) {
        List<IChessMove> moves;
        List<List<Integer>> list = new ArrayList<>();
        moves = generateAllPossibleMoves(match, rollOne, rollTwo);
        String state = match.toFen();
        for ( IChessMove move : moves) {
            List<Integer> list2 = new ArrayList<>();
            for (IChessboardSquare square : move.possibilities()) {
                match.playMove(move.owner(), square);
                int value = evaluateBoardState(match);
                list2.add(value);
                match.interpret(state);
            }
            list.add(list2);
        }
        int max = 0;
        int index = 0; // index of the move
        for (int i = 0; i < list.size(); i++) {
            List<Integer> list2 = list.get(i);
            for(int j =0; j < list2.size(); j ++){
                if(list2.get(j) > max){
                    max = list2.get(j);
                    index = i;
                }
            }
        }
        return (ChessMove) moves.get(index);
    }



}
