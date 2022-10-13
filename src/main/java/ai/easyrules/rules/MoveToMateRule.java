package ai.easyrules.rules;

import java.util.List;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;
import org.jeasy.rules.api.Facts;

import ai.easyrules.LFacts;
import ai.easyrules.Utils;
import chess.interfaces.IChessMatch;
import chess.interfaces.IChessMove;
import chess.interfaces.IChessPiece;
import chess.interfaces.IChessboardSquare;
import chess.units.ChessPiece;
import chess.utility.ChessboardSquare;

/*
  
   ChessMove [owner=ChessPiece [fen=P, team=1, file=1, rank=6], destinations=[ChessBoardSquare [file=1, rank=5], ChessBoardSquare [file=1, rank=4]]] 
   ChessMove [owner=ChessPiece [fen=P, team=1, file=2, rank=6], destinations=[ChessBoardSquare [file=2, rank=5], ChessBoardSquare [file=2, rank=4]]] 
   ChessMove [owner=ChessPiece [fen=P, team=1, file=3, rank=6], destinations=[ChessBoardSquare [file=3, rank=5], ChessBoardSquare [file=3, rank=4]]] 
   ChessMove [owner=ChessPiece [fen=P, team=1, file=4, rank=6], destinations=[ChessBoardSquare [file=4, rank=5], ChessBoardSquare [file=4, rank=4]]] 
   ChessMove [owner=ChessPiece [fen=P, team=1, file=5, rank=6], destinations=[ChessBoardSquare [file=5, rank=5], ChessBoardSquare [file=5, rank=4]]] 
   ChessMove [owner=ChessPiece [fen=P, team=1, file=6, rank=6], destinations=[ChessBoardSquare [file=6, rank=5], ChessBoardSquare [file=6, rank=4]]] 
   ChessMove [owner=ChessPiece [fen=P, team=1, file=7, rank=6], destinations=[ChessBoardSquare [file=7, rank=5], ChessBoardSquare [file=7, rank=4]]]
  
 */

@Rule(name = MoveToMateRule.NAME, description = MoveToMateRule.DESCRIPTION, priority = 10)
public class MoveToMateRule extends BaseRule {
	static final String DESCRIPTION = "Add a score if we can push to mate";
	static final String NAME = "- Move to Mate Rule-";

	 public MoveToMateRule() {
		score=2000;
	}
	@Condition
	public boolean when(@Fact(LFacts.CHESSMOVE) IChessMove move, @Fact(LFacts.MATCH) IChessMatch match, @Fact(LFacts.ROLL) char roll) {

		if (!checkRoll(move, roll))
			return false;

		int destFile = move.possibilities().get(0).file();
		int destRank = move.possibilities().get(0).rank();

		ChessPiece piece = (ChessPiece) move.owner();

		ChessPiece newPiece = Utils.clone(piece);

		// we set the new position on the possible move
		newPiece.setPosition(new ChessboardSquare(destFile, destRank));

		// now let's generate all possible moves ...
		List<IChessMove> movesOf = match.generateMovesOf(newPiece);
		movesOf = Utils.splitMoves(movesOf);

		// we must find the opponent KING
		IChessPiece king = match.getKing(move.owner().team() == 1 ? 0 : 1);
		int kingFile = king.file();
		int kingRank = king.rank();

		for (IChessMove moveNew : movesOf) {
			List<IChessboardSquare> possibilities = moveNew.possibilities();

			for (IChessboardSquare boardSquare : possibilities) {

				// now let's check if with in a possible move we can reach the opponent KING
				if (boardSquare.file() == kingFile && boardSquare.rank() == kingRank)
					return true;
			}

		}

		return false;
	}

	@Action(order = 1)
	public void attackMove(@Fact(LFacts.CHESSMOVE) IChessMove chessMove) {
		/*
		 * 
		 * ChessMove [owner=ChessPiece [fen=P, team=1, file=0, rank=6], destinations=[ChessBoardSquare [file=0, rank=5], ChessBoardSquare [file=0,rank=4]]]
		 * 
		 */
//		System.out.println("");
		evaluateMove(chessMove);
//		System.out.println("");

	}

	@Action(order = 2)
	public void Finally(Facts facts) throws Exception {
		ai.easyrules.Action currentAction = facts.get(LFacts.ACTION);
		if (currentAction.compareTo(ai.easyrules.Action.ONLY_MOVE) < 0)
			facts.put(LFacts.ACTION, ai.easyrules.Action.ONLY_MOVE);
	}

	private void evaluateMove(IChessMove move) {

		List<IChessboardSquare> possibilities = move.possibilities();

		possibilities.get(0).addScore(score);
	}
}