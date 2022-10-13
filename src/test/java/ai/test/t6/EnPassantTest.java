package ai.test.t6;

import ai.easyrules.Action;
import ai.easyrules.EasyRuleEngine;
import ai.easyrules.Utils;
import chess.ChessMatch;
import chess.interfaces.IChessMove;
import chess.interfaces.IChessboardSquare;
import chess.utility.ChessMove;
import examples.JustShowChessBoard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnPassantTest {

    @Test
    void enPassant() {

        //black is starting


        // Generate starting position
        String startPos;
        startPos = "k7/5p2/8/6P1/8/8/8/K7 b - - 0 1";

        // Creating a new match.
        ChessMatch match = new ChessMatch(startPos);

        int nextPlayer = match.nextPlayer();
        List<IChessMove> moves = match.legalMovesOf(nextPlayer);


        //case 1: we roll a P
        //case 2: we roll doubles

        for (IChessMove mv : moves) {
            if (mv.owner().toFen() == 'p' || mv.owner().toFen() == 'x')
                System.out.println(mv);
        }

        EasyRuleEngine dumyRuleEngine = new EasyRuleEngine(match, 'b','b');
        //EasyRuleEngine dumyRuleEngine2 = new EasyRuleEngine(match, 'p','b');

        dumyRuleEngine.play();
        //dumyRuleEngine2.play();

        ChessMove bestMove = dumyRuleEngine.getBestMove();
        //ChessMove bestMove2 = dumyRuleEngine2.getBestMove();

        System.out.println("bestMove-->" + bestMove);

        //this is for black
        IChessboardSquare destination = bestMove.possibilities().get(0);
        Assertions.assertEquals(5, destination.file());
        Assertions.assertEquals(3, destination.rank());

        //check where white ends up
        IChessboardSquare destinationWhite = bestMove.possibilities().get(0);
        Assertions.assertEquals(5, destinationWhite.file());
        Assertions.assertEquals(2, destinationWhite.file());

//        IChessboardSquare destination2 = bestMove2.possibilities().get(0);
//        Assertions.assertEquals(5, destination.file());
//        Assertions.assertEquals(3, destination.rank());

//        IChessboardSquare destinationWhite2 = bestMove2.possibilities().get(1);
//        Assertions.assertEquals(5, destinationWhite.file());
//        Assertions.assertEquals(2, destinationWhite.file());



    }
}
