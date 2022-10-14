package examples;

import chess.ChessMatch;
import gui.ChessWindow;

public class JustShowChessBoard {
	public static void main(String[] args) {

		JustShowChessBoard runGame = new JustShowChessBoard();
		runGame.mainLoop();

	}

	public void mainLoop() {

		// Creating a new window.
		ChessWindow window = new ChessWindow(640, 640, false);

		String startPos;
		// full pieces
		startPos = "8/8/8/p/1K6/8/8/k w - - 0 1";
		startPos ="K/8/8/4b/8/5Q2/8/k w - - 0 1";
		startPos = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
		startPos = "1n1qk3/1r6/8/3P4/4pR2/P2K1p1b/P2p1P2/8 w - - 0 1";
		startPos = "8/4n1PK/8/8/8/1r6/8/k7 w - - 0 1";
		startPos = "k7/7b/8/R3b4/5p2/8/2P5/1K2Q3 w - - 0 1";
		startPos = "1n1qk3/1r6/8/3P4/4Pr2/P2K1p1b/P2p1P2/8 w - - 0 1";
		startPos = "1r6/2p3pp/p1p2k2/4p3/8/1K6/P1PN2B1/R7 W - - 0 1";
		 startPos = "k7/pppppppp/8/8/8/8/8/R3K2R w KQkq - 0 1";
			startPos = "8/P7/8/8/2K5/8/8/k7 w - - 0 1";
		startPos =  "rnbqkb1r/pppppppp/8/4n3/2B5/8/PPPP1PPP/RNBQK1NR w - - 0 1";
			// Creating a new match.
		ChessMatch match = new ChessMatch(startPos);
		match.loadKings();

		// Displays the match
		window.display(match);

		// Getting the moves of the current player easily by

	}

	public void showChessBoard(String fen) {

		//Create window
		ChessWindow window = new ChessWindow(640, 640, false);

		// start position
		String startPos;
		startPos = fen;

		// Creating a new match.
		ChessMatch match = new ChessMatch(startPos);
		match.loadKings();

		// Displays the match
		window.display(match);

	}

}
