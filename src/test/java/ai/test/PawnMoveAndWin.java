package ai.test;

import java.util.List;

import org.junit.jupiter.api.Test;

import ai.easyrules.EasyRuleEngine;
import chess.ChessMatch;
import chess.interfaces.IChessMove;

class PawnMoveAndWin {

	@Test
	void test() {

		String startPos = "8/8/8/p/1K6/8/8/k w - - 0 1";
		// Creating a new match.
		ChessMatch match = new ChessMatch(startPos);
		int nextPlayer = match.nextPlayer();
		List<IChessMove> moves = match.legalMovesOf(nextPlayer);
		for (IChessMove mv : moves) {
			if (mv.owner().toFen() == 'p')
				System.out.println(mv);
			}
			
			
 		
			EasyRuleEngine dumyRuleEngine = new EasyRuleEngine(match, 'p');
			dumyRuleEngine.play();
 
	 
	}

}
