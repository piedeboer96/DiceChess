package ai.easyrules;

import java.util.List;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;
import org.jeasy.rules.api.Facts;

import ai.evaluation.PieceSquareTable;
import chess.interfaces.IChessBoardSquare;
import chess.interfaces.IChessMove;

/*
  
   ChessMove [owner=ChessPiece [fen=P, team=1, file=1, rank=6], destinations=[ChessBoardSquare [file=1, rank=5], ChessBoardSquare [file=1, rank=4]]] 
   ChessMove [owner=ChessPiece [fen=P, team=1, file=2, rank=6], destinations=[ChessBoardSquare [file=2, rank=5], ChessBoardSquare [file=2, rank=4]]] 
   ChessMove [owner=ChessPiece [fen=P, team=1, file=3, rank=6], destinations=[ChessBoardSquare [file=3, rank=5], ChessBoardSquare [file=3, rank=4]]] 
   ChessMove [owner=ChessPiece [fen=P, team=1, file=4, rank=6], destinations=[ChessBoardSquare [file=4, rank=5], ChessBoardSquare [file=4, rank=4]]] 
   ChessMove [owner=ChessPiece [fen=P, team=1, file=5, rank=6], destinations=[ChessBoardSquare [file=5, rank=5], ChessBoardSquare [file=5, rank=4]]] 
   ChessMove [owner=ChessPiece [fen=P, team=1, file=6, rank=6], destinations=[ChessBoardSquare [file=6, rank=5], ChessBoardSquare [file=6, rank=4]]] 
   ChessMove [owner=ChessPiece [fen=P, team=1, file=7, rank=6], destinations=[ChessBoardSquare [file=7, rank=5], ChessBoardSquare [file=7, rank=4]]]
  
 */

@Rule(name = "my rule", description = "Move in the high value for the position ", priority = 1)
public class MoveByPositionRule {
	 
	int currentScore=0;
	private IChessMove chessMove;
	@Condition
	public boolean when(@Fact("ChessMove") IChessMove move) {

		return true;
	}

	@Action(order = 1)
	public void increaseRanking(@Fact("ChessMove") IChessMove chessMove) {
		/*
		 * 
		 * ChessMove [owner=ChessPiece [fen=P, team=1, file=0, rank=6], destinations=[ChessBoardSquare [file=0, rank=5], ChessBoardSquare [file=0,rank=4]]]
		 * 
		 */
		this.chessMove = chessMove;
		evaluateMove(chessMove);
	}
	  
    @Action(order = 2)
    public void Finally(Facts facts) throws Exception {
    	
    	IChessMove best = facts.get(EasyRuleEngine.BEST_MOVE);
    	
    	if (best.owner()==null || best.possibilities().get(0).getScore()<chessMove.possibilities().get(0).getScore())
    		facts.put(EasyRuleEngine.BEST_MOVE, chessMove);
    	
    	facts.put(EasyRuleEngine.ACTION, ai.easyrules.Action.ONLY_MOVE);
    }
	

	
	private void evaluateMove(IChessMove move) {
		char fen = move.owner().toFen();
		List<IChessBoardSquare> possibilities = move.possibilities();
		if(possibilities.size()>1)
			throw new RuntimeException("Should not happes");
		
		IChessBoardSquare possibleMove = possibilities.get(0);
		int rank = possibleMove.rank();
		int file = possibleMove.file();
		int bestScore;
		switch (fen) {
		case 'P':
		case 'p':

			bestScore = PieceSquareTable.pst_PAWN[rank][file];
			break;
		case 'B':
		case 'b':

			bestScore = PieceSquareTable.pst_BISCHOP[rank][file];

			break;
		case 'K':
		case 'k':
			bestScore = PieceSquareTable.pst_KING_MIDGAME[rank][file];
			break;
		case 'N':
		case 'n':
			bestScore = PieceSquareTable.pst_KNIGHT[rank][file];
			break;
		case 'Q':
		case 'q':
			bestScore = PieceSquareTable.pst_QUEEN[rank][file];
			break;
		case 'R':
		case 'r':
			bestScore = PieceSquareTable.pst_ROOKS[rank][file];
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + fen);
		}
		
		int oldscore=possibleMove.getScore();
		possibleMove.addScore(bestScore);
		System.out.println("increasing score for PAWN rank = "+rank +" file= "+file+" old score "+oldscore+" new score "+possibleMove.getScore());

	}
}