package ai.test.t2;

import java.util.List;

import org.junit.jupiter.api.Test;

import ai.easyrules.Action;
import ai.easyrules.EasyRuleEngine;
import chess.ChessMatch;
import chess.interfaces.IChessMove;

public class PawnMoveAndWinTest {

	// 0 black 1 white
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
			Action play = dumyRuleEngine.play();
			
			assert(play==Action.FINISH_MATCH);
 
	 
	}

}
