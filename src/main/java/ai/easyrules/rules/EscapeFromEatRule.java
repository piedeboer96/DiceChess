//package ai.easyrules.rules;
//
//import java.util.List;
//
//import ai.easyrules.BoardAction;
//import ai.easyrules.LFacts;
//import ai.easyrules.Utils;
//import org.jeasy.rules.annotation.Action;
//import org.jeasy.rules.annotation.Condition;
//import org.jeasy.rules.annotation.Fact;
//import org.jeasy.rules.annotation.Rule;
//import org.jeasy.rules.api.Facts;
//
//@Rule(name = EscapeFromEatRule.NAME, description = EscapeFromEatRule.DESCRIPTION, priority = 1)
//public class EscapeFromEatRule extends ABaseRule{
//	final static  String DESCRIPTION = "If the piece is under Attack let's try to escape";
//	final static   String NAME = "- Escape from EAT  -";
//
//	@Condition
//	public boolean when(@Fact(LFacts.CHESSMOVE) IChessMove move, @Fact(LFacts.MATCH) ChessMatch match, @Fact(LFacts.ROLL) char rollOne) {
//		// Head Guard if the move is not for the same as roll we do not proceed to check if this rule can fire
//		if (!checkRoll(move, rollOne))
//			return false;
//
//		int file = move.possibilities().get(0).file();
//		int rank = move.possibilities().get(0).rank();
//
//		int otherPlayer = match.getPlayer() == 1 ? 0 : 1;
//		List<IChessMove> generateMovesOf = match.generateMovesOf(otherPlayer);
//
//		// if the opponent can move and eat in our destintaion the we activate the rule
//		List<IChessMove> splitMoves = Utils.splitMoves(generateMovesOf);
//		for (IChessMove opponentMove : splitMoves) {
//			if (opponentMove.possibilities().get(0).file() == file
//					&& opponentMove.possibilities().get(0).rank() == rank)
//				return true;
//		}
//
//		return false;
//	}
//
//	@Action(order = 1)
//	public void increaseRanking(@Fact(LFacts.CHESSMOVE) IChessMove chessMove) {
//		char fen = chessMove.owner().toFen();
//		List<IChessboardSquare> possibilities = chessMove.possibilities();
//		IChessboardSquare iChessBoardSquare = possibilities.get(0);
//
//		switch (fen) {
//			case 'P', 'p' -> score = -Pawn.pointValue;
//			case 'B', 'b' -> score = -Bishop.pointValue;
//			case 'K', 'k' -> score = -King.pointValue;
//			case 'N', 'n' -> score = -Knight.pointValue;
//			case 'Q', 'q' -> score = -Queen.pointValue;
//			case 'R', 'r' -> score = -Rook.pointValue;
//			default -> throw new IllegalArgumentException("Unexpected value: " + fen);
//		}
//
//		iChessBoardSquare.addScore(score);
//	}
//
//	@Action(order = 2)
//	public void Finally(Facts facts) throws Exception {
//		setAction(facts, BoardAction.ONLY_MOVE);
//	}
//
//
//}