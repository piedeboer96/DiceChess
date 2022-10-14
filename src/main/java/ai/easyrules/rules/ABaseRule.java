package ai.easyrules.rules;

import org.jeasy.rules.api.Facts;

import ai.easyrules.BoardAction;
import ai.easyrules.LFacts;
import chess.interfaces.IChessMove;

public abstract class ABaseRule {

	protected int score = 0;
	
	public boolean checkRoll(IChessMove move, char rollOne) {
		return move.owner().toFen() == rollOne || rollOne == 'x';
	}

	public int getScore() {
		return score;
	}

	protected void setAction(Facts facts, BoardAction action) {
		ai.easyrules.BoardAction currentAction = facts.get(LFacts.ACTION);
		if (currentAction.compareTo(action) < 0)
			facts.put(LFacts.ACTION, action);
	}
}
