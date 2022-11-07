package ai.easyrules;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import chess.units.King;

class TestClonePiece {

	@Test
	void test() throws CloneNotSupportedException {
		char fen='k';
		int rank=2;
		int file=3;
		King king = new King(fen, file, rank);
		King newKing = (King) king.clone();
		
		assertNotEquals(king, newKing);
		assertEquals(king.toFen(), newKing.toFen());
		assertEquals(king.file(), newKing.file());
		assertEquals(king.rank(), newKing.rank());
	}

}
