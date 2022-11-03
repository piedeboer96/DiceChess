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
import phase1version.chess.ChessMatch;
import phase1version.chess.interfaces.IChessMove;
import phase1version.chess.interfaces.IChessboardSquare;
import phase1version.chess.units.Bishop;
import phase1version.chess.units.King;
import phase1version.chess.units.Knight;
import phase1version.chess.units.Pawn;
import phase1version.chess.units.Queen;
import phase1version.chess.units.Rook;

/*
  
   
 */

@Rule(name = NotSuicideRule.NAME, description = NotSuicideRule.DESCRIPTION, priority = 1)
public class NotSuicideRule extends ABaseRule{


	final static  String DESCRIPTION = "Do not move if I can be eated by another piece";
	final static   String NAME = "- Not  Suicide     -";

	@Condition
	public boolean when(@Fact(LFacts.CHESSMOVE) IChessMove move, @Fact(LFacts.MATCH) ChessMatch match, @Fact(LFacts.ROLL) char roll) {

		if (! (checkRoll(move, roll) )) 
			return false;
		
		int file = move.possibilities().get(0).file();				//potential mistake
		int rank = move.possibilities().get(0).rank();				//potential mistake

		int otherPlayer = match.getPlayer() == 1 ? 0 : 1;
		List<IChessMove> generateMovesOf = match.generateMovesOf(otherPlayer);

		// if the opponent can move and eat in our destintaion the we activate the rule
		List<IChessMove> splitMoves = Utils.splitMoves(generateMovesOf);
		for (IChessMove opponentMove : splitMoves) {
			if (opponentMove.possibilities().get(0).file() == file				//potential mistake
					&& opponentMove.possibilities().get(0).rank() == rank)		//potential mistake
				return true;
		}

		return false;
	}

	@Action(order = 1)
	public void increaseRanking(@Fact(LFacts.CHESSMOVE) IChessMove chessMove) {


		char fen = chessMove.owner().toFen();
		List<IChessboardSquare> possibilities = chessMove.possibilities();		//potential mistake
		IChessboardSquare IChessboardSquare = possibilities.get(0);				//potential mistake

		switch (fen) {
			case 'P', 'p' -> score = -Pawn.pointValue;
			case 'B', 'b' -> score = -Bishop.pointValue;
			case 'K', 'k' -> score = -King.pointValue;
			case 'N', 'n' -> score = -Knight.pointValue;
			case 'Q', 'q' -> score = -Queen.pointValue;
			case 'R', 'r' -> score = -Rook.pointValue;
			default -> throw new IllegalArgumentException("Unexpected value: " + fen);
		}

		IChessboardSquare.addScore(score);


	}
	
	@Action(order = 2)
	public void Finally(Facts facts) throws Exception {
		setAction(facts, BoardAction.NO_MOVE);
	}

	 
}