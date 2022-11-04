package ai.easyrules;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import phase1version.ai.easyrules.EasyRuleEngine;
import phase1version.ai.easyrules.Utils;
import phase1version.chess.ChessMatch;
import phase1version.chess.interfaces.IChessMatch;
import phase1version.chess.interfaces.IChessMove;
import phase1version.chess.interfaces.IChessPiece;
import phase1version.chess.interfaces.IChessboardSquare;
import phase1version.chess.units.Pawn;
import phase1version.chess.utility.ChessMove;
import phase1version.chess.utility.ChessboardSquare;

public class EasyRuleEngineTest {

	@Test
	public void testEasyRuleEngine() {

		// Creating a new match.
		IChessMatch match = new ChessMatch();
		EasyRuleEngine dumyRuleEngine = new EasyRuleEngine(match, 'p','p');
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
