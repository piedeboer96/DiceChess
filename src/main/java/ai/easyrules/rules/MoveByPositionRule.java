//package ai.easyrules.rules;
//
//import java.util.List;
//
//import ai.easyrules.BoardAction;
//import ai.easyrules.LFacts;
//import ai.evaluation.PieceSquareTable;
//import org.jeasy.rules.annotation.Action;
//import org.jeasy.rules.annotation.Condition;
//import org.jeasy.rules.annotation.Fact;
//import org.jeasy.rules.annotation.Rule;
//import org.jeasy.rules.api.Facts;
//
//import chess.interfaces.IChessMove;
//import chess.interfaces.IChessboardSquare;
//
//@Rule(name = MoveByPositionRule.NAME, description = MoveByPositionRule.DESCRIPTION, priority = 1)
//public class MoveByPositionRule extends ABaseRule {
//
//	final static String DESCRIPTION = "Move in the high value for the position ";
//	final static String NAME = "- Move By Position -";
//
//	@Condition
//	public boolean when(@Fact(LFacts.CHESSMOVE) IChessMove move, @Fact(LFacts.ROLL) char rollOne) {
//
//		// Head Guard if the move is not for the same as roll we do not proceed to check if this rule can fire
//		if (!checkRoll(move, rollOne))
//			return false;
//
//		return true;
//	}
//
//	@Action(order = 1)
//	public void increaseRanking(@Fact(LFacts.CHESSMOVE) IChessMove chessMove) {
//
//		char fen = chessMove.owner().toFen();
//		List<IChessboardSquare> possibilities = chessMove.possibilities();
//
//
//		IChessboardSquare possibleMove = possibilities.get(0);
//		int rank = possibleMove.rank();
//		int file = possibleMove.file();
//
//		switch (fen) {
//		case 'P':
//		case 'p':
//
//			score = PieceSquareTable.pst_PAWN[rank][file];
//			break;
//		case 'B':
//		case 'b':
//
//			score = PieceSquareTable.pst_BISCHOP[rank][file];
//
//			break;
//		case 'K':
//		case 'k':
//			score = PieceSquareTable.pst_KING_MIDGAME[rank][file];
//			break;
//		case 'N':
//		case 'n':
//			score = PieceSquareTable.pst_KNIGHT[rank][file];
//			break;
//		case 'Q':
//		case 'q':
//			score = PieceSquareTable.pst_QUEEN[rank][file];
//			break;
//		case 'R':
//		case 'r':
//			score = PieceSquareTable.pst_ROOKS[rank][file];
//			break;
//		default:
//			throw new IllegalArgumentException("Unexpected value: " + fen);
//		}
//
//		possibleMove.addScore(score);
//	}
//
//	@Action(order = 2)
//	public void Finally(Facts facts) throws Exception {
//		setAction(facts, BoardAction.ONLY_MOVE);
//	}
//
//
//}