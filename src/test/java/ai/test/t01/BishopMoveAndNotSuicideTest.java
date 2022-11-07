package ai.test.t01;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.Test;

import ai.easyrules.EasyRuleEngine;
import ai.easyrules.ResultAI;
import ai.easyrules.Utils;
import chess.ChessMatch;
import chess.interfaces.IChessMove;

public class BishopMoveAndNotSuicideTest {

	@Test
	void bishopMoveAndNotSuicide() {

		String startPos = "K/8/8/4b/8/5Q2/8/k w - - 0 1";
		// Creating a new match.
		ChessMatch match = new ChessMatch(startPos);
		int nextPlayer = match.nextPlayer();

		EasyRuleEngine dumyRuleEngine = new EasyRuleEngine(match, 'b', 'p');

		List<IChessMove> moves = match.legalMovesOf(nextPlayer);
		List<IChessMove> splitMoves = Utils.splitMoves(moves);

		System.out.println("All Legal moves ");

		for (IChessMove mv : splitMoves) {
			if (mv.owner().toFen() == 'b')
				System.out.println(mv);
		}
		char[] rolls = new char[2];
		rolls[0] = 'b';
		rolls[1] = 'p';
		System.err.println("-----------");
		List<IChessMove> filterMovesOf = match.legalMovesOf(nextPlayer, rolls);
		for (IChessMove mv : Utils.splitMoves(filterMovesOf)) {

			System.out.println(mv);
		}

		System.out.println();
		System.out.println();

		ResultAI play = dumyRuleEngine.play();
		
		System.out.println("bestMove-->" + play);
		
		assertFalse(play.toRank == 0 && play.toFile == 1);
		assertFalse(play.toRank == 4 && play.toFile == 5);
		assertFalse(play.toRank == 5 && play.toFile == 6);
		assertFalse(play.toRank == 2 && play.toFile == 5);

	}

}
