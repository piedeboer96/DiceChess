package ai.easyrules.rules;

import java.util.List;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;
import org.jeasy.rules.api.Facts;

import ai.easyrules.LFacts;
import ai.easyrules.Utils;
import chess.ChessMatch;
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

@Rule(name = EscapeFromEatRule.NAME, description = EscapeFromEatRule.DESCRIPTION, priority = 1)
public class EscapeFromEatRule extends BaseRule{


	static final String DESCRIPTION = "If the piece is under Attack let's try to escape";
	static final String NAME = "- Escape from EAT  -";
	

	@Condition
	public boolean when(@Fact(LFacts.CHESSMOVE) IChessMove move, @Fact(LFacts.MATCH) ChessMatch match,@Fact(LFacts.ROLL) char roll) {

		if (! (checkRoll(move, roll) )) 
			return false;
		
		int file = move.possibilities().get(0).file();
		int rank = move.possibilities().get(0).rank();

		int otherPlayer = match.getPlayer() == 1 ? 0 : 1;
		List<IChessMove> generateMovesOf = match.generateMovesOf(otherPlayer);

		// if the opponent can move and eat in our destintaion the we activate the rule
		List<IChessMove> splitMoves = Utils.splitMoves(generateMovesOf);
		for (IChessMove opponentMove : splitMoves) {
			if (opponentMove.possibilities().get(0).file() == file
					&& opponentMove.possibilities().get(0).rank() == rank)
				return true;
		}

		return false;
	}

	@Action(order = 1)
	public void increaseRanking(@Fact(LFacts.CHESSMOVE) IChessMove chessMove) {


		evaluateMove(chessMove);

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
		IChessboardSquare iChessBoardSquare = possibilities.get(0);
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