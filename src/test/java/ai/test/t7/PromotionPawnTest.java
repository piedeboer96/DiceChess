package ai.test.t7;

import ai.easyrules.EasyRuleEngine;
import ai.easyrules.Utils;
import chess.ChessMatch;
import chess.interfaces.IChessMove;
import chess.interfaces.IChessboardSquare;
import chess.utility.ChessMove;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PromotionPawnTest {

    @Test
    public void promotionPawnTest() {

        //Position that allows for white and black to promote after a valid roll
        String startPos =  "8/k5P1/8/8/8/2K5/6p1/8 w - - 0 1";

        // Creating a new match
        ChessMatch match = new ChessMatch(startPos);
        int nextPlayer = match.nextPlayer();
        EasyRuleEngine dumyRuleEngine = new EasyRuleEngine(match, 'p','p');

        List<IChessMove> moves = match.legalMovesOf(nextPlayer);
        List<IChessMove> splitMoves = Utils.splitMoves(moves);

        System.out.println("All Legal moves ");
        
        for (IChessMove mv : splitMoves) {
            if (mv.owner().toFen() == 'p')
                System.out.println(mv);
        }

        System.out.println();
        System.out.println();

        dumyRuleEngine.play();
        ChessMove bestMove = dumyRuleEngine.getBestMove();
        System.out.println("bestMove-->" + bestMove);

        IChessboardSquare destination = bestMove.possibilities().get(0);
        assertTrue(destination.rank() == 1 && destination.file() == 7);

    }
}
