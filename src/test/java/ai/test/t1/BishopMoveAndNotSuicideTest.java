package ai.test.t1;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.Test;

import ai.easyrules.EasyRuleEngine;
import ai.easyrules.Utils;
import chess.ChessMatch;
import chess.interfaces.IChessboardSquare;
import chess.interfaces.IChessMove;
import chess.utility.ChessMove;

public class BishopMoveAndNotSuicideTest {

	@Test
	void bishopMoveAndNotSuicide() {

		String startPos = "K/8/8/4b/8/5Q2/8/k w - - 0 1";
		// Creating a new match.
		ChessMatch match = new ChessMatch(startPos);
		int nextPlayer = match.nextPlayer();
		EasyRuleEngine dumyRuleEngine = new EasyRuleEngine(match, 'b','p');

		List<IChessMove> moves = match.legalMovesOf(nextPlayer);
		List<IChessMove> splitMoves = Utils.splitMoves(moves);
		System.out.println("All Legal moves ");
		for (IChessMove mv : splitMoves) {
			if (mv.owner().toFen() == 'b')
				System.out.println(mv);
		}

		System.out.println();
		System.out.println();

		dumyRuleEngine.play();
		ChessMove bestMove = dumyRuleEngine.getBestMove();
		System.out.println("bestMove-->" + bestMove);
		IChessboardSquare destination = bestMove.possibilities().get(0);
		assertFalse(destination.rank() == 0 && destination.file() == 1);
		assertFalse(destination.rank() == 4 && destination.file() == 5);
		assertFalse(destination.rank() == 5 && destination.file() == 6);
		assertFalse(destination.rank() == 2 && destination.file() == 5);

	}

}
