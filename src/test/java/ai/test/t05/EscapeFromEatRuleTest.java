package ai.test.t05;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import ai.easyrules.EasyRuleEngine;
import ai.easyrules.ResultAI;
import ai.easyrules.Utils;
import chess.ChessMatch;
import chess.interfaces.IChessMove;

//startPos = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
//fgh45rrt
//startPos = "k7/7b/8/R3b4/5p2/8/2P5/1K2Q3 w - - 0 1";
public class EscapeFromEatRuleTest {

	@Test
	void bishopMustEscapeFromEat() {
		
		String startPos = null;
		startPos = "k7/7b/8/R3b4/5p2/8/2P5/1K2Q3 w - - 0 1";
		// Creating a new match.
		ChessMatch match = new ChessMatch(startPos);
		match.loadKings();
		int nextPlayer = match.nextPlayer();
		EasyRuleEngine dumyRuleEngine = new EasyRuleEngine(match, 'b','b');

		List<IChessMove> moves = match.legalMovesOf(nextPlayer);
		List<IChessMove> splitMoves = Utils.splitMoves(moves);
		System.out.println("All Legal moves ");
		
		for (IChessMove mv : splitMoves) {
			if (mv.owner().toFen() == 'b')
				System.out.println(mv);
		}

		System.out.println();
		System.out.println();

		ResultAI play = dumyRuleEngine.play();
		
		System.out.println("bestMove-->" + play);
		
		assertEquals(7, play.toFile);
		
		 
		 
	}

}
