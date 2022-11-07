package ai.easyrules.rules;

import ai.easyrules.BoardAction;
import ai.easyrules.LFacts;
import org.jeasy.rules.api.Facts;

import chess.interfaces.IChessMove;

public abstract class ABaseRule {

	protected int score = 0;
//	 check or castel
	public boolean checkRoll(IChessMove move, char rollOne) {
		return move.owner().toFen() == rollOne || rollOne == 'x';
	}

	public int getScore() {
		return score;
	}

	protected void setAction(Facts facts, BoardAction action) {
		BoardAction currentAction = facts.get(LFacts.ACTION);
		if (currentAction.compareTo(action) < 0)
			facts.put(LFacts.ACTION, action);
	}
}
