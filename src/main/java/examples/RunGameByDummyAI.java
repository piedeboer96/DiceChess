package examples;

import java.util.concurrent.TimeUnit;

import ai.easyrules.Action;
import ai.easyrules.EasyRuleEngine;
import chess.ChessMatch;
import chess.interfaces.IChessMatch;
import die.Die;
import gui.ChessGameWindow;
import gui.interfaces.IChessGameWindow;

public class RunGameByDummyAI {
	public static void main(String[] args) {

		RunGameByDummyAI runGame = new RunGameByDummyAI();
		runGame.mainLoop();

	}

	public void mainLoop() {

		// Creating a new window.
		ChessGameWindow window = new ChessGameWindow();

		String startPos;
		// full pieces
		startPos = "8/8/8/p/1K6/8/8/k w - - 0 1";
		startPos = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";


		// Creating a new match.
		ChessMatch match = new ChessMatch(startPos);
		match.loadKings();

		// Displays the match
		window.displayMatch(match);

		// Getting the moves of the current player easily by

		int currentPlayer;

		int i = 0;
		int maxMove = 1000;
		while (i++ < maxMove) {
			currentPlayer = match.getPlayer();
			// first we need fire the roll
			Die myRoll = new Die();
			char rollTheDie = myRoll.roll(currentPlayer);

			// dumpMoves(moves);

			EasyRuleEngine dumyRuleEngine = new EasyRuleEngine(match, rollTheDie);
			Action play = dumyRuleEngine.play();
			if (play == Action.FINISH_MATCH) {
				while (true) {
					sleep(500);
				}
			}
			window.updateUI();

			while (((ChessGameWindow) window).getPause())
				try {

					sleep(100);

				} catch (Exception e) {
				}

			sleep(1000);
			window.displayMatch(match);

		}
	}

	private void sleep(int sec) {
		try {
			TimeUnit.MILLISECONDS.sleep(sec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
