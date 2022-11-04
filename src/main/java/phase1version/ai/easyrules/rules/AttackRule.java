package phase1version.ai.easyrules.rules;

import java.util.List;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;
import org.jeasy.rules.api.Facts;

import phase1version.ai.easyrules.BoardAction;
import phase1version.ai.easyrules.LFacts;
import phase1version.chess.interfaces.IChessMatch;
import phase1version.chess.interfaces.IChessMove;
import phase1version.chess.interfaces.IChessPiece;
import phase1version.chess.interfaces.IChessboardSquare;
import phase1version.chess.units.Bishop;
import phase1version.chess.units.King;
import phase1version.chess.units.Knight;
import phase1version.chess.units.Pawn;
import phase1version.chess.units.Queen;
import phase1version.chess.units.Rook;

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
public class AttackRule extends ABaseRule {
	
	 final static String DESCRIPTION = "Add a score according to capturing a piece";
	 final static String NAME = "- Attack Rule       -";
	
	private IChessPiece opponentPiece;

	@Condition
	public boolean when(@Fact(LFacts.CHESSMOVE) IChessMove move, @Fact(LFacts.MATCH) IChessMatch match, @Fact(LFacts.ROLL) char rollOne) {
		
		// Head Guard if the move is not for the same as roll we do not proceed to check if this rule can fire 
		if (!checkRoll(move, rollOne)) { return false; }

		opponentPiece = match.get(move.possibilities().get(0));
		return opponentPiece != null;
	}

	@Action(order = 1)
	public void attackMove(@Fact(LFacts.CHESSMOVE) IChessMove chessMove) {

		char fen = opponentPiece.toFen();
		
		List<IChessboardSquare> possibilities = chessMove.possibilities();

		switch (fen) {
			case 'P', 'p' -> score = 2 * Pawn.pointValue;
			case 'B', 'b' -> score = 2 * Bishop.pointValue;
			case 'K', 'k' -> score = 2 * King.pointValue;
			case 'N', 'n' -> score = 2 * Knight.pointValue;
			case 'Q', 'q' -> score = 2 * Queen.pointValue;
			case 'R', 'r' -> score = 2 * Rook.pointValue;
			default -> throw new IllegalArgumentException("Unexpected value: " + fen);
		}
		possibilities.get(0).addScore(score);


	}

	@Action(order = 2)
	public void Finally(Facts facts) throws Exception {
		setAction(facts, BoardAction.ONLY_MOVE);
		 
	}

	 
}