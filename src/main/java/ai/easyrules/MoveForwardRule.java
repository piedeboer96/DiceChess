package ai.easyrules;

import java.util.Iterator;
import java.util.List;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;

import org.jeasy.rules.annotation.Rule;
import org.jeasy.rules.api.Facts;

import chess.interfaces.IChessBoardSquare;
import chess.utility.ChessMove;
 

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
	public boolean when() {

		return true;
	}

	@Action
	public void increaseRanking(Facts facts) {
		/*
		 * 
		 * ChessMove [owner=ChessPiece [fen=P, team=1, file=0, rank=6], destinations=[ChessBoardSquare [file=0, rank=5], ChessBoardSquare [file=0,rank=4]]]
		 * 
		 */

		ChessMove best=facts.get("best");
		
		
		for (Iterator<org.jeasy.rules.api.Fact<?>> iterator2 = facts.iterator(); iterator2.hasNext();) {
			Object o = iterator2.next();
			if (o instanceof org.jeasy.rules.api.Fact) {
				@SuppressWarnings("rawtypes")
				org.jeasy.rules.api.Fact f = (org.jeasy.rules.api.Fact) o;
				Object value = f.getValue();
				if (value instanceof ChessMove) {

					ChessMove chessMove = (ChessMove) value;
					System.out.println("********Evaluating ********");
					System.out.println(chessMove);
					evaluateMove(chessMove);
					best=checkIfBest(facts, chessMove,best);
					System.out.println("***************************");
					System.out.println("");

				}

			}

		}
		System.out.println("current best is "+best);
		facts.put("best", best);

	}

	private ChessMove checkIfBest(Facts facts, ChessMove chessMove, ChessMove best) {
		 

		int maxBest = Utils.findMaxScore(best);
		int maxMove = Utils.findMaxScore(chessMove);

		if (maxBest < maxMove)
			return chessMove;
		else
			return best;

	}

	

	private void evaluateMove(ChessMove move) {
		int team = move.owner().team();
		System.out.println("Evaluating team " + team);
		//team 1 white
		if (team == 1) {
			List<IChessBoardSquare> possibilities = move.possibilities();
			for (IChessBoardSquare possibleMove : possibilities) {
				if (move.owner().rank() > possibleMove.rank()) {
					possibleMove.addScore(1);
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