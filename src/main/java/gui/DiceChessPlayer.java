package gui;

import java.awt.event.MouseEvent;

import chess.interfaces.IChessboardSquare;
import gui.interfaces.IChessboard;
import gui.interfaces.IHighlighter;
import gui.interfaces.IWindow;
import gui.utility.Dice;

/**
 * Provides a player for a 'dice chess' game.
 **/
public final class DiceChessPlayer extends Player {
	private char[] rollResults;

	public DiceChessPlayer(boolean playerCanPlayOnBothTeams) {
		super(playerCanPlayOnBothTeams);
	}

	@Override
	protected void loadLegalMoves() {
		if (rollResults == null) {
			throw new IllegalCallerException("You forgot to roll the dice before making a move!");
		}
		legalMoves = match.legalMovesOf(team, rollResults);
		if (legalMoves.size() == 0) {
			if (match.playerIsCheckMated(team)) {
				throw new IllegalStateException("Bro u're done");
			}
			System.out.println("Player " + team + " has no moves, therefore moving to the next player.");
			team = match.nextPlayer();
			onPlay();
		}
	}

	@Override
	protected void onPlay() {
		legalMoves = null;
		rollResults = null;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// If no chess match is assigned to our player or the turn to play is not ours,
		// then we are not supposed to do anything more.
		if (match == null || match.getPlayer() != team) {
			System.out.println("Player interacted too early with the window!");
			return;
		}

		// Getting the window the player has interacted with.
		IWindow window = (IWindow) e.getSource();

		// Determines the mouse position relative to the content that gets displayed.
		int[] positionInDisplayedContent = window.determineLocationInContentBody(e.getX(), e.getY());

		// Gets the object that has been clicked.
		Object clickSource = window.findInteractionSource(positionInDisplayedContent[0], positionInDisplayedContent[1]);

		// Determining whether the player has clicked on the chessboard or the panel containing dice.
		// If the player clicked on the board and the player has already rolled, then interactions with the board are allowed.
		if (clickSource instanceof IChessboard && rollResults != null) {
			// Getting the chessboard the player is interacting with and the highlighter responsible for the eye candy on the board.
			IChessboard chessboard = (IChessboard) clickSource;
			IHighlighter highlighter = chessboard.getHighlighter();

			// Determine the square that has been clicked.
			IChessboardSquare selectedSquare = chessboard.determineSquare(positionInDisplayedContent[0],
					positionInDisplayedContent[1]);

			// If no square has been selected, then this action can be interpreted as deselection the previous move.
			if (selectedSquare == null) {
				deselect(highlighter);
			}

			// Else if the last click resulted in a deselection (meaning last clicked square is null), then this
			// click action can be interpreted as a new selection.
			else if (lastSelectedSquare == null) {
				onSelect(selectedSquare, highlighter);
			}

			// Else if the last clicked square is not null, then the action could be interpreted as either
			// a move or a deselection. So, if the current selected square is the same as the last clicked square,
			// then this action must be a deselection.
			else if (selectedSquare.equals(lastSelectedSquare)) {
				deselect(highlighter);
			}

			// Else if the action was not a deselection, then perhaps this action is a valid move.
			// Hence, attempt to make a move. Regardless of whether the move was valid, this action should always result
			// into a deselection.
			else {
				play(selectedSquare);
				deselect(highlighter);
			}
		}

		// Else if the player clicked on the panel containing the dice and hasn't rolled yet, then the dice should be rolled.
		else if (clickSource instanceof Dice && rollResults == null) {
			// Getting the dice
			Dice dice = (Dice) clickSource;

			// Letting it roll and reading the results.
			rollResults = dice.roll(team);
		}

		// Update the window to display the latest changes that (may) have been made.
		window.refresh();
	}
}
