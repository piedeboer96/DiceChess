package ai.easyrules.rules;

import java.util.List;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;
import org.jeasy.rules.api.Facts;

import ai.easyrules.BoardAction;
import ai.easyrules.LFacts;
import ai.easyrules.Utils;
import chess.ChessMatch;
import chess.interfaces.IChessMove;
import chess.interfaces.IChessboardSquare;
import chess.units.Bishop;
import chess.units.King;
import chess.units.Knight;
import chess.units.Pawn;
import chess.units.Queen;
import chess.units.Rook;

/*
  
   
 */

@Rule(name = NotSuicideRule.NAME, description = NotSuicideRule.DESCRIPTION, priority = 1)
public class NotSuicideRule extends ABaseRule{


	final static  String DESCRIPTION = "Do not move if I can be eated by another piece";
	final static   String NAME = "- Not  Suicide     -";

	@Condition
	public boolean when(@Fact(LFacts.CHESSMOVE) IChessMove move, @Fact(LFacts.MATCH) ChessMatch match,@Fact(LFacts.ROLL) char roll) {

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

		IChessboardSquare.addScore(score);


	}
	
	@Action(order = 2)
	public void Finally(Facts facts) throws Exception {
		setAction(facts,BoardAction.NO_MOVE);
	}

	 
}