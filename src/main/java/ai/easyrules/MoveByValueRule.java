package ai.easyrules;

import java.util.List;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;

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

@Rule
public class MoveByValueRule {
	static final String MOVE_BY_VALUE = "Move in the high value ";

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
		char fen = move.owner().toFen();
		List<IChessBoardSquare> possibilities = move.possibilities();

		switch (fen) {
		case 'P':
		case 'p':

			for (IChessBoardSquare iChessBoardSquare : possibilities)
				iChessBoardSquare.addScore(-Pawn.pointValue);

			break;
		case 'B':
		case 'b':

			for (IChessBoardSquare iChessBoardSquare : possibilities)
				iChessBoardSquare.addScore(-Bishop.pointValue);

			break;
		case 'K':
		case 'k':

			for (IChessBoardSquare iChessBoardSquare : possibilities)
				iChessBoardSquare.addScore(-King.pointValue);

			break;
		case 'N':
		case 'n':

			for (IChessBoardSquare iChessBoardSquare : possibilities)
				iChessBoardSquare.addScore(-Knight.pointValue);
			break;
		case 'Q':
		case 'q':

			for (IChessBoardSquare iChessBoardSquare : possibilities)
				iChessBoardSquare.addScore(-Queen.pointValue);

			break;
		case 'R':
		case 'r':

			for (IChessBoardSquare iChessBoardSquare : possibilities)
				iChessBoardSquare.addScore(-Rook.pointValue);
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + fen);

		}
	}
}