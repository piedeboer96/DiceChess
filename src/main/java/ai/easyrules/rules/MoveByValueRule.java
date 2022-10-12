package ai.easyrules.rules;

import java.util.List;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;
import org.jeasy.rules.api.Facts;

import ai.easyrules.LFacts;
import chess.interfaces.IChessboardSquare;
import chess.interfaces.IChessMove;
import chess.units.Bishop;
import chess.units.King;
import chess.units.Knight;
import chess.units.Pawn;
import chess.units.Queen;
import chess.units.Rook;

/*
  
   ChessMove [owner=ChessPiece [fen=P, team=1, file=1, rank=6], destinations=[ChessBoardSquare [file=1, rank=5], ChessBoardSquare [file=1, rank=4]]] 
   ChessMove [owner=ChessPiece [fen=P, team=1, file=2, rank=6], destinations=[ChessBoardSquare [file=2, rank=5], ChessBoardSquare [file=2, rank=4]]] 
   ChessMove [owner=ChessPiece [fen=P, team=1, file=3, rank=6], destinations=[ChessBoardSquare [file=3, rank=5], ChessBoardSquare [file=3, rank=4]]] 
   ChessMove [owner=ChessPiece [fen=P, team=1, file=4, rank=6], destinations=[ChessBoardSquare [file=4, rank=5], ChessBoardSquare [file=4, rank=4]]] 
   ChessMove [owner=ChessPiece [fen=P, team=1, file=5, rank=6], destinations=[ChessBoardSquare [file=5, rank=5], ChessBoardSquare [file=5, rank=4]]] 
   ChessMove [owner=ChessPiece [fen=P, team=1, file=6, rank=6], destinations=[ChessBoardSquare [file=6, rank=5], ChessBoardSquare [file=6, rank=4]]] 
   ChessMove [owner=ChessPiece [fen=P, team=1, file=7, rank=6], destinations=[ChessBoardSquare [file=7, rank=5], ChessBoardSquare [file=7, rank=4]]]
  
 */


@Rule(name = MoveByValueRule.NAME, description = MoveByValueRule.DESCRIPTION, priority = 1)
public class MoveByValueRule {


	static final String DESCRIPTION = "Add a score according to the type of fen";
	static final String NAME = "- Move By Value    -";

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
		char fen = move.owner().toFen();
		List<IChessboardSquare> possibilities = move.possibilities();

		switch (fen) {
		case 'P':
		case 'p':

			for (IChessboardSquare iChessBoardSquare : possibilities) {
				iChessBoardSquare.addScore(-Pawn.pointValue);
//				System.out.println("increasing score for PAWN rank = " + rank + " file= " + file + " old score "
//						+ oldscore + " new score " + iChessBoardSquare.getScore());
			}

			break;
		case 'B':
		case 'b':

			for (IChessboardSquare iChessBoardSquare : possibilities)

			{
				iChessBoardSquare.addScore(-Bishop.pointValue);
//				System.out.println("increasing score for PAWN rank = " + rank + " file= " + file + " old score "
//						+ oldscore + " new score " + iChessBoardSquare.getScore());

			}

			break;
		case 'K':
		case 'k':

			for (IChessboardSquare iChessBoardSquare : possibilities)

			{
				iChessBoardSquare.addScore(-King.pointValue);
//				System.out.println("increasing score for PAWN rank = " + rank + " file= " + file + " old score "
//						+ oldscore + " new score " + iChessBoardSquare.getScore());

			}

			break;
		case 'N':
		case 'n':

			for (IChessboardSquare iChessBoardSquare : possibilities)

			{
				iChessBoardSquare.addScore(-Knight.pointValue);
//				System.out.println("increasing score for PAWN rank = " + rank + " file= " + file + " old score "
//						+ oldscore + " new score " + iChessBoardSquare.getScore());

			}

			break;
		case 'Q':
		case 'q':

			for (IChessboardSquare iChessBoardSquare : possibilities)

			{
				iChessBoardSquare.addScore(-Queen.pointValue);
//				System.out.println("increasing score for PAWN rank = " + rank + " file= " + file + " old score "
//						+ oldscore + " new score " + iChessBoardSquare.getScore());

			}

			break;
		case 'R':
		case 'r':

			for (IChessboardSquare iChessBoardSquare : possibilities)

			{
				iChessBoardSquare.addScore(-Rook.pointValue);
//				System.out.println("increasing score for PAWN rank = " + rank + " file= " + file + " old score "
//						+ oldscore + " new score " + iChessBoardSquare.getScore());

			}

			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + fen);

		}
	}
}