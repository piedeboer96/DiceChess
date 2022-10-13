package die;

import java.util.Random;

import die.interfaces.IDie;

public class Die implements IDie {
	public char roll(int team) {
		Random generator = new Random();
		int result = generator.nextInt(6);
		char fen;
		switch (result) {
		case 0:
			// Bishop
			fen = 'b';
			break;
		case 1:
			// King
			fen = 'k';
			break;
		case 2:
			// Knight
			fen = 'n';
			break;
		case 3:
			// Pawn
			fen = 'p';
			break;
		case 4:
			// Queen
			fen = 'q';
			break;
		case 5:
			// Rook
			fen = 'r';
			break;
		default:
			throw new IllegalStateException("This die does not support the number: " + result);
		}
		// If the die is rolled for white (team 1), then the fen character should be an upper case.
		if (team == 1) {
			fen = Character.toUpperCase(fen);
		}
		return fen;
	}
}
