package examples;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import ai.easyrules.Action;
import ai.easyrules.EasyRuleEngine;
import chess.ChessMatch;
import gui.DiceChessWindow;
import gui.Player;

public class RunGameAIvsHuman {
	public static void main(String[] args) {

		RunGameAIvsHuman runGame = new RunGameAIvsHuman();
		runGame.mainLoop();

	}

	public void mainLoop() {

		// Creating a new window.
		DiceChessWindow window = new DiceChessWindow(800, 640, false);

		String startPos;
		// full pieces
		startPos = "8/8/8/p/1K6/8/8/k w - - 0 1";
		startPos = "K/8/8/4b/8/5Q2/8/k w - - 0 1";
		startPos = "1n1qk3/1r6/8/3p4/4pR2/P2K1p1b/P2p1P2/8 w - - 0 1";
		startPos = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";

		// Creating a new match.
		ChessMatch match = new ChessMatch(startPos);
		match.loadKings();

		// Displays the match
		window.display(match);

		// Getting the moves of the current player easily by

		while (true) {

			int currentPlayer = match.getPlayer();
			if (currentPlayer == 0) {

				// first we need fire the roll
				char[] roll = window.getDice().roll(currentPlayer);
				char rollOne = roll[0];
				char rollTwo = roll[1];
				System.out.println("RollOne = " + rollOne + "  RollTwo = " + rollTwo);
				EasyRuleEngine dumyRuleEngine = new EasyRuleEngine(match, rollOne, rollTwo);
				if (dumyRuleEngine.play() == Action.FINISH_MATCH) { endGame(window); }
				 
			} else {
				var player = window.getPlayer();
				player.playIn(1, match);
			}

			sleep(500);
			window.refresh();

		}
	}

	private void endGame(DiceChessWindow window) {
		while (true) {
			window.refresh();
			sleep(500);
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
