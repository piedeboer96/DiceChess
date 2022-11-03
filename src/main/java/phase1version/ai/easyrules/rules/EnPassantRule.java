package phase1version.ai.easyrules.rules;

import static phase1version.ai.easyrules.rules.ruleUtils.checkColumnForPawnFromScratch;
import static phase1version.ai.easyrules.rules.ruleUtils.checkEnpassant;

import phase1version.chess.ChessMatch;
import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;
import org.jeasy.rules.api.Facts;

import phase1version.ai.easyrules.BoardAction;
import phase1version.ai.easyrules.LFacts;
import phase1version.chess.interfaces.IChessMatch;
import phase1version.chess.interfaces.IChessMove;

@Rule(name = EnPassantRule.NAME, description = EnPassantRule.DESCRIPTION, priority = 10) // set priority
public class EnPassantRule extends ABaseRule {
	ChessMatch match;
	int file;
	int team;
	final static String DESCRIPTION = "enpassant move";
	final static String NAME = "- make enpassant move -";

	@Condition
	public boolean when(@Fact(LFacts.CHESSMOVE) IChessMove move, @Fact(LFacts.ROLL) char rollOne, @Fact(LFacts.MATCH) IChessMatch match) {
		this.match = (ChessMatch) match;
		file = move.possibilities().get(0).file();
		team = move.owner().team();

		// Head Guard if the move is not for the same as roll we do not proceed to check if this rule can fire
		if (!checkRoll(move, rollOne))
			return false;

		char pawn = move.owner().toFen();
		if (pawn == 'p' && checkEnpassant(move.owner().team(), move.owner(), match.toFen())) 
		{

			return true;
		}

		return false;
	}

	@Action(order = 1)
	public void makeEnpassant(@Fact(LFacts.CHESSMOVE) IChessMove chessMove) {
		if (checkColumnForPawnFromScratch(file, team, match.toFen())) 
		{
			score = 75;
		} else {
			score = 150;
		}
		chessMove.possibilities().get(0).addScore(score);

	}

	@Action(order = 2)
	public void Finally(Facts facts) throws Exception {
		setAction(facts, BoardAction.ENPASSANT);
	}

}
