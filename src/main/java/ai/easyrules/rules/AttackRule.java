package ai.easyrules.rules;

import java.util.List;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;
import org.jeasy.rules.api.Facts;

import ai.easyrules.LFacts;
import chess.interfaces.IChessMatch;
import chess.interfaces.IChessMove;
import chess.interfaces.IChessPiece;
import chess.interfaces.IChessboardSquare;
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

@Rule(name = AttackRule.NAME, description = AttackRule.DESCRIPTION, priority = 10)
public class AttackRule extends BaseRule {
	static final String DESCRIPTION = "Add a score according to capturing a piece";
	static final String NAME = "- Attack Rule      -";
	private IChessPiece opponentPiece;

	@Condition
	public boolean when(@Fact(LFacts.CHESSMOVE) IChessMove move, @Fact(LFacts.MATCH) IChessMatch match, @Fact(LFacts.ROLL) char rollOne) {
		opponentPiece = match.get(move.possibilities().get(0));
		if (checkRoll(move, rollOne) && opponentPiece != null) {

			return true;

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
		char fen = opponentPiece.toFen();

		List<IChessboardSquare> possibilities = move.possibilities();

		switch (fen) {

		case 'P':
		case 'p':
			possibilities.get(0).addScore(2 * Pawn.pointValue);
			break;
		case 'B':
		case 'b':

			possibilities.get(0).addScore(2 * Bishop.pointValue);
			break;
		case 'K':
		case 'k':

			possibilities.get(0).addScore(2 * King.pointValue);
			break;
		case 'N':
		case 'n':

			possibilities.get(0).addScore(2 * Knight.pointValue);

			break;
		case 'Q':
		case 'q':
			possibilities.get(0).addScore(2 * Queen.pointValue);

			break;
		case 'R':
		case 'r':

			possibilities.get(0).addScore(2 * Rook.pointValue);

			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + fen);

		}
	}
}