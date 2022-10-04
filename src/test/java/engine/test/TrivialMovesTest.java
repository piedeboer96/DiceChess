package engine.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.jeasy.rules.api.Facts;
import org.junit.jupiter.api.Test;

import chess.interfaces.IChessBoardSquare;
import chess.interfaces.IChessPiece;
import chess.units.ChessPiece;
import chess.units.Pawn;
import chess.utility.ChessBoardSquare;
import chess.utility.ChessMove;

/**
 * 
 * 
 * Roll P Dumping Moves : ChessMove [owner=ChessPiece [fen=P, team=1, file=0,
 * rank=6], destinations=[ChessBoardSquare [file=0, rank=5], ChessBoardSquare
 * [file=0, rank=4]]] ChessMove [owner=ChessPiece [fen=P, team=1, file=1,
 * rank=6], destinations=[ChessBoardSquare [file=1, rank=5], ChessBoardSquare
 * [file=1, rank=4]]] ChessMove [owner=ChessPiece [fen=P, team=1, file=2,
 * rank=6], destinations=[ChessBoardSquare [file=2, rank=5], ChessBoardSquare
 * [file=2, rank=4]]] ChessMove [owner=ChessPiece [fen=P, team=1, file=3,
 * rank=6], destinations=[ChessBoardSquare [file=3, rank=5], ChessBoardSquare
 * [file=3, rank=4]]] ChessMove [owner=ChessPiece [fen=P, team=1, file=4,
 * rank=6], destinations=[ChessBoardSquare [file=4, rank=5], ChessBoardSquare
 * [file=4, rank=4]]] ChessMove [owner=ChessPiece [fen=P, team=1, file=5,
 * rank=6], destinations=[ChessBoardSquare [file=5, rank=5], ChessBoardSquare
 * [file=5, rank=4]]] ChessMove [owner=ChessPiece [fen=P, team=1, file=6,
 * rank=6], destinations=[ChessBoardSquare [file=6, rank=5], ChessBoardSquare
 * [file=6, rank=4]]] ChessMove [owner=ChessPiece [fen=P, team=1, file=7,
 * rank=6], destinations=[ChessBoardSquare [file=7, rank=5], ChessBoardSquare
 * [file=7, rank=4]]]
 * 
 */

class TrivialMovesTest {

	@Test
	void test() {
		Facts facts = new Facts();

		IChessPiece piece = new Pawn('P', 0, 6);
		List<IChessBoardSquare> destinations = new ArrayList<IChessBoardSquare>();
		destinations.add(new ChessBoardSquare(0, 5));
		destinations.add(new ChessBoardSquare(0, 4));
		ChessMove move1 = new ChessMove(piece, destinations);
		facts.put("move", move1);
		System.out.println(move1);

		piece = new Pawn('P', 1, 6);
		destinations = new ArrayList<IChessBoardSquare>();
		destinations.add(new ChessBoardSquare(1, 4));
		destinations.add(new ChessBoardSquare(1, 5));
		move1 = new ChessMove(piece, destinations);
		facts.put("move", move1);

		piece = new Pawn('P', 2, 6);
		destinations = new ArrayList<IChessBoardSquare>();
		destinations.add(new ChessBoardSquare(2, 4));
		destinations.add(new ChessBoardSquare(2, 5));
		move1 = new ChessMove(piece, destinations);
		facts.put("move", move1);

		piece = new Pawn('P', 3, 6);
		destinations = new ArrayList<IChessBoardSquare>();
		destinations.add(new ChessBoardSquare(3, 4));
		destinations.add(new ChessBoardSquare(3, 5));
		move1 = new ChessMove(piece, destinations);
		facts.put("move", move1);

	}

}
