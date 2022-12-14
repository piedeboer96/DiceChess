package ai.test.t03;


import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ai.easyrules.EasyRuleEngine;
import ai.easyrules.ResultAI;
import ai.easyrules.Utils;
import chess.ChessMatch;
import chess.interfaces.IChessMove;

public class TestKingEatPawnTest {

	@Test
	void test() {

		String startPos ;
		
		startPos = "1n1qk3/1r6/8/3P4/4Pr2/P2K1p1b/P2p1P2/8 w - - 0 1";
		// Creating a new match.
		
		ChessMatch match = new ChessMatch(startPos);
		
		int nextPlayer;
		nextPlayer = match.getPlayer();
		if(nextPlayer==0)
			nextPlayer=match.nextPlayer();
		
		char roll = 'K';

		List<IChessMove> moves = match.legalMovesOf(nextPlayer);
		List<IChessMove> splitMoves = Utils.splitMoves(moves);
		
		System.out.println("All Legal moves ");
		for (IChessMove mv : splitMoves) {
			if (mv.owner().toFen() == roll)
				System.out.println(mv);
		}

		System.out.println();
		System.out.println();

		EasyRuleEngine dumyRuleEngine = new EasyRuleEngine(match, 'R','R');
		ResultAI play = dumyRuleEngine.play();
		
		
		Assertions.assertEquals(3, play.toFile);
		Assertions.assertEquals(6, play.toRank);
		

	}

}
