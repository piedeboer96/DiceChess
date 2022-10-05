package examples;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ai.easyrules.EasyRuleEngine;
import ai.easyrules.Utils;
import chess.ChessMatch;
import chess.interfaces.IChessBoardSquare;
import chess.interfaces.IChessMatch;
import chess.interfaces.IChessMove;
import chess.interfaces.IChessPiece;
import die.Die;
import gui.ChessGameWindow;
import gui.interfaces.IChessGameWindow;

public class RunGameByDummyAI {
	public static void main(String[] args) {

		RunGameByDummyAI runGame = new RunGameByDummyAI();
		runGame.mailLoop();

	}

	public void mailLoop() {

        // Creating a new window.
        IChessGameWindow window = new ChessGameWindow();

        // Creating a new match.
        IChessMatch match = new ChessMatch();

        // Displays the match
        window.displayMatch(match);

        
		
		// Getting the moves of the current player easily by

		int currentPlayer;

		
		int i=0;
		int maxMove=100;
		
		while (i++ < maxMove) {
			currentPlayer = match.getPlayer();
			// first we need fire the roll
			Die myRoll = new Die();
			char res = myRoll.roll(currentPlayer);

			List<IChessMove> moves = match.legalMovesOf(currentPlayer);

			//dumpMoves(moves);
			window.displayMatch(match);

			
			
			

			if (moves.isEmpty())
			{
				System.out.println("I cant move any pieces .... :(");
				currentPlayer = match.nextPlayer();
			}
			else {
				IChessMove chooseMyMove = chooseMyMove(moves);

				System.out.println("chessMove piece " + chooseMyMove);
				IChessBoardSquare destinationBoard=Utils.findMaxPossibilites(chooseMyMove.possibilities());
				IChessPiece piece=chooseMyMove.owner();
				System.out.println("");
				System.err.println("AI decide to move   "+ piece+ "  ----to--->>>  "+destinationBoard);
				System.out.println("Press Enter to continue");
				/*
				try{System.in.read();}
				catch(Exception e){}
				*/
				sleep(1);
				match.playMove(piece,destinationBoard);
				window.displayMatch(match);
				currentPlayer = match.getPlayer();

			}

			 
			
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

		EasyRuleEngine dumyRuleEngine = new EasyRuleEngine (realMoves);
		return dumyRuleEngine.getNextMove();

	}

	public static  void dumpMoves(List<IChessMove> moves) {
		System.out.println("Dumping Moves :");
		for (IChessMove iChessMove : moves) {
			System.out.println(iChessMove);

		}
	}
}
