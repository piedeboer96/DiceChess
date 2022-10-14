package ai.test.t08;

import java.util.List;

import org.junit.jupiter.api.Test;

import ai.easyrules.EasyRuleEngine;
import ai.easyrules.ResultAI;
import ai.easyrules.Utils;
import chess.ChessMatch;
import chess.interfaces.IChessMove;

public class RookNotMovingTest {

    @Test
    void rookNotMoving() {

        //black is starting


        // Generate starting position
        String startPos;
        startPos = "1r6/2p3pp/p1p2k2/4p3/8/1K6/P1PN2B1/R7 W - - 0 1";

     // Creating a new match.
     		ChessMatch match = new ChessMatch(startPos);
     		int nextPlayer = match.getPlayer();
     		
     		EasyRuleEngine dumyRuleEngine = new EasyRuleEngine(match, 'R','Q');

     		List<IChessMove> moves = match.legalMovesOf(nextPlayer);
     		List<IChessMove> splitMoves = Utils.splitMoves(moves);
     		
     		System.out.println("All Legal moves ");
     		
     		for (IChessMove mv : splitMoves) {
     			if (mv.owner().toFen() == 'b')
     				System.out.println(mv);
     		}
         
       

        ResultAI play = dumyRuleEngine.play();
       

       
       

        System.out.println("play-->" + play);

    
//        Assertions.assertEquals(null, play);
       






    }
}
