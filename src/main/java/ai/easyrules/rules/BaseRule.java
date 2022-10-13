package ai.easyrules.rules;

import chess.interfaces.IChessMove;

public class BaseRule {

	protected int score = 0;
	
	public boolean checkRoll(IChessMove move, char rollOne) {
		return move.owner().toFen() == rollOne || rollOne == 'x';
	}

	public int getScore() {
		return score;
	}
}
