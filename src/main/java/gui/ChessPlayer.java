package gui;

import java.awt.event.MouseEvent;

import chess.interfaces.IChessboardSquare;
import gui.interfaces.IChessboard;
import gui.interfaces.IHighlighter;
import gui.interfaces.IWindow;
import gui.utility.Chessboard;

/**
 * Provides a human player for a normal chess game.
 **/
public final class ChessPlayer extends Player
{
    public ChessPlayer(boolean playerCanPlayOnBothTeams) { super(playerCanPlayOnBothTeams); }

    @Override protected void loadLegalMoves() { legalMoves = match.legalMovesOf(team); }

    @Override protected void onPlay() { legalMoves = null; }

    @Override public void mousePressed(MouseEvent e)
    {
        // If no chess match is assigned to our player or the turn to play is not ours,
        // then we are not supposed to do anything more.
        if (match == null || match.getPlayer() != team)
        {
            System.out.println("Player interacted too early with the window!");
            return;
        }

        // Get the window that has been clicked.
        IWindow window = (IWindow) e.getSource();

        // Determines the mouse position relative to the content that gets displayed.
        int[] positionInDisplayedContent = window.determineLocationInContentBody(e.getX(), e.getY());

        // Gets the object that has been clicked.
        Object clickSource = window.findInteractionSource(positionInDisplayedContent[0], positionInDisplayedContent[1]);

        // Chess player is made to only interact with the chessboard. So throw an error if something is obtained.
        boolean sourceIsChessboard = clickSource instanceof Chessboard;
        if (!sourceIsChessboard) { throw new IllegalStateException("Chess player has not been assigned to a window with only a chessboard!"); }

        // Getting the chessboard the player is interacting with and the highlighter responsible for the eye candy on the board.
        IChessboard chessboard = (IChessboard) clickSource;
        IHighlighter highlighter = chessboard.getHighlighter();

        // Determine the square that has been clicked.
        IChessboardSquare selectedSquare = chessboard.determineSquare(positionInDisplayedContent[0], positionInDisplayedContent[1]);

        // If no square has been selected, then this action can be interpreted as deselection the previous move.
        if (selectedSquare == null) { deselect(highlighter); }

        // Else if the last click resulted in a deselection (meaning last clicked square is null), then this
        // click action can be interpreted as a new selection.
        else if (lastSelectedSquare == null) { onSelect(selectedSquare, highlighter); }

        // Else if  the last clicked square is not null, then the action could be interpreted as either
        // a move or a deselection. So, if the current selected square is the same as the last clicked square,
        // then this action must be a deselection.
        else if (selectedSquare.equals(lastSelectedSquare)) { deselect(highlighter); }

        // Else if the action was not a deselection, then perhaps this action is a valid move.
        // Hence, attempt to make a move. Regardless of whether the move was valid, this action should always result
        // into a deselection.
        else { play(selectedSquare); deselect(highlighter); }

        // Finally, update the window to display the latest changes.
        window.refresh();
    }
}
