//package ai.easyrules.rules;
//
//import java.util.List;
//
//import ai.easyrules.BoardAction;
//import ai.easyrules.LFacts;
//import ai.evaluation.PointValue;
//import game.ChessPiece;
//import game.DiceChess;
//import game.Opportunity;
//import game.Square;
//import org.jeasy.rules.annotation.Action;
//import org.jeasy.rules.annotation.Condition;
//import org.jeasy.rules.annotation.Fact;
//import org.jeasy.rules.annotation.Rule;
//import org.jeasy.rules.api.Facts;
//
//
///*
//
//   ChessMove [owner=ChessPiece [fen=P, team=1, file=1, rank=6], destinations=[ChessBoardSquare [file=1, rank=5], ChessBoardSquare [file=1, rank=4]]]
//   ChessMove [owner=ChessPiece [fen=P, team=1, file=2, rank=6], destinations=[ChessBoardSquare [file=2, rank=5], ChessBoardSquare [file=2, rank=4]]]
//   ChessMove [owner=ChessPiece [fen=P, team=1, file=3, rank=6], destinations=[ChessBoardSquare [file=3, rank=5], ChessBoardSquare [file=3, rank=4]]]
//   ChessMove [owner=ChessPiece [fen=P, team=1, file=4, rank=6], destinations=[ChessBoardSquare [file=4, rank=5], ChessBoardSquare [file=4, rank=4]]]
//   ChessMove [owner=ChessPiece [fen=P, team=1, file=5, rank=6], destinations=[ChessBoardSquare [file=5, rank=5], ChessBoardSquare [file=5, rank=4]]]
//   ChessMove [owner=ChessPiece [fen=P, team=1, file=6, rank=6], destinations=[ChessBoardSquare [file=6, rank=5], ChessBoardSquare [file=6, rank=4]]]
//   ChessMove [owner=ChessPiece [fen=P, team=1, file=7, rank=6], destinations=[ChessBoardSquare [file=7, rank=5], ChessBoardSquare [file=7, rank=4]]]
//
// */
//
//@Rule(name = AttackRule.NAME, description = AttackRule.DESCRIPTION, priority = 10)
//public class AttackRule extends ABaseRule {
//
//	final static String DESCRIPTION = "Add a score according to capturing a piece";
//	final static String NAME = "- Attack Rule       -";
//
//	private ChessPiece opponentPiece;
//
//	@Condition
//	public boolean when(@Fact(LFacts.CHESSMOVE) Opportunity move, @Fact(LFacts.MATCH) DiceChess match, @Fact(LFacts.ROLL) char rollOne) {
//
//		// Head Guard if the move is not for the same as roll we do not proceed to check if this rule can fire
//		if (!checkRoll(move, rollOne)) {
//			return false;
//		}
//
//		opponentPiece = match.getBoard().read(move.options().get(0));
//		return opponentPiece != null;
//	}
//
//	@Action(order = 1)
//	public void attackMove(@Fact(LFacts.CHESSMOVE) Opportunity chessMove) {
//		char fen = opponentPiece.toString().charAt(0);
//		List<Square> possibilities = chessMove.options();
//		switch (fen) {
//			case 'P', 'p' -> score = 2 * PointValue.PAWN;
//			case 'B', 'b' -> score = 2 * PointValue.BISHOP;
//			case 'K', 'k' -> score = 2 * PointValue.KING;
//			case 'N', 'n' -> score = 2 * PointValue.KNIGHT;
//			case 'Q', 'q' -> score = 2 * PointValue.QUEEN;
//			case 'R', 'r' -> score = 2 * PointValue.ROOK;
//			default -> throw new IllegalArgumentException("Unexpected value: " + fen);
//		}
//		possibilities.get(0).addScore(score);
//	}
//
//	@Action(order = 2)
//	public void Finally(Facts facts) throws Exception {
//		setAction(facts, BoardAction.ONLY_MOVE);
//
//	}
//}