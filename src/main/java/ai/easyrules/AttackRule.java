package ai.easyrules;

import chess.interfaces.IChessBoardSquare;
import chess.interfaces.IChessMove;
import chess.interfaces.IChessPiece;
import chess.units.*;
import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;
import org.jeasy.rules.api.Facts;

import java.util.List;

/*
  
   ChessMove [owner=ChessPiece [fen=P, team=1, file=1, rank=6], destinations=[ChessBoardSquare [file=1, rank=5], ChessBoardSquare [file=1, rank=4]]] 
   ChessMove [owner=ChessPiece [fen=P, team=1, file=2, rank=6], destinations=[ChessBoardSquare [file=2, rank=5], ChessBoardSquare [file=2, rank=4]]] 
   ChessMove [owner=ChessPiece [fen=P, team=1, file=3, rank=6], destinations=[ChessBoardSquare [file=3, rank=5], ChessBoardSquare [file=3, rank=4]]] 
   ChessMove [owner=ChessPiece [fen=P, team=1, file=4, rank=6], destinations=[ChessBoardSquare [file=4, rank=5], ChessBoardSquare [file=4, rank=4]]] 
   ChessMove [owner=ChessPiece [fen=P, team=1, file=5, rank=6], destinations=[ChessBoardSquare [file=5, rank=5], ChessBoardSquare [file=5, rank=4]]] 
   ChessMove [owner=ChessPiece [fen=P, team=1, file=6, rank=6], destinations=[ChessBoardSquare [file=6, rank=5], ChessBoardSquare [file=6, rank=4]]] 
   ChessMove [owner=ChessPiece [fen=P, team=1, file=7, rank=6], destinations=[ChessBoardSquare [file=7, rank=5], ChessBoardSquare [file=7, rank=4]]]
  
 */

@Rule(name = "Attack Rule ", description = "Add a score according to capturing a piece", priority = 1)
public class AttackRule {

	@Condition
	public boolean when(@Fact("ChessMove") IChessMove move, @Fact("ROLL") char roll) {
		if (move.owner().toFen() == roll) {
			return true;
		}
		return false;
	}

	@Action(order = 1)
	public void attackMove(@Fact("ChessMove") IChessMove chessMove) {
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

		// need to change ENUM to  ATTACK
		facts.put(EasyRuleEngine.ACTION, ai.easyrules.Action.ONLY_MOVE);
	}

	private void evaluateMove(IChessMove move) {
		char fen = move.owner().toFen();

		IChessPiece owner = move.owner();  						// the owner of the move

		List<IChessBoardSquare> possibilities = move.possibilities();

		switch (fen) {

		case 'P':
		case 'p':

			for (IChessBoardSquare iChessBoardSquare : possibilities) {

				IChessPiece pieceOnTargetDestination = null;					//HOW DO I GET THIS?
				boolean isEnemy = owner.opponentOf(pieceOnTargetDestination);

				if (isEnemy) {

					// piece in that position has a score field
					// add this score to the move
				} else {
					System.out.println("Friendly piece...");
				}


				int rank = iChessBoardSquare.rank();
				int file = iChessBoardSquare.file();


				//int oldscore = iChessBoardSquare.getScore();

				// we want to check all possible attacks by our pawn
				// which other pieces are on the reachable destinations of the pawn




//				iChessBoardSquare.addScore(-Pawn.pointValue);
//				System.out.println("increasing score for PAWN rank = " + rank + " file= " + file + " old score "
//						+ oldscore + " new score " + iChessBoardSquare.getScore());

			}

			break;
		case 'B':
		case 'b':

			for (IChessBoardSquare iChessBoardSquare : possibilities)

			{
				int rank = iChessBoardSquare.rank();
				int file = iChessBoardSquare.file();
				int oldscore = iChessBoardSquare.getScore();
				iChessBoardSquare.addScore(-Bishop.pointValue);
//				System.out.println("increasing score for PAWN rank = " + rank + " file= " + file + " old score "
//						+ oldscore + " new score " + iChessBoardSquare.getScore());

			}

			break;
		case 'K':
		case 'k':

			for (IChessBoardSquare iChessBoardSquare : possibilities)

			{
				int rank = iChessBoardSquare.rank();
				int file = iChessBoardSquare.file();
				int oldscore = iChessBoardSquare.getScore();
				iChessBoardSquare.addScore(-King.pointValue);
//				System.out.println("increasing score for PAWN rank = " + rank + " file= " + file + " old score "
//						+ oldscore + " new score " + iChessBoardSquare.getScore());

			}

			break;
		case 'N':
		case 'n':

			for (IChessBoardSquare iChessBoardSquare : possibilities)

			{
				int rank = iChessBoardSquare.rank();
				int file = iChessBoardSquare.file();
				int oldscore = iChessBoardSquare.getScore();
				iChessBoardSquare.addScore(-Knight.pointValue);
//				System.out.println("increasing score for PAWN rank = " + rank + " file= " + file + " old score "
//						+ oldscore + " new score " + iChessBoardSquare.getScore());

			}

			break;
		case 'Q':
		case 'q':

			for (IChessBoardSquare iChessBoardSquare : possibilities)

			{
				int rank = iChessBoardSquare.rank();
				int file = iChessBoardSquare.file();
				int oldscore = iChessBoardSquare.getScore();
				iChessBoardSquare.addScore(-Queen.pointValue);
//				System.out.println("increasing score for PAWN rank = " + rank + " file= " + file + " old score "
//						+ oldscore + " new score " + iChessBoardSquare.getScore());

			}

			break;
		case 'R':
		case 'r':

			for (IChessBoardSquare iChessBoardSquare : possibilities)

			{
				int rank = iChessBoardSquare.rank();
				int file = iChessBoardSquare.file();
				int oldscore = iChessBoardSquare.getScore();
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