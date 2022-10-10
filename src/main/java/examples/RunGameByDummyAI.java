package examples;

import java.util.concurrent.TimeUnit;

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
		IChessGameWindow window = new ChessGameWindow();

		// Creating a new match.
		IChessMatch match = new ChessMatch();

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
			System.err.println(rollTheDie + " for player " + currentPlayer);

			// dumpMoves(moves);
			window.displayMatch(match);


			EasyRuleEngine dumyRuleEngine = new EasyRuleEngine(match, rollTheDie);
			dumyRuleEngine.play();

//				try {
//					System.in.read();
//				} catch (Exception e) {
//				}

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
