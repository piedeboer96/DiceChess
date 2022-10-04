package engine.test;

import java.util.List;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;

import chess.interfaces.IChessBoardSquare;
import chess.utility.ChessMove;

/**
 * 
 * 
 * Roll P Dumping Moves :
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * ChessMove [owner=ChessPiece [fen=P, team=1, file=1, rank=6],
 * destinations=[ChessBoardSquare [file=1, rank=5], ChessBoardSquare [file=1,
 * rank=4]]] ChessMove [owner=ChessPiece [fen=P, team=1, file=2, rank=6],
 * destinations=[ChessBoardSquare [file=2, rank=5], ChessBoardSquare [file=2,
 * rank=4]]] ChessMove [owner=ChessPiece [fen=P, team=1, file=3, rank=6],
 * destinations=[ChessBoardSquare [file=3, rank=5], ChessBoardSquare [file=3,
 * rank=4]]] ChessMove [owner=ChessPiece [fen=P, team=1, file=4, rank=6],
 * destinations=[ChessBoardSquare [file=4, rank=5], ChessBoardSquare [file=4,
 * rank=4]]] ChessMove [owner=ChessPiece [fen=P, team=1, file=5, rank=6],
 * destinations=[ChessBoardSquare [file=5, rank=5], ChessBoardSquare [file=5,
 * rank=4]]] ChessMove [owner=ChessPiece [fen=P, team=1, file=6, rank=6],
 * destinations=[ChessBoardSquare [file=6, rank=5], ChessBoardSquare [file=6,
 * rank=4]]] ChessMove [owner=ChessPiece [fen=P, team=1, file=7, rank=6],
 * destinations=[ChessBoardSquare [file=7, rank=5], ChessBoardSquare [file=7,
 * rank=4]]]
 * 
 */

@Rule
public class MoveForwardRule {
	static final String MOVE_FORWARD = "Move forward";

	@Condition
	public boolean isMoveForwad(@Fact("move") ChessMove move) {

		if (move.possibilities().size() > 0)
			return true;
		else
			return false;
	}

	@Action
	public void increaseRanking(@Fact("move") ChessMove move) {
		// ChessMove [owner=ChessPiece [fen=P, team=1, file=0, rank=6],
		// destinations=[ChessBoardSquare [file=0, rank=5], ChessBoardSquare [file=0,
		// rank=4]]]
		int team = move.owner().team();
		if (team == 1) {
			List<IChessBoardSquare> possibilities = move.possibilities();
			for (IChessBoardSquare possibleMove : possibilities) {
				if (move.owner().rank() < possibleMove.rank()) {
					possibleMove.addScore(1);
					System.out.println("increasing score for" + possibleMove);

				}
			}

		} else {
			List<IChessBoardSquare> possibilities = move.possibilities();
			for (IChessBoardSquare possibleMove : possibilities) {
				if (move.owner().rank() > possibleMove.rank()) {
					possibleMove.addScore(1);
					System.out.println("decrease score for" + possibleMove);

				}
			}
		}

	}
}
