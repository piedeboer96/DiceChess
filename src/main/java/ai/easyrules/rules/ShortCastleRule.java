package ai.easyrules.rules;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;
import org.jeasy.rules.api.Facts;

import ai.easyrules.BoardAction;
import ai.easyrules.LFacts;
import chess.interfaces.IChessMatch;
import chess.interfaces.IChessMove;

/*
 *    this the  short castle position 
  
   ChessMove [ [fen=K, team=1, file=4, rank=7], destinations=[CBS [file=6, rank=7, score=0]]]
  
 */

@Rule(name = ShortCastleRule.NAME, description = ShortCastleRule.DESCRIPTION, priority = 10)
public class ShortCastleRule extends ABaseRule {
	
	
	static final String DESCRIPTION = "Execute a short Castle";
	static final String NAME = "- Short Castle Rule -";
	 
	
	/**
	 * We set the score for a short castle to 1000
	 * This score will be added when and if  the @Action castleMove is fired  
	 */
	public ShortCastleRule() {
		score=1000;
	}

	@Condition
	public boolean when(@Fact(LFacts.CHESSMOVE) IChessMove move, @Fact(LFacts.ROLL) char roll) {
		
		// Head Guard if the move is not for the same as roll we do not proceed to check if this rule can fire 
		if (!checkRoll(move, roll)) 
			return false;
	
		// is a castle rules if the King can move from 4,7 to --> 6,7 only
		int kingFile = move.owner().file();
		int kingRank = move.owner().rank();
		int KingFileDest = move.possibilities().get(0).file();
		int kingRankDest = move.possibilities().get(0).rank();
		
		if (kingFile==4 && kingRank==7 && KingFileDest==6&&kingRankDest==7)
		return true;
		
		return false;
	}

	@Action(order = 1)
	public void castleMove(@Fact(LFacts.CHESSMOVE) IChessMove chessMove) {
		/**
		 * 
		 * ChessMove [ [fen=K, team=1, file=4, rank=7], destinations=[CBS [file=6, rank=7, score=0]]]
		 * 
		 */
		chessMove.possibilities().get(0).addScore(score);

	}

	@Action(order = 2)
	public void Finally(Facts facts) throws Exception {
		setAction(facts,BoardAction.ONLY_MOVE);
	}

}








