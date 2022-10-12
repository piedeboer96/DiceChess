package gui;

import chess.interfaces.IChessMove;
import chess.interfaces.IChessboardSquare;
import gui.interfaces.IHighlighter;
import gui.utility.Chessboard;
import gui.utility.Dice;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public final class DiceChessPlayer extends Player
{
    private List<IChessMove> constrained;
    private char[] results;

    @Override public void mousePressed(MouseEvent e)
    {
        if (match == null || team != match.getPlayer()) { return; }
        else if (availableMoves == null)
        {
            results = null;
        }
        var window = (DiceChessWindow) e.getSource();
        int[] coordinates = window.applyCoordinateCorrection(e.getX(), e.getY());
        var source =  window.findInteractionSource(coordinates[0], coordinates[1]);
        if (source instanceof Dice && results == null)
        {
            var dice = (Dice) source;
            results = dice.roll(team);
            availableMoves = match.filterMovesOf(team, results);
            if (availableMoves.size() == 0)
            {
                deselect(true);
                team = match.nextPlayer();
            }
        }
        else if (source instanceof Chessboard)
        {
            var chessboard = (Chessboard) source;
            var selected = chessboard.determineSquare(coordinates[0], coordinates[1]);
            var highlighter = chessboard.getHighlighter();
            digest(selected, highlighter);
        }
        window.refresh();
    }
}
