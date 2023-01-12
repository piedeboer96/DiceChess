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
//import chess.interfaces.IChessboardSquare;
//
//@Rule(name = MoveForwardRule.NAME, description = MoveForwardRule.DESCRIPTION, priority = 1)
//public class MoveForwardRule extends ABaseRule {
//
//	final static String DESCRIPTION = "Add 1 score if we can move forward";
//	final static String NAME = "- Move Forward     -";
//
//	public MoveForwardRule() {
//		score = 10;
//	}
//
//	@Condition
//	public boolean when(@Fact(LFacts.CHESSMOVE) IChessMove move, @Fact(LFacts.ROLL) char rollOne) {
//
//		// Head Guard if the move is not for the same as roll we do not proceed to check if this rule can fire
//		if (!checkRoll(move, rollOne))
//			return false;
//
//		int team = move.owner().team();
//		// team 1 white
//		IChessboardSquare possibleMove = move.possibilities().get(0);
//		int rank = possibleMove.rank();
//		if (team == 1 && move.owner().rank() > rank)
//			// if it progresses on the board (increase the rank)
//			return true;
//		else if (team == 0 && move.owner().rank() < rank)
//			return true;
//
//	return false;
//	}
//
//	@Action(order = 1)
//	public void increaseRanking(@Fact(LFacts.CHESSMOVE) IChessMove chessMove) {
//
//		IChessboardSquare possibleMove = chessMove.possibilities().get(0);
//		possibleMove.addScore(score); // include your evaluation features into the scoring mechanism
//
//
//	}
//
//	@Action(order = 2)
//	public void Finally(Facts facts) throws Exception {
//		setAction(facts, BoardAction.ONLY_MOVE);
//	}
//
//
//}