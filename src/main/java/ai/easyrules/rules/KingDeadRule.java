package ai.easyrules.rules;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;
import org.jeasy.rules.api.Facts;

import ai.easyrules.LFacts;
import chess.interfaces.IChessMatch;
import chess.interfaces.IChessMove;
import chess.interfaces.IChessPiece;

/*
  
   ChessMove [owner=ChessPiece [fen=P, team=1, file=1, rank=6], destinations=[ChessBoardSquare [file=1, rank=5], ChessBoardSquare [file=1, rank=4]]] 
   ChessMove [owner=ChessPiece [fen=P, team=1, file=2, rank=6], destinations=[ChessBoardSquare [file=2, rank=5], ChessBoardSquare [file=2, rank=4]]] 
   ChessMove [owner=ChessPiece [fen=P, team=1, file=3, rank=6], destinations=[ChessBoardSquare [file=3, rank=5], ChessBoardSquare [file=3, rank=4]]] 
   ChessMove [owner=ChessPiece [fen=P, team=1, file=4, rank=6], destinations=[ChessBoardSquare [file=4, rank=5], ChessBoardSquare [file=4, rank=4]]] 
   ChessMove [owner=ChessPiece [fen=P, team=1, file=5, rank=6], destinations=[ChessBoardSquare [file=5, rank=5], ChessBoardSquare [file=5, rank=4]]] 
   ChessMove [owner=ChessPiece [fen=P, team=1, file=6, rank=6], destinations=[ChessBoardSquare [file=6, rank=5], ChessBoardSquare [file=6, rank=4]]] 
   ChessMove [owner=ChessPiece [fen=P, team=1, file=7, rank=6], destinations=[ChessBoardSquare [file=7, rank=5], ChessBoardSquare [file=7, rank=4]]]
  
 */



@Rule(name = KingDeadRule.NAME, description = KingDeadRule.DESCRIPTION, priority = 20)
public class KingDeadRule extends BaseRule{

	static final String DESCRIPTION = "End of the game because the king is dead ";
	static final String NAME = "- King  Dead       -";
	int currentScore = 0;
	private IChessPiece opponentPiece;

	@Condition
	public boolean when(@Fact(LFacts.CHESSMOVE) IChessMove move, @Fact(LFacts.MATCH) IChessMatch match,@Fact(LFacts.ROLL) char roll ) {
		opponentPiece = match.get(move.possibilities().get(0));
		if ( checkRoll(move, roll) && opponentPiece != null
				&& (opponentPiece.toFen() == 'K' || opponentPiece.toFen() == 'k')) {

			return true;

		}
		return false;
	}

	@Action(order = 1)
	public void kingIsDead(@Fact("ChessMove") IChessMove chessMove) {
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
		if (currentAction.compareTo(ai.easyrules.Action.FINISH_MATCH) < 0)
			facts.put(LFacts.ACTION, ai.easyrules.Action.FINISH_MATCH);
	}
	
 
	private void evaluateMove(IChessMove move) {
		move.possibilities().get(0).addScore(100000);
	}
}