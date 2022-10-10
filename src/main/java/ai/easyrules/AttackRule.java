package ai.easyrules;

import chess.interfaces.IChessBoardSquare;
import chess.interfaces.IChessMatch;
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
	private IChessPiece opponentPiece;
	private IChessMove chessMove;

	@Condition
	public boolean when(@Fact("ChessMove") IChessMove move, @Fact("Match") IChessMatch match, @Fact("ROLL") char roll) {
		opponentPiece = match.get(move.possibilities().get(0));
		if (move.owner().toFen() == roll && opponentPiece != null) {

			return true;

		}
		return false;
	}

	@Action(order = 1)
	public void attackMove(@Fact("ChessMove") IChessMove chessMove) {
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
		char fen = opponentPiece.toFen();

		List<IChessBoardSquare> possibilities = move.possibilities();

		switch (fen) {

		case 'P':
		case 'p':
			possibilities.get(0).addScore(2*Pawn.pointValue);
			break;
		case 'B':
		case 'b':

			possibilities.get(0).addScore(2*Bishop.pointValue);
			break;
		case 'K':
		case 'k':

			possibilities.get(0).addScore(2*King.pointValue);
			break;
		case 'N':
		case 'n':

			possibilities.get(0).addScore(2*Knight.pointValue);

			break;
		case 'Q':
		case 'q':
			possibilities.get(0).addScore(2*Queen.pointValue);

			break;
		case 'R':
		case 'r':

			possibilities.get(0).addScore(2*Rook.pointValue);

			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + fen);

		}
	}
}