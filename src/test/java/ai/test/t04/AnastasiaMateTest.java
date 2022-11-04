package ai.test.t04;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import phase1version.ai.easyrules.EasyRuleEngine;
import phase1version.ai.easyrules.ResultAI;
import phase1version.ai.easyrules.Utils;
import phase1version.chess.ChessMatch;
import phase1version.chess.interfaces.IChessMove;

//startPos = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
public class AnastasiaMateTest {

	@Test
	void anastasiaMateRockMove() {
		String startPos = null;
		startPos = "8/4n1PK/8/8/8/1r6/8/k7 w - - 0 1";
		// Creating a new match.
		ChessMatch match = new ChessMatch(startPos);
		match.loadKings();
		int nextPlayer = match.nextPlayer();
		EasyRuleEngine dumyRuleEngine = new EasyRuleEngine(match, 'r','p');

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
		assertEquals(5, play.toRank);
		 
		 
	}

}
