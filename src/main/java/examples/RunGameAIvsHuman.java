package examples;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ai.easyrules.BoardAction;
import ai.easyrules.EasyRuleEngine;
import ai.easyrules.ResultAI;
import chess.ChessMatch;
import chess.interfaces.IChessboardSquare;
import chess.utility.ChessboardSquare;
import gui.DiceChessWindow;
import gui.interfaces.IHighlighter;
import gui.utility.Highlighter;

public class RunGameAIvsHuman {

	IChessboardSquare fromDestination = new ChessboardSquare(0, 0);
	List<IChessboardSquare> possibleDestinations = new ArrayList<IChessboardSquare>();

	private boolean isHighligh = false;
	private ResultAI play;

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

		window.addListenerHighlightLastMove(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				IHighlighter highlighter = window.getHighlighter();
				if (isHighligh) {
					highlighter.rememberSelection(null);
					highlighter.rememberDestinations(null);
				} else {

					highlighter.rememberSelection(fromDestination);
					highlighter.rememberDestinations(possibleDestinations);
				}
				isHighligh=!isHighligh;	
			}
		});
		
		window.addListenerShowDetails(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				window.clearConsole();
				ArrayList<String> bestMsgStack = play.bestMsgStack;
				for (String string : bestMsgStack) {
					window.setConsoleText(string);
				}
				window.setConsoleText("Moved Fen |"+play.fen+"| from ["+play.fromFile+","+play.fromRank+"] -->  to ["+play.toFile+","+play.toRank+"]");
			}
		});
		window.addListenerClearConsole(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				window.clearConsole();
			}
		});


		while (true) {

			int currentPlayer = match.getPlayer();
			if (currentPlayer == 0) {

				// first we need fire the roll
				char[] roll = window.getDice().roll(currentPlayer);
				char rollOne = roll[0];
				char rollTwo = roll[1];

				System.out.println("RollOne = " + rollOne + "  RollTwo = " + rollTwo);

				EasyRuleEngine dumyRuleEngine = new EasyRuleEngine(match, rollOne, rollTwo);

				 play = dumyRuleEngine.play();
				possibleDestinations.clear();
				if (play.action == BoardAction.NO_MOVE) {
					window.setConsoleText("AI Can't Move");
				} else {
					possibleDestinations.add(new ChessboardSquare(play.toFile, play.toRank));
					fromDestination = new ChessboardSquare(play.fromFile, play.fromRank);
					char fen = play.fen;
					int fromRank = play.fromRank;
					int fromFile = play.fromFile;
					int toFile = play.toFile;
					int toRank = play.toRank;
					window.setConsoleText("AI Move fen " + fen + " From (" + fromFile + ", " + fromRank + ") --> " + " TO  (" + toFile + ", " + toRank + ")");
				}
				if (play.action == BoardAction.FINISH_MATCH) {
					window.setConsoleText("Match is Over");
					endGame(window);
				}

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
