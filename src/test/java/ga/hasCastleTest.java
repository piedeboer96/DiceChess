package ga;

import ai.easyrules.EasyRuleEngine;
import ai.easyrules.ResultAI;
import ai.easyrules.Utils;
import chess.ChessMatch;
import chess.interfaces.IChessMove;
import chess.interfaces.IChessPiece;
import chess.utility.ChessboardSquare;
import org.junit.jupiter.api.Test;
import phase2version.GA.FitnessFunction;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class hasCastleTest {

	@Test
	public void testResult(){
		String startPos = "1r6/2p3pp/p1p2k2/4p3/8/8/3N2B1/R1N1K2R w KQkq - 0 1";
		ChessMatch match = new ChessMatch(startPos);
		int f = FitnessFunction.kingCastled(match);

		assertEquals(1, f);
	}
}
/**
 * IChessPiece king = match.get(new ChessboardSquare(6,7));
 * 		IChessPiece rook = match.get(new ChessboardSquare(5,7));
 * 		rook.toFen()=='R' && king.toFen()=='K'
 */