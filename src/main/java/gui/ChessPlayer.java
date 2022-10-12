package gui;

import gui.utility.Chessboard;

import java.awt.event.MouseEvent;

public final class ChessPlayer extends Player
{
    @Override public void mousePressed(MouseEvent e)
    {
        if (match == null || team != match.getPlayer()) { return; }
        else if (availableMoves == null) { availableMoves = match.legalMovesOf(team); }
        var window = (ChessWindow) e.getSource();
        int[] coordinates = window.applyCoordinateCorrection(e.getX(), e.getY());
        var chessboard = (Chessboard) window.findInteractionSource(coordinates[0], coordinates[1]);
        var selected = chessboard.determineSquare(coordinates[0], coordinates[1]);
        var highlighter = chessboard.getHighlighter();
        digest(selected, highlighter);
        window.refresh();
    }
}
