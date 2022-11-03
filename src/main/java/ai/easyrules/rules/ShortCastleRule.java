package ai.easyrules.rules;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;
import org.jeasy.rules.api.Facts;

import ai.easyrules.BoardAction;
import ai.easyrules.LFacts;
import chess.interfaces.IChessMove;

/*
 *    this the  short castle position 
  
   ChessMove [ [fen=K, team=1, file=4, rank=7], destinations=[CBS [file=6, rank=7, score=0]]]
   ChessMove [ [fen=R, team=1, file=7, rank=7], destinations=[CBS [file=5, rank=7, score=0]]]
 */

@Rule(name = ShortCastleRule.NAME, description = ShortCastleRule.DESCRIPTION, priority = 10)
public class ShortCastleRule extends ABaseRule {

	static final String DESCRIPTION = "Execute a short Castle";
	static final String NAME = "- Short Castle Rule -";

	/**
	 * We set the score for a short castle to 2000 This score will be added when and if the @Action castleMove is fired
	 */
	public ShortCastleRule() {
		score = 2000;
	}

	@Condition
	public boolean when(@Fact(LFacts.CHESSMOVE) IChessMove move, @Fact(LFacts.ROLL) char roll) {

		// Head Guard if the move is not for the same as roll we do not proceed to check if this rule can fire
		if (!checkRoll(move, roll))
			return false;
//		Evaluating roll [R] From  [fen=R, team=1, file=7, rank=7] -- To Move --> CBS [file=5, rank=7, score=0]
//				Rule   - Move By Position - new Score =  0
		// is a castle rules if the King can move from 4,7 to --> 6,7 only
		if (roll == 'K'||roll=='x') {
			int kingFile = move.owner().file();
			int kingRank = move.owner().rank();
			int kingFileDest = move.possibilities().get(0).file();
			int kingRankDest = move.possibilities().get(0).rank();

			if (kingFile == 4 && kingRank == 7 && kingFileDest == 6 && kingRankDest == 7)
				return true;
		} else if (roll == 'R'||roll=='x') {
			int rookFile = move.owner().file();
			int rookRank = move.owner().rank();
			int rookFileDest = move.possibilities().get(0).file();
			int rookRankDest = move.possibilities().get(0).rank();

			if (rookFile == 7 && rookRank == 7 && rookFileDest == 5 && rookRankDest == 7)
				return true;
		} else if (roll == 'k'||roll=='x') {
			int kingFile = move.owner().file();
			int kingRank = move.owner().rank();
			int kingFileDest = move.possibilities().get(0).file();
			int kingRankDest = move.possibilities().get(0).rank();

			if (kingFile == 4 && kingRank == 0 && kingFileDest == 6 && kingRankDest == 0)
				return true;
		} else if (roll == 'r'||roll=='x') {
			int rookFile = move.owner().file();
			int rookRank = move.owner().rank();
			int rookFileDest = move.possibilities().get(0).file();
			int rookRankDest = move.possibilities().get(0).rank();

			if (rookFile == 7 && rookRank == 0 && rookFileDest == 5 && rookRankDest == 0)
				return true;
		}
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
		setAction(facts, BoardAction.MOVE_AND_CASTLE_SHORT);
	}

}
