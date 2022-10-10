package ai.easyrules;

import java.util.List;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;
import org.jeasy.rules.api.Facts;

import chess.interfaces.IChessBoardSquare;
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


@Rule(name = "- Move By Value    -", description = "Add a score according to the type of fen", priority = 10)
public class MoveByValueRule {

	private IChessMove chessMove;

	@Condition
	public boolean when(@Fact("ChessMove") IChessMove move, @Fact("ROLL") char roll) {
		if (move.owner().toFen() == roll) {
			return true;
		}
		return false;
	}

	@Action(order = 1)
	public void increaseRanking(@Fact("ChessMove") IChessMove chessMove) {
		this.chessMove = chessMove;
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

		IChessMove best = facts.get(EasyRuleEngine.BEST_MOVE);

		if (best.owner() == null
				|| best.possibilities().get(0).getScore() < chessMove.possibilities().get(0).getScore()) {
			facts.put(EasyRuleEngine.BEST_MOVE, chessMove);
			facts.put(EasyRuleEngine.ACTION, ai.easyrules.Action.ONLY_MOVE);
		}
	}

	private void evaluateMove(IChessMove move) {
		char fen = move.owner().toFen();
		List<IChessBoardSquare> possibilities = move.possibilities();

		switch (fen) {
		case 'P':
		case 'p':

			for (IChessBoardSquare iChessBoardSquare : possibilities) {
				iChessBoardSquare.addScore(-Pawn.pointValue);
//				System.out.println("increasing score for PAWN rank = " + rank + " file= " + file + " old score "
//						+ oldscore + " new score " + iChessBoardSquare.getScore());
			}

			break;
		case 'B':
		case 'b':

			for (IChessBoardSquare iChessBoardSquare : possibilities)

			{
				iChessBoardSquare.addScore(-Bishop.pointValue);
//				System.out.println("increasing score for PAWN rank = " + rank + " file= " + file + " old score "
//						+ oldscore + " new score " + iChessBoardSquare.getScore());

			}

			break;
		case 'K':
		case 'k':

			for (IChessBoardSquare iChessBoardSquare : possibilities)

			{
				iChessBoardSquare.addScore(-King.pointValue);
//				System.out.println("increasing score for PAWN rank = " + rank + " file= " + file + " old score "
//						+ oldscore + " new score " + iChessBoardSquare.getScore());

			}

			break;
		case 'N':
		case 'n':

			for (IChessBoardSquare iChessBoardSquare : possibilities)

			{
				iChessBoardSquare.addScore(-Knight.pointValue);
//				System.out.println("increasing score for PAWN rank = " + rank + " file= " + file + " old score "
//						+ oldscore + " new score " + iChessBoardSquare.getScore());

			}

			break;
		case 'Q':
		case 'q':

			for (IChessBoardSquare iChessBoardSquare : possibilities)

			{
				iChessBoardSquare.addScore(-Queen.pointValue);
//				System.out.println("increasing score for PAWN rank = " + rank + " file= " + file + " old score "
//						+ oldscore + " new score " + iChessBoardSquare.getScore());

			}

			break;
		case 'R':
		case 'r':

			for (IChessBoardSquare iChessBoardSquare : possibilities)

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