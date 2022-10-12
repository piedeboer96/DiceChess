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
            availableMoves = match.legalMovesOf(team);
        }
        var window = (DiceChessWindow) e.getSource();
        int[] coordinates = window.applyCoordinateCorrection(e.getX(), e.getY());
        var source =  window.findInteractionSource(coordinates[0], coordinates[1]);
        if (source instanceof Dice && results == null)
        {
            var dice = (Dice) source;
            results = dice.roll(team);
            if (results[0] == results[1]) { constrained = availableMoves; }
            else
            {
                constrained = new ArrayList<>();
                for (IChessMove move : availableMoves)
                {
                    var chessPiece = move.owner();
                    var fen = chessPiece.toFen();
                    if (fen == results[0] || fen == results[1])
                    {
                        constrained.add(move);
                    }
                }
            }
        }
        else if (source instanceof Chessboard && results != null)
        {
            var chessboard = (Chessboard) source;
            var selected = chessboard.determineSquare(coordinates[0], coordinates[1]);
            var highlighter = chessboard.getHighlighter();
            digest(selected, highlighter);
        }
        window.refresh();
    }

    @Override protected void select(IChessboardSquare selected)
    {
        selectedPiece = match.get(selected);
        if (selectedPiece == null || availableMoves == null) { return; }
        availableDestinations = new ArrayList<>();
        for (var chessMove : constrained)
        {
            if (chessMove.owner().equals(selectedPiece))
            {
                availableDestinations.addAll(chessMove.possibilities());
            }
        }
    }
}
