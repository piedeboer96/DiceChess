package ai.easyrules.rules;

import java.util.List;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;
import org.jeasy.rules.api.Facts;

import ai.easyrules.BoardAction;
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
 *    this the  short castle position 
  
   ChessMove [ [fen=K, team=1, file=4, rank=7], destinations=[CBS [file=6, rank=7, score=0]]]
  
 */

@Rule(name = ShortCastleRule.NAME, description = ShortCastleRule.DESCRIPTION, priority = 10)
public class ShortCastleRule extends ABaseRule {
	
	
	static final String DESCRIPTION = "Execute a short Castle";
	static final String NAME = "- Short Castle Rule -";
	 

	@Condition
	public boolean when(@Fact(LFacts.CHESSMOVE) IChessMove move, @Fact(LFacts.MATCH) IChessMatch match, @Fact(LFacts.ROLL) char rollOne) {
		
		// Head Guard if the move is not for the same as roll we do not proceed to check if this rule can fire 
		if (!checkRoll(move, rollOne)) 
			return false;
	
		
		
		return true;
	}

	@Action(order = 1)
	public void castleMove(@Fact(LFacts.CHESSMOVE) IChessMove chessMove) {
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
		setAction(facts,BoardAction.ONLY_MOVE);
	}

	private void evaluateMove(IChessMove move) {
		
		move.possibilities().get(0).addScore(score);
		
	}
}








