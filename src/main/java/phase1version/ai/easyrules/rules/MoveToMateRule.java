package phase1version.ai.easyrules.rules;

import java.util.List;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;
import org.jeasy.rules.api.Facts;

import phase1version.ai.easyrules.BoardAction;
import phase1version.ai.easyrules.LFacts;
import phase1version.ai.easyrules.Utils;
import phase1version.chess.interfaces.IChessMatch;
import phase1version.chess.interfaces.IChessMove;
import phase1version.chess.interfaces.IChessPiece;
import phase1version.chess.interfaces.IChessboardSquare;
import phase1version.chess.units.ChessPiece;
import phase1version.chess.utility.ChessboardSquare;

/*
  
      
 */

@Rule(name = MoveToMateRule.NAME, description = MoveToMateRule.DESCRIPTION, priority = 10)
public class MoveToMateRule extends ABaseRule {
	final static  String DESCRIPTION = "Add a score if we can push to mate";
	final static   String NAME = "- Move to Mate Rule-";

	 public MoveToMateRule() {
		score=2000;
	}
	@Condition
	public boolean when(@Fact(LFacts.CHESSMOVE) IChessMove move, @Fact(LFacts.MATCH) IChessMatch match, @Fact(LFacts.ROLL) char roll) {

		if (!checkRoll(move, roll))
			return false;

		int destFile = move.possibilities().get(0).file();
		int destRank = move.possibilities().get(0).rank();

		ChessPiece piece = (ChessPiece) move.owner();

		ChessPiece newPiece = Utils.clone(piece);

		// we set the new position on the possible move
		newPiece.setPosition(new ChessboardSquare(destFile, destRank));

		// now let's generate all possible moves ...
		List<IChessMove> movesOf = match.generateMovesOf(newPiece);
		movesOf = Utils.splitMoves(movesOf);

		// we must find the opponent KING
		IChessPiece king = match.getKing(move.owner().team() == 1 ? 0 : 1);
		int kingFile = king.file();
		int kingRank = king.rank();

		for (IChessMove moveNew : movesOf) {
			List<IChessboardSquare> possibilities = moveNew.possibilities();

			for (IChessboardSquare boardSquare : possibilities) {

				// now let's check if with in a possible move we can reach the opponent KING
				if (boardSquare.file() == kingFile && boardSquare.rank() == kingRank)
					return true;
			}

		}

		return false;
	}

	@Action(order = 1)
	public void attackMove(@Fact(LFacts.CHESSMOVE) IChessMove chessMove) {

		
		List<IChessboardSquare> possibilities = chessMove.possibilities();

		possibilities.get(0).addScore(score);

	}

	@Action(order = 2)
	public void Finally(Facts facts) throws Exception {
		setAction(facts, BoardAction.ONLY_MOVE);
	}

	 
}