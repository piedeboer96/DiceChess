package ai.easyrules;

import java.util.List;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;
import org.jeasy.rules.api.Facts;

import chess.ChessMatch;
import chess.interfaces.IChessBoardSquare;
import chess.interfaces.IChessMove;
import chess.interfaces.IChessPiece;
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

@Rule(name = "- Not  Suicide     -", description = "Do not move if I can be eated by another piece", priority = 10)
public class NotSuicideRule {

	private IChessMove chessMove;

	@Condition
	public boolean when(@Fact(LFacts.CHESSMOVE) IChessMove move, @Fact(LFacts.MATCH) ChessMatch match) {

		int file = move.possibilities().get(0).file();
		int rank = move.possibilities().get(0).rank();

		int otherPlayer = match.getPlayer() == 1 ? 0 : 1;
		List<IChessMove> generateMovesOf = match.generateMovesOf(otherPlayer);

		// if the opponent can move and eat in our destintaion the we activate the rule
		List<IChessMove> splitMoves = EasyRuleEngine.splitMoves(generateMovesOf);
		for (IChessMove opponentMove : splitMoves) {
			if (opponentMove.possibilities().get(0).file() == file
					&& opponentMove.possibilities().get(0).rank() == rank)
				return true;
		}

		return false;
	}

	@Action(order = 1)
	public void increaseRanking(@Fact(LFacts.CHESSMOVE) IChessMove chessMove) {

		this.chessMove = chessMove;

		evaluateMove(chessMove);

	}

	@Action(order = 2)
	public void Finally(Facts facts) throws Exception {

		IChessMove best = facts.get(LFacts.BEST_MOVE);

		if (best.owner() == null
				|| best.possibilities().get(0).getScore() < chessMove.possibilities().get(0).getScore()) {
			facts.put(LFacts.BEST_MOVE, chessMove);
			facts.put(LFacts.ACTION, ai.easyrules.Action.ONLY_MOVE);
		}
	}

	private void evaluateMove(IChessMove move) {
		char fen = move.owner().toFen();
		List<IChessBoardSquare> possibilities = move.possibilities();
		IChessBoardSquare iChessBoardSquare = possibilities.get(0);
		int score;
		switch (fen) {
		case 'P':
		case 'p':

			score = -Pawn.pointValue;

			break;
		case 'B':
		case 'b':
			score = -Bishop.pointValue;
			break;
		case 'K':
		case 'k':
			score = -King.pointValue;
			break;
		case 'N':
		case 'n':
			score = -Knight.pointValue;
			break;
		case 'Q':
		case 'q':

			score = -Queen.pointValue;

			break;
		case 'R':
		case 'r':
			score = -Rook.pointValue;
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + fen);

		}

		iChessBoardSquare.addScore(score);
	}
}