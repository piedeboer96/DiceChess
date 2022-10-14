package ai.test.t06;

import java.util.List;

import org.junit.jupiter.api.Test;

import ai.easyrules.EasyRuleEngine;
import chess.ChessMatch;
import chess.interfaces.IChessMove;

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

        


    }
}
