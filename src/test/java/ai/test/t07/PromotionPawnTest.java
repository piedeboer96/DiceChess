package ai.test.t07;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.Test;

import ai.easyrules.EasyRuleEngine;
import ai.easyrules.ResultAI;
import ai.easyrules.Utils;
import chess.ChessMatch;
import chess.interfaces.IChessMove;

public class PromotionPawnTest {

	@Test
	public void promotionPawnTest() {

		// Position that allows for white and black to promote after a valid roll
//        String startPos =  "8/k5P1/8/8/8/2K5/6p1/8 w - - 0 1";
		String startPos = "8/P7/8/8/2K5/8/8/k7 w - - 0 1";
		// Creating a new match
		ChessMatch match = new ChessMatch(startPos);
		int player = match.getPlayer();
		EasyRuleEngine dumyRuleEngine = new EasyRuleEngine(match, 'P', 'P');

		List<IChessMove> moves = match.legalMovesOf(player);
		List<IChessMove> splitMoves = Utils.splitMoves(moves);

		System.out.println("All Legal moves ");

		for (IChessMove mv : splitMoves) {
			if (mv.owner().toFen() == 'P')
				System.out.println(mv);
		}

		System.out.println();
		System.out.println();

		ResultAI play = dumyRuleEngine.play();
	
		System.out.println("bestMove-->" + play);

		// assertTrue(destination.rank() == 1 && destination.file() == 7);
		assertTrue(play.toRank == 0 && play.toFile == 0);
		fail("we must check that the pawn become a queen");
	}
}
