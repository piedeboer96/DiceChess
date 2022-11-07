package ai.test.t10;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import ai.easyrules.EasyRuleEngine;
import ai.easyrules.ResultAI;
import ai.easyrules.Utils;
import chess.ChessMatch;
import chess.interfaces.IChessMove;
import chess.interfaces.IChessPiece;
import chess.utility.ChessboardSquare;

/*

    Your king can not have moved- Once your king moves, you can no longer castle, even if you move the king back to the starting square. Many strategies involve forcing the opponent’s king to move just for this reason.
    Your rook can not have moved- If you move your rook, you can’t castle on that side anymore. Both the king and the rook you are castling with can’t have moved.
    Your king can NOT be in check- Though castling often looks like an appealing escape, you can’t castle while you are in check! Once you are out of check, then you can castle. Unlike moving, being checked does not remove the ability to castle later.
    Your king can not pass through check- If any square the king moves over or moves onto would put you in check, you can’t castle. You’ll have to get rid of that pesky attacking piece first!

 */
public class LongCastleTest {

	@Test
	void castleKingPointOfView() {

		String startPos = "k7/pppppppp/8/8/8/8/8/R3K1NR w KQkq - 0 1";

		// Creating a new match.
		ChessMatch match = new ChessMatch(startPos);
		int player = match.getPlayer();
		EasyRuleEngine dumyRuleEngine = new EasyRuleEngine(match, 'R', 'P');

		List<IChessMove> moves = match.legalMovesOf(player);
		List<IChessMove> splitMoves = Utils.splitMoves(moves);
		System.out.println("All Legal moves ");
		for (IChessMove mv : splitMoves) {
			if (mv.owner().toFen() == 'K')
				System.out.println(mv);
		}

		System.out.println();
		System.out.println();

		ResultAI play = dumyRuleEngine.play();

		
		System.out.println("result-->" + play);
		
		
		IChessPiece king = match.get(new ChessboardSquare(2,7));
		assertEquals('K', king.toFen());
		IChessPiece rook = match.get(new ChessboardSquare(3,7));
		
		System.out.println(rook);
		assertEquals('R', rook.toFen());

		
	}

}
