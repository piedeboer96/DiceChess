package ai.test.t12;

import ai.easyrules.EasyRuleEngine;
import ai.easyrules.Utils;
import chess.ChessMatch;
import chess.interfaces.IChessMove;
import chess.interfaces.IChessboardSquare;
import chess.utility.ChessMove;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

/*

  unfinish, @eden will finish this

 */
public class KnightDecision {

	@Test
	void castleKingPointOfView() {

		String startPos = "rnbqkbnr/8/8/8/8/8/8/R3K2R W - - 0 1";
		// Creating a new match.
		ChessMatch match = new ChessMatch(startPos);
		int nextPlayer = match.nextPlayer();
		EasyRuleEngine dumyRuleEngine = new EasyRuleEngine(match, 'R','R');

		List<IChessMove> moves = match.legalMovesOf(nextPlayer);
		List<IChessMove> splitMoves = Utils.splitMoves(moves);
		System.out.println("All Legal moves ");
		for (IChessMove mv : splitMoves) {
			if (mv.owner().toFen() == 'R')
				System.out.println(mv);
		}

		System.out.println();
		System.out.println();

		dumyRuleEngine.play();
		ChessMove bestMove = dumyRuleEngine.getBestMove();
		System.out.println("bestMove-->" + bestMove);
		IChessboardSquare destination = bestMove.possibilities().get(0);
		assertTrue(destination.rank() == 3 && destination.file() == 7);
		assertTrue(destination.rank() == 5 && destination.file() == 7);
	}

}
