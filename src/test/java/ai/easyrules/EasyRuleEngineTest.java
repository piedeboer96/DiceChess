package ai.easyrules;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import chess.ChessMatch;
import chess.interfaces.IChessMatch;
import chess.interfaces.IChessMove;
import chess.interfaces.IChessPiece;
import chess.interfaces.IChessboardSquare;
import chess.units.Pawn;
import chess.utility.ChessMove;
import chess.utility.ChessboardSquare;

public class EasyRuleEngineTest {

	@Test
	public void testEasyRuleEngine() {

		// Creating a new match.
		IChessMatch match = new ChessMatch();
		EasyRuleEngine dumyRuleEngine = new EasyRuleEngine(match, 'p');
		dumyRuleEngine.play();

	}

	@Test
	public void testSplitMoves() {

		IChessPiece piece = new Pawn('P', 0, 6);
		List<IChessboardSquare> destinations = new ArrayList<IChessboardSquare>();
		destinations.add(new ChessboardSquare(0, 5));
		destinations.add(new ChessboardSquare(0, 4));
		ChessMove move = new ChessMove(piece, destinations);
		List<IChessMove> list = new ArrayList<IChessMove>();
		list.add(move);
		List<IChessMove> splitMoves = Utils.splitMoves(list);
		System.out.println(splitMoves);
	}

}
