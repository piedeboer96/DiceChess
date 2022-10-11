package ai.test;

import java.util.List;

import org.junit.jupiter.api.Test;

import ai.easyrules.Action;
import ai.easyrules.EasyRuleEngine;
import chess.ChessMatch;
import chess.interfaces.IChessMove;

class BishopMoveAndNotSuicideTest {

	@Test
	void test() {

		String startPos = "K/8/8/4b/8/5Q2/8/k w - - 0 1";
		// Creating a new match.
		ChessMatch match = new ChessMatch(startPos);
		int nextPlayer = match.nextPlayer();
			EasyRuleEngine dumyRuleEngine = new EasyRuleEngine(match, 'b');
			
			List<IChessMove> moves = match.legalMovesOf(nextPlayer);
			List<IChessMove> splitMoves = EasyRuleEngine.splitMoves(moves);
			System.out.println("All Legal moves ");
			for (IChessMove mv : splitMoves) {
				if (mv.owner().toFen() == 'b')
					System.out.println(mv);
				}
				
				System.out.println();
				System.out.println();
				
			Action play = dumyRuleEngine.play();
			
			
			assert(play==Action.FINISH_MATCH);
 
	 
	}

}
