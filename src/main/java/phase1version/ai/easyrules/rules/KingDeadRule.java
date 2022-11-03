package phase1version.ai.easyrules.rules;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;
import org.jeasy.rules.api.Facts;

import phase1version.ai.easyrules.BoardAction;
import phase1version.ai.easyrules.LFacts;
import phase1version.chess.interfaces.IChessMatch;
import phase1version.chess.interfaces.IChessMove;
import phase1version.chess.interfaces.IChessPiece;

@Rule(name = KingDeadRule.NAME, description = KingDeadRule.DESCRIPTION, priority = 20)
public class KingDeadRule extends ABaseRule {

	final static String DESCRIPTION = "End of the game because the king is dead ";
	final static String NAME = "- King  Dead       -";

	private IChessPiece opponentPiece;

	public KingDeadRule() {
		score = 100000;
	}

	@Condition
	public boolean when(@Fact(LFacts.CHESSMOVE) IChessMove move, @Fact(LFacts.MATCH) IChessMatch match, @Fact(LFacts.ROLL) char rollOne) {

		if (!checkRoll(move, rollOne))
			return false;

		opponentPiece = match.get(move.possibilities().get(0));
		if (opponentPiece != null && (opponentPiece.toFen() == 'K' || opponentPiece.toFen() == 'k'))
			return true;

		return false;
	}

	@Action(order = 1)
	public void kingIsDead(@Fact("ChessMove") IChessMove chessMove) {

		chessMove.possibilities().get(0).addScore(score);
	}

	@Action(order = 2)
	public void Finally(Facts facts) throws Exception {
		setAction(facts, BoardAction.FINISH_MATCH);
	}

}