package ai.test;

import java.util.ArrayList;
import java.util.List;

import org.jeasy.rules.api.Fact;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.RuleListener;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.junit.jupiter.api.Test;

import ai.easyrules.MoveForwardRule;
import chess.interfaces.IChessBoardSquare;
import chess.interfaces.IChessPiece;
import chess.units.Pawn;
import chess.utility.ChessBoardSquare;
import chess.utility.ChessMove;

/**
 * 
 * 
 * Roll P Dumping Moves : ChessMove [owner=ChessPiece [fen=P, team=1, file=0, rank=6], destinations=[ChessBoardSquare [file=0, rank=5], ChessBoardSquare [file=0, rank=4]]] ChessMove [owner=ChessPiece [fen=P, team=1, file=1, rank=6], destinations=[ChessBoardSquare [file=1,
 * rank=5], ChessBoardSquare [file=1, rank=4]]] ChessMove [owner=ChessPiece [fen=P, team=1, file=2, rank=6], destinations=[ChessBoardSquare [file=2, rank=5], ChessBoardSquare [file=2, rank=4]]] ChessMove [owner=ChessPiece [fen=P, team=1, file=3, rank=6],
 * destinations=[ChessBoardSquare [file=3, rank=5], ChessBoardSquare [file=3, rank=4]]] ChessMove [owner=ChessPiece [fen=P, team=1, file=4, rank=6], destinations=[ChessBoardSquare [file=4, rank=5], ChessBoardSquare [file=4, rank=4]]] ChessMove [owner=ChessPiece [fen=P, team=1,
 * file=5, rank=6], destinations=[ChessBoardSquare [file=5, rank=5], ChessBoardSquare [file=5, rank=4]]] ChessMove [owner=ChessPiece [fen=P, team=1, file=6, rank=6], destinations=[ChessBoardSquare [file=6, rank=5], ChessBoardSquare [file=6, rank=4]]] ChessMove [owner=ChessPiece
 * [fen=P, team=1, file=7, rank=6], destinations=[ChessBoardSquare [file=7, rank=5], ChessBoardSquare [file=7, rank=4]]]
 * 
 */

class TrivialMovesTest {

	@Test
	void test() {
		Facts facts = new Facts();
		/*
		 * ChessMove best = new ChessMove(new Pawn('P', 4, 6), new ArrayList<IChessBoardSquare>());
		 * 
		 * facts.put("best", best);
		 */

		Fact<ChessMove> fact = null;

		IChessPiece piece = new Pawn('P', 0, 6);
		List<IChessBoardSquare> destinations = new ArrayList<IChessBoardSquare>();
		destinations.add(new ChessBoardSquare(0, 5));
		destinations.add(new ChessBoardSquare(0, 4));
		ChessMove move1 = new ChessMove(piece, destinations);

		fact = new Fact<ChessMove>("ChessMove", move1);
		facts.add(fact);

		piece = new Pawn('p', 1, 6);
		destinations = new ArrayList<IChessBoardSquare>();
		destinations.add(new ChessBoardSquare(1, 4));
		destinations.add(new ChessBoardSquare(1, 5));
		move1 = new ChessMove(piece, destinations);
		// facts.put("move", move1);
		fact = new Fact<ChessMove>("move2", move1);
		facts.add(fact);

		piece = new Pawn('P', 2, 6);
		destinations = new ArrayList<IChessBoardSquare>();
		destinations.add(new ChessBoardSquare(2, 4));
		destinations.add(new ChessBoardSquare(2, 5));
		move1 = new ChessMove(piece, destinations);
		// facts.put("move", move1);
		fact = new Fact<ChessMove>("move3", move1);
		facts.add(fact);

		piece = new Pawn('P', 3, 6);
		destinations = new ArrayList<IChessBoardSquare>();
		destinations.add(new ChessBoardSquare(3, 4));
		destinations.add(new ChessBoardSquare(3, 5));

		move1 = new ChessMove(piece, destinations);
		fact = new Fact<ChessMove>("move4", move1);
		facts.add(fact);

		Rules rules = new Rules();
		MoveForwardRule moveRules = new MoveForwardRule();
		rules.register(moveRules);

		DefaultRulesEngine rulesEngine = new DefaultRulesEngine();

		RuleListener myRuleListener = new RuleListener() {
			@Override
			public void onSuccess(Rule rule, Facts facts) {

				System.out.println("rule " + rule.getDescription());
				System.out.println(rule.getName());
				System.out.println("Facts " + facts);
			}
		};
		rulesEngine.registerRuleListener(myRuleListener);
		rulesEngine.fire(rules, facts);

	}

}
