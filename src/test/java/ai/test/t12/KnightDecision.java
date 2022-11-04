package ai.test.t12;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.Test;

import phase1version.ai.easyrules.EasyRuleEngine;
import phase1version.ai.easyrules.Utils;
import phase1version.chess.ChessMatch;
import phase1version.chess.interfaces.IChessMove;
import phase1version.chess.interfaces.IChessboardSquare;
import phase1version.chess.utility.ChessMove;

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
