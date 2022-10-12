package ai.easyrules.rules;

import chess.interfaces.IChessMove;

public class BaseRule {

	public boolean checkRoll(IChessMove move, char rollOne) {
		return move.owner().toFen() == rollOne || rollOne == 'x';
	}

}
