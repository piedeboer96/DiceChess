package ai.easyrules.rules;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;
import org.jeasy.rules.api.Facts;

import ai.easyrules.LFacts;
import chess.interfaces.IChessboardSquare;
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

@Rule(name = MoveForwardRule.NAME, description = MoveForwardRule.DESCRIPTION, priority = 1)
public class MoveForwardRule extends BaseRule {


	static final String DESCRIPTION = "Add 1 score if we can move forward";
	// we want to apply to any moves

	static final String NAME = "- Move Forward     -";

	@Condition
	public boolean when(@Fact(LFacts.CHESSMOVE) IChessMove move, @Fact(LFacts.ROLL) char roll) {

		if (checkRoll(move, roll)) {
			int team = move.owner().team();
			// team 1 white
			IChessboardSquare possibleMove = move.possibilities().get(0);
			int rank = possibleMove.rank();
			if (team == 1 && move.owner().rank() > rank)
				// if it progresses on the board (increase the rank)
				return true;
			else if (team == 0 && move.owner().rank() < rank)
				return true;
		}

		return false;

	}

	@Action(order = 1)
	public void increaseRanking(@Fact(LFacts.CHESSMOVE) IChessMove chessMove) {
		/*
		 * 
		 * ChessMove [owner=ChessPiece [fen=P, team=1, file=0, rank=6], destinations=[ChessBoardSquare [file=0, rank=5], ChessBoardSquare [file=0,rank=4]]]
		 * 
		 */

		evaluateMove(chessMove);

	}
	
	@Action(order = 2)
	public void Finally(Facts facts) throws Exception {
		ai.easyrules.Action currentAction = facts.get(LFacts.ACTION);
		if (currentAction.compareTo(ai.easyrules.Action.ONLY_MOVE)< 0)
			facts.put(LFacts.ACTION, ai.easyrules.Action.ONLY_MOVE);
	}
 
	private void evaluateMove(IChessMove move) {

		IChessboardSquare possibleMove = move.possibilities().get(0);
		possibleMove.addScore(1); // include your evaluation features into the scoring mechanism

	}
}