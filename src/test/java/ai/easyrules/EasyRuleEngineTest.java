package ai.easyrules;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import chess.ChessMatch;
import chess.interfaces.IChessBoardSquare;
import chess.interfaces.IChessMatch;
import chess.interfaces.IChessMove;
import chess.interfaces.IChessPiece;
import chess.units.Pawn;
import chess.utility.ChessBoardSquare;
import chess.utility.ChessMove;

public class EasyRuleEngineTest {

	@Test
	public void testEasyRuleEngine() {

        // Creating a new match.
        IChessMatch match = new ChessMatch();
        EasyRuleEngine dumyRuleEngine = new EasyRuleEngine (match);
		dumyRuleEngine.play();

	}
	
	
	@Test 
	public void testSplitMoves() {
		
		     IChessMatch match = new ChessMatch();
	        EasyRuleEngine dumyRuleEngine = new EasyRuleEngine (match);
	        
	        IChessPiece piece = new Pawn('P', 0, 6);
			List<IChessBoardSquare> destinations = new ArrayList<IChessBoardSquare>();
			destinations.add(new ChessBoardSquare(0, 5));
			destinations.add(new ChessBoardSquare(0, 4));
			ChessMove move = new ChessMove(piece, destinations);
			List<IChessMove> list = new ArrayList<IChessMove>();
			list.add(move);
	        List<IChessMove> splitMoves = dumyRuleEngine.splitMoves(list);
	        System.out.println(splitMoves);
	}

}
