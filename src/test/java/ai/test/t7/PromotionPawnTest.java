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
        String startPos =  "8/k5P1/8/8/8/2K5/6p1/8 b - - 0 1";

        //Test for black
        testTeam(false, startPos);
        //Test for white
        testTeam(true, startPos);
    }

    public void testTeam(boolean isNextPlayer, String startPos) {
        // Creating a new match
        ChessMatch match = new ChessMatch(startPos);
        int player;

        if (isNextPlayer) { player = match.nextPlayer(); }
        else { player = match.getPlayer(); }

        EasyRuleEngine dumyRuleEngine = new EasyRuleEngine(match, 'p','p');

        List<IChessMove> moves = match.legalMovesOf(player);
        List<IChessMove> splitMoves = Utils.splitMoves(moves);

        System.out.println("All Legal moves ");

        for (IChessMove mv : splitMoves) {
            if (mv.owner().toFen() == 'p' || mv.owner().toFen() == 'x')
                System.out.println(mv);
        }

        System.out.println();
        System.out.println();

        dumyRuleEngine.play();
        ChessMove bestMove = dumyRuleEngine.getBestMove();

        if (player == 0) {
            System.out.println("black bestMove-->" + bestMove);
            IChessboardSquare destinationBlack = bestMove.possibilities().get(0);
            assertTrue(destinationBlack.rank() == 7 && destinationBlack.file() == 6);
        } else {
            System.out.println("white bestMove-->" + bestMove);
            IChessboardSquare destinationWhite = bestMove.possibilities().get(0);
            assertTrue(destinationWhite.rank() == 0 && destinationWhite.file() == 6);
        }
    }
}
