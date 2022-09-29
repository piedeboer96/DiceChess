import chess.ChessMatch;
import chess.interfaces.IChessBoardSquare;
import chess.interfaces.IChessMatch;
import chess.interfaces.IChessMove;
import chess.interfaces.IChessPiece;
import die.Roll;
import engine.test.DumyRuleEngine;
import gui.Window;
import gui.utility.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RunGameByDummyAI {
	public static void main(String[] args) {

		RunGameByDummyAI runGame = new RunGameByDummyAI();
		runGame.mailLoop();

	}

	public void mailLoop() {

		IChessMatch match = new ChessMatch();
		Window window = new Window();

		// Getting the moves of the current player easily by

		int currentPlayer;

		
		int i=0;
		int maxMove=100;
		
		while (i++ < maxMove) {
			currentPlayer = match.getPlayer();
			// first we need fire the roll
			Roll myRoll = new Roll();
			char res = myRoll.roll();

			List<IChessMove> moves = match.legalMovesOf(currentPlayer);

			//dumpMoves(moves);
			window.displayMatch(match);

			System.out.println("Roll " + res);

			// now let search in legalmove if we can move
			List<IChessMove> realMoves = new ArrayList<>();

			for (IChessMove iChessMove : moves) {
				char fen = iChessMove.owner().toFen();
				if (Character.toUpperCase(fen) == Character.toUpperCase(res)) {
					realMoves.add(iChessMove);
				}
			}

			//dumpMoves(realMoves);

			if (realMoves.isEmpty())
			{
				System.out.println("I cant move any pieces .... :(");
				currentPlayer = match.nextPlayer();
			}
			else {
				IChessMove chooseMyMove = chooseMyMove(realMoves);

				System.out.println("AI decide to move " + chooseMyMove);
				IChessPiece piece=chooseMyMove.owner();
				IChessBoardSquare destinationBoard=chooseMyMove.possibilities().get(0);
				match.playMove(piece,destinationBoard);

			}

			sleep(1);
			
			System.out.println("New player is " + currentPlayer);
		}

	}

	private void sleep(int sec) {
		try {
			TimeUnit.SECONDS.sleep(sec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private IChessMove chooseMyMove(List<IChessMove> realMoves) {

		DumyRuleEngine dumyRuleEngine = new DumyRuleEngine (realMoves);
		return dumyRuleEngine.getNextMove();

	}

	public static  void dumpMoves(List<IChessMove> moves) {
		System.out.println("Dumping Moves :");
		for (IChessMove iChessMove : moves) {
			System.out.println(iChessMove);

		}
	}
}
