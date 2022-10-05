package ai.easyrules;

import java.util.List;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;

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

@Rule
public class MoveForwardRule {
	static final String MOVE_FORWARD = "Move forward";

	@Condition
	public boolean when(@Fact("ChessMove") IChessMove move) {

		return true;
	}

	@Action
	public void increaseRanking(@Fact("ChessMove") IChessMove chessMove) {
		/*
		 * 
		 * ChessMove [owner=ChessPiece [fen=P, team=1, file=0, rank=6], destinations=[ChessBoardSquare [file=0, rank=5], ChessBoardSquare [file=0,rank=4]]]
		 * 
		 */

		System.out.println("********Evaluating ********");

		evaluateMove(chessMove);

		System.out.println("***************************");
		System.out.println("");

	}

	

	private void evaluateMove(IChessMove move) {
		int team = move.owner().team();
		System.out.println("Evaluating team " + team);
		// team 1 white
		if (team == 1) {
			List<IChessBoardSquare> possibilities = move.possibilities();

			for (IChessBoardSquare possibleMove : possibilities) {
				//if it progresses on the board (increase the rank)



				if (move.owner().rank() > possibleMove.rank()) {

					possibleMove.addScore(1);			// include your evaluation features into the scoring mechanism

					System.out.println("increasing score for team 1" + possibleMove);

				} else

					System.out.println("No Scores score for" + possibleMove);
			}

		} else {
			List<IChessBoardSquare> possibilities = move.possibilities();
			for (IChessBoardSquare possibleMove : possibilities) {
				if (move.owner().rank() < possibleMove.rank()) {
					possibleMove.addScore(1);
					System.out.println("increasing  score for team 0 " + possibleMove);

				} else
					System.out.println("No Scores score for" + possibleMove);
			}

		}
	}
}