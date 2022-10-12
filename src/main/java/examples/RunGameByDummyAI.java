package examples;

import java.util.concurrent.TimeUnit;

import ai.easyrules.Action;
import ai.easyrules.EasyRuleEngine;
import chess.ChessMatch;
import die.Die;
import gui.ChessWindow;
import gui.DiceChessWindow;

public class RunGameByDummyAI {
	public static void main(String[] args) {

		RunGameByDummyAI runGame = new RunGameByDummyAI();
		runGame.mainLoop();

	}

	public void mainLoop() {

		// Creating a new window.
		DiceChessWindow window = new DiceChessWindow(800, 640, false);

		String startPos;
		// full pieces
		startPos = "8/8/8/p/1K6/8/8/k w - - 0 1";
		startPos ="K/8/8/4b/8/5Q2/8/k w - - 0 1";
		startPos = "1n1qk3/1r6/8/3p4/4pR2/P2K1p1b/P2p1P2/8 w - - 0 1";
		startPos = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";

		// Creating a new match.
		ChessMatch match = new ChessMatch(startPos);
		match.loadKings();

		// Displays the match
		window.display(match);

		// Getting the moves of the current player easily by

		int currentPlayer;

		int i = 0;
		int maxMove = 1000;
		while (i++ < maxMove) {
			currentPlayer = match.getPlayer();
			// first we need fire the roll
			Die diceOne = new Die();
			Die diceTwo = new Die();
			char rollOne = diceOne.roll(currentPlayer);
			char rollTwo = diceTwo.roll(currentPlayer);

			sleep(500);

			EasyRuleEngine dumyRuleEngine = new EasyRuleEngine(match, rollOne, rollTwo);
			Action play = dumyRuleEngine.play();
			if (play == Action.FINISH_MATCH) {
				while (true) {
					window.refresh();
					sleep(500);
				}
			}
			//
			window.refresh();

			sleep(1000);
			window.display(match);
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
