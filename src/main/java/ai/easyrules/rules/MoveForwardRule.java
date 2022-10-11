package ai.easyrules.rules;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;
import org.jeasy.rules.api.Facts;

import ai.easyrules.LFacts;
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

 
@Rule(name = "- Move Forward     -", description = "Add 1 score if we can move forward", priority = 1)
public class MoveForwardRule {

	private IChessMove chessMove;

	// we want to apply to any moves

	@Condition
	public boolean when(@Fact(LFacts.CHESSMOVE) IChessMove move,@Fact(LFacts.ROLL) char roll ) {
 
		if ( move.owner().toFen() == roll ) 
			return true;
 
		return false;
	 
	}

	@Action(order = 1)
	public void increaseRanking(@Fact(LFacts.CHESSMOVE) IChessMove chessMove) {
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

		IChessMove best = facts.get(LFacts.BEST_MOVE);

		if (best.owner() == null
				|| best.possibilities().get(0).getScore() < chessMove.possibilities().get(0).getScore()) {
			facts.put(LFacts.BEST_MOVE, chessMove);
			facts.put(LFacts.ACTION, ai.easyrules.Action.ONLY_MOVE);
		}
	}

	private void evaluateMove(IChessMove move) {
		int team = move.owner().team();
		// team 1 white
		IChessBoardSquare possibleMove = move.possibilities().get(0);
		int rank = possibleMove.rank();

		if (team == 1) {

			// if it progresses on the board (increase the rank)

			if (move.owner().rank() > rank) {

				possibleMove.addScore(1); // include your evaluation features into the scoring mechanism


			}

		} else {

			{
				if (move.owner().rank() < rank) {

					possibleMove.addScore(1);

				}
			}

		}
	}
}