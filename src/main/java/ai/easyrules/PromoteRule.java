package ai.easyrules;

import java.util.List;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;
import org.jeasy.rules.api.Facts;

import ai.evaluation.PieceSquareTable;
import chess.interfaces.IChessBoardSquare;
import chess.interfaces.IChessMove;

/*
  
   ChessMove [owner=ChessPiece [fen=P, team=1, file=1, rank=6], destinations=[ChessBoardSquare [file=1, rank=5], ChessBoardSquare [file=1, rank=4]]] 
   ChessMove [owner=ChessPiece [fen=P, team=1, file=2, rank=6], destinations=[ChessBoardSquare [file=2, rank=5], ChessBoardSquare [file=2, rank=4]]] 
   ChessMove [owner=ChessPiece [fen=P, team=1, file=3, rank=6], destinations=[ChessBoardSquare [file=3, rank=5], ChessBoardSquare [file=3, rank=4]]] 
   ChessMove [owner=ChessPiece [fen=P, team=1, file=4, rank=6], destinations=[ChessBoardSquare [file=4, rank=5], ChessBoardSquare [file=4, rank=4]]] 
   ChessMove [owner=ChessPiece [fen=P, team=1, file=5, rank=6], destinations=[ChessBoardSquare [file=5, rank=5], ChessBoardSquare [file=5, rank=4]]] 
   ChessMove [owner=ChessPiece [fen=P, team=1, file=6, rank=6], destinations=[ChessBoardSquare [file=6, rank=5], ChessBoardSquare [file=6, rank=4]]] 
   ChessMove [owner=ChessPiece [fen=P, team=1, file=7, rank=6], destinations=[ChessBoardSquare [file=7, rank=5], ChessBoardSquare [file=7, rank=4]]]
  
 */

@Rule(name = "my promote rule", description = "Promote the pawn in the first opponent line ", priority = 1)
public class PromoteRule {

	int currentScore = 0;
	private IChessMove chessMove;

	@Condition
	public boolean when(@Fact("ChessMove") IChessMove move, @Fact("ROLL") char roll) {
		if (move.owner().toFen() != roll) {
			return false;
		}
		int rank = move.possibilities().get(0).rank();
		char pawn = move.owner().toFen();
		if (pawn == 'p' && rank == 7) {
			return true;
		} else if (pawn == 'P' && rank == 0) {
			return true;
		}
		;
		return false;
	}

	@Action(order = 1)
	public void bestPromote(@Fact("ChessMove") IChessMove chessMove) {
		/*
		 * 
		 * ChessMove [owner=ChessPiece [fen=P, team=1, file=0, rank=6], destinations=[ChessBoardSquare [file=0, rank=5], ChessBoardSquare [file=0,rank=4]]]
		 * 
		 */
		this.chessMove = chessMove;
		evaluateMove(chessMove);
	}

	@Action(order = 2)
	public void Finally(Facts facts) throws Exception {

		IChessMove best = facts.get(EasyRuleEngine.BEST_MOVE);

		if (best.owner() == null
				|| best.possibilities().get(0).getScore() < chessMove.possibilities().get(0).getScore())
			facts.put(EasyRuleEngine.BEST_MOVE, chessMove);

		facts.put(EasyRuleEngine.ACTION, ai.easyrules.Action.MOVE_AND_PROMOTE);

	}

	private void evaluateMove(IChessMove move) {
		move.possibilities().get(0).addScore(1500);
	}
}