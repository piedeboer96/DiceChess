//package ai.easyrules.rules;
//
//import ai.easyrules.BoardAction;
//import ai.easyrules.LFacts;
//import game.DiceChess;
//import game.Opportunity;
//import org.jeasy.rules.annotation.Action;
//import org.jeasy.rules.annotation.Condition;
//import org.jeasy.rules.annotation.Fact;
//import org.jeasy.rules.annotation.Rule;
//import org.jeasy.rules.api.Facts;
//
//@Rule(name = EnPassantRule.NAME, description = EnPassantRule.DESCRIPTION, priority = 10) // set priority
//public class EnPassantRule extends ABaseRule {
//	DiceChess match;
//	int file;
//	int team;
//	final static String DESCRIPTION = "enpassant move";
//	final static String NAME = "- make enpassant move -";
//
//	@Condition
//	public boolean when(@Fact(LFacts.CHESSMOVE) Opportunity move, @Fact(LFacts.ROLL) char rollOne, @Fact(LFacts.MATCH) DiceChess match) {
//		this.match = (DiceChess) match;
//		file = move.options().get(0).file();
//		team = move.owner().color();
//
//		// Head Guard if the move is not for the same as roll we do not proceed to check if this rule can fire
//		if (!checkRoll(move, rollOne))
//			return false;
//
//		char pawn = move.owner().toString().charAt(0);
//		if (pawn == 'p' && ruleUtils.checkEnpassant(move.owner().color(), move.owner(), match.toString()))
//		{
//
//			return true;
//		}
//
//		return false;
//	}
//
//	@Action(order = 1)
//	public void makeEnpassant(@Fact(LFacts.CHESSMOVE) IChessMove chessMove) {
//		if (ruleUtils.checkColumnForPawnFromScratch(file, team, match.toFen()))
//		{
//			score = 75;
//		} else {
//			score = 150;
//		}
//		chessMove.possibilities().get(0).addScore(score);
//
//	}
//
//	@Action(order = 2)
//	public void Finally(Facts facts) throws Exception {
//		setAction(facts, BoardAction.ENPASSANT);
//	}
//
//}
