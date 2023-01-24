//package ai.easyrules.rules;
//
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
//@Rule(name = NewBestActionRule.NAME, description = NewBestActionRule.DESCRIPTION, priority = 50)
//public class NewBestActionRule extends ABaseRule{
//
//	public static final String DESCRIPTION = " Just print the new best move ";
//	public static final String NAME = "- New Best Move    -";
//
//	@Condition
//	public boolean when(@Fact(LFacts.CHESSMOVE) IChessMove move, @Fact(LFacts.ROLL) char rollOne) {
//
//		// Head Guard if the move is not for the same as roll we do not proceed to check if this rule can fire
//		if (!checkRoll(move, rollOne))
//			return false;
//		return true;
//	}
//
//	@Action(order = 1)
//	public void checkBestMove(@Fact(LFacts.BEST_MOVE) IChessMove bestMove, @Fact(LFacts.CHESSMOVE) IChessMove chessMove, Facts facts) throws Exception {
//
//		if (bestMove.owner() == null || bestMove.possibilities().get(0).getScore() < chessMove.possibilities().get(0).getScore()) {
//
//			facts.put(LFacts.OLD_MOVE, bestMove);
//			facts.put(LFacts.BEST_MOVE, chessMove);
//			facts.put(LFacts.BEST_MOVE_CHANGED, Boolean.TRUE);
//
//		} else
//			facts.put(LFacts.BEST_MOVE_CHANGED, Boolean.FALSE);
//
//	}
//
//}