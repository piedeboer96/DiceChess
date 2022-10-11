package ai.easyrules.rules;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;
import org.jeasy.rules.api.Facts;

import ai.easyrules.LFacts;
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

@Rule(name = NewBestActionRule.NAME, description = NewBestActionRule.DESCRIPTION, priority = 50)
public class NewBestActionRule {

	public static final String DESCRIPTION = " Just print the new best move ";
	public static final String NAME = "- New Best Move    -";

	@Condition
	public boolean when(@Fact(LFacts.CHESSMOVE) IChessMove move, @Fact(LFacts.ROLL) char roll) {

		if (move.owner().toFen() == roll)
			return true;
		return false;
	}

	@Action(order = 1)
	public void checkBestMove(@Fact(LFacts.BEST_MOVE) IChessMove bestMove, @Fact(LFacts.CHESSMOVE) IChessMove chessMove, Facts facts) throws Exception {

		if (bestMove.owner() == null || bestMove.possibilities().get(0).getScore() < chessMove.possibilities().get(0).getScore()) {

			facts.put(LFacts.OLD_MOVE, bestMove);
			facts.put(LFacts.BEST_MOVE, chessMove);
			facts.put(LFacts.BEST_MOVE_CHANGED, Boolean.TRUE);

		} else
			facts.put(LFacts.BEST_MOVE_CHANGED, Boolean.FALSE);

	}

}