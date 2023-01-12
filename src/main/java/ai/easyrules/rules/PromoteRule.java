//package ai.easyrules.rules;
//
//import ai.easyrules.BoardAction;
//import ai.easyrules.LFacts;
//import org.jeasy.rules.annotation.Action;
//import org.jeasy.rules.annotation.Condition;
//import org.jeasy.rules.annotation.Fact;
//import org.jeasy.rules.annotation.Rule;
//import org.jeasy.rules.api.Facts;
//
//import chess.interfaces.IChessMove;
//
///*
//
//
// */
//
//@Rule(name = PromoteRule.NAME, description = PromoteRule.DESCRIPTION, priority = 10)
//public class PromoteRule extends ABaseRule {
//
//	final static String DESCRIPTION = "Promote the pawn in the first opponent line ";
//	final static String NAME = "- Promote to Queen -";
//	int currentScore = 0;
//
//	public PromoteRule() {
//		score = 1500;
//	}
//
//	@Condition
//	public boolean when(@Fact(LFacts.CHESSMOVE) IChessMove move, @Fact(LFacts.ROLL) char rollOne)
//	{
//
//		// Head Guard if the move is not for the same as roll we do not proceed to check if this rule can fire
//		if (!checkRoll(move, rollOne))
//			return false;
//
//
//		int rank = move.possibilities().get(0).rank();
//		char pawn = move.owner().toFen();
//		if (pawn == 'p' && rank == 7)
//			return true;
//		else if (pawn == 'P' && rank == 0)
//			return true;
//
//		return false;
//	}
//
//	@Action(order = 1)
//	public void bestPromote(@Fact(LFacts.CHESSMOVE) IChessMove chessMove) {
//		chessMove.possibilities().get(0).addScore(score);
//	}
//
//	@Action(order = 2)
//	public void Finally(Facts facts) throws Exception {
//		setAction(facts, BoardAction.MOVE_AND_PROMOTE);
//	}
//
//}