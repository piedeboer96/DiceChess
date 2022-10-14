package ai.test.t12;

import ai.easyrules.EasyRuleEngine;
import ai.easyrules.Utils;
import chess.ChessMatch;
import chess.interfaces.IChessMove;
import chess.interfaces.IChessboardSquare;
import chess.utility.ChessMove;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*
  The knight suicide for nothing, like if its life has no value.
  
 */
public class KnightDecision {

	@Test
	void castleKingPointOfView() {

		String startPos = "rnbqkb1r/pppppppp/8/4n3/2B5/8/PPPP1PPP/RNBQK1NR w - - 0 1";
		// Creating a new match.
		ChessMatch match = new ChessMatch(startPos);
		int nextPlayer = match.nextPlayer();
		EasyRuleEngine dumyRuleEngine = new EasyRuleEngine(match, 'n','n');

		List<IChessMove> moves = match.legalMovesOf(nextPlayer);
		List<IChessMove> splitMoves = Utils.splitMoves(moves);
		System.out.println("All Legal moves ");
		for (IChessMove mv : splitMoves) {
			if (mv.owner().toFen() == 'n')
				System.out.println(mv);
		}

		System.out.println();
		System.out.println();

		dumyRuleEngine.play();
		ChessMove bestMove = dumyRuleEngine.getBestMove();
		System.out.println("bestMove-->" + bestMove);
		IChessboardSquare destination = bestMove.possibilities().get(0);
		assertFalse(destination.file() == 3 && destination.rank() == 5);
		assertFalse(destination.file() == 5 && destination.rank() == 5);
		assertFalse(destination.file() == 6 && destination.rank() == 4);
	}

}
