package ai.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.jeasy.rules.api.Fact;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.junit.jupiter.api.Test;

import ai.easyrules.MoveByValueRule;
import chess.interfaces.IChessBoardSquare;
import chess.interfaces.IChessPiece;
import chess.units.King;
import chess.units.Pawn;
import chess.utility.ChessBoardSquare;
import chess.utility.ChessMove;

/**
 * 
 * 
 * Roll P Dumping Moves : ChessMove [owner=ChessPiece [fen=P, team=1, file=0, rank=6], destinations=[ChessBoardSquare [file=0, rank=5], ChessBoardSquare [file=0, rank=4]]] ChessMove [owner=ChessPiece [fen=P, team=1, file=1, rank=6], destinations=[ChessBoardSquare [file=1, rank=5], ChessBoardSquare
 * [file=1, rank=4]]] ChessMove [owner=ChessPiece [fen=P, team=1, file=2, rank=6], destinations=[ChessBoardSquare [file=2, rank=5], ChessBoardSquare [file=2, rank=4]]] ChessMove [owner=ChessPiece [fen=P, team=1, file=3, rank=6], destinations=[ChessBoardSquare [file=3, rank=5], ChessBoardSquare
 * [file=3, rank=4]]] ChessMove [owner=ChessPiece [fen=P, team=1, file=4, rank=6], destinations=[ChessBoardSquare [file=4, rank=5], ChessBoardSquare [file=4, rank=4]]] ChessMove [owner=ChessPiece [fen=P, team=1, file=5, rank=6], destinations=[ChessBoardSquare [file=5, rank=5], ChessBoardSquare
 * [file=5, rank=4]]] ChessMove [owner=ChessPiece [fen=P, team=1, file=6, rank=6], destinations=[ChessBoardSquare [file=6, rank=5], ChessBoardSquare [file=6, rank=4]]] ChessMove [owner=ChessPiece [fen=P, team=1, file=7, rank=6], destinations=[ChessBoardSquare [file=7, rank=5], ChessBoardSquare
 * [file=7, rank=4]]]
 * 
 */

class MoveByValueRuleTest {

	@Test

	void testPawn() {
		Facts facts = new Facts();

		Fact<ChessMove> fact = null;

		IChessPiece piece = new Pawn('P', 0, 6);
		List<IChessBoardSquare> destinations = new ArrayList<IChessBoardSquare>();
		destinations.add(new ChessBoardSquare(0, 5));
		destinations.add(new ChessBoardSquare(0, 4));
		ChessMove move = new ChessMove(piece, destinations);

		fact = new Fact<ChessMove>("ChessMove", move);
		facts.add(fact);

		Rules rules = new Rules();
		MoveByValueRule moveByValue = new MoveByValueRule();

		rules.register(moveByValue);

		RulesEngine rulesEngine = new DefaultRulesEngine();
		rulesEngine.fire(rules, facts);
		System.out.println("move = " + move);
		assertEquals(Pawn.pointValue, -move.possibilities().get(0).getScore());

	}

	@Test

	void testKing() {
		Facts facts = new Facts();

		Fact<ChessMove> fact = null;

		IChessPiece piece = new King('K', 0, 6);
		List<IChessBoardSquare> destinations = new ArrayList<IChessBoardSquare>();
		destinations.add(new ChessBoardSquare(0, 5));

		ChessMove move = new ChessMove(piece, destinations);

		fact = new Fact<ChessMove>("ChessMove", move);
		facts.add(fact);

		Rules rules = new Rules();
		MoveByValueRule moveByValue = new MoveByValueRule();

		rules.register(moveByValue);

		RulesEngine rulesEngine = new DefaultRulesEngine();
		rulesEngine.fire(rules, facts);
		System.out.println("move = " + move);
		assertEquals(King.pointValue, -move.possibilities().get(0).getScore());
	}
	

}