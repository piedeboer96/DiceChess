package ai.test.t02;

import java.util.List;

import org.junit.jupiter.api.Test;

import phase1version.ai.easyrules.BoardAction;
import phase1version.ai.easyrules.EasyRuleEngine;
import phase1version.chess.ChessMatch;
import phase1version.chess.interfaces.IChessMove;

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
		
			EasyRuleEngine dumyRuleEngine = new EasyRuleEngine(match, 'p','b');

			BoardAction play = dumyRuleEngine.play().action;
			
			assert(play==BoardAction.FINISH_MATCH);
 
	 
	}

}
