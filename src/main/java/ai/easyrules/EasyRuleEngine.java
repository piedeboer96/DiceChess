package ai.easyrules;

import java.util.ArrayList;
import java.util.List;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.core.DefaultRulesEngine;

import chess.interfaces.IChessBoardSquare;
import chess.interfaces.IChessMove;
import chess.utility.ChessMove;

public class EasyRuleEngine {

	IChessMove bestMove = null;

	public EasyRuleEngine(List<IChessMove> realMoves) {

		Rules rules = loaRules();

		System.out.println("Evaluating " + realMoves.size() + " moves");

		for (IChessMove move : realMoves) {
			Facts facts = new Facts();
			facts.put("ChessMove", move);
			DefaultRulesEngine rulesEngine = new DefaultRulesEngine();
			rulesEngine.fire(rules, facts);
			checkIFisTheBestMove(move);
		}

	}

	private void checkIFisTheBestMove(IChessMove move) {

		List<IChessBoardSquare> possibilities = move.possibilities();
		for (IChessBoardSquare poss : possibilities) {
			if (bestMove == null || bestMove.possibilities().get(0).getScore() < poss.getScore())
				assignBestMove(move, poss);
		}

	}

	private void assignBestMove(IChessMove move, IChessBoardSquare poss) {
		bestMove = new ChessMove(move.owner(), new ArrayList<IChessBoardSquare>());
		bestMove.possibilities().add(poss);

	}

	public Rules loaRules() {

		Rules rules = new Rules();

		// load all rules ..

		// this simple rules assign 1 score if the piece can be moved FWD
		MoveForwardRule moveRules = new MoveForwardRule();
		MoveByPositionRule moveByposition = new MoveByPositionRule();
		MoveByValueRule moveByValue = new MoveByValueRule();

		rules.register(moveByposition);
		rules.register(moveByValue);
		rules.register(moveRules);

		return rules;

	}

	public IChessMove getNextMove() {
		System.err.println("The best move is " + bestMove);
		return bestMove;
	}

}
