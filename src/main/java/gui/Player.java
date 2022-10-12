package gui;

import chess.interfaces.IChessMatch;
import chess.interfaces.IChessMove;
import chess.interfaces.IChessPiece;
import chess.interfaces.IChessboardSquare;
import gui.interfaces.IHighlighter;

import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.List;

public abstract class Player extends MouseAdapter
{
    protected List<IChessboardSquare> availableDestinations;
    protected List<IChessMove> availableMoves;
    protected IChessPiece selectedPiece;
    protected IChessMatch match;
    protected int team;

    protected void clearHighlights(IHighlighter highlighter, boolean clearSelectableOptions)
    {
        highlighter.rememberSelection(null);
        highlighter.rememberDestinations(null);
        if (clearSelectableOptions) { highlighter.rememberSelectableOptions(null); }
    }

    protected void deselect(boolean clearAvailableMoves)
    {
        availableDestinations = null;
        selectedPiece = null;
        if (clearAvailableMoves) { availableMoves = null; }
    }

    protected void digest(IChessboardSquare selected, IHighlighter highlighter)
    {
        if (selected != null)
        {
            if (selectedPiece != null)
            {
                play(selected);
                clearHighlights(highlighter, true);

            }
            else
            {
                select(selected);
                highlightSelection(highlighter);
            }
        }
        else
        {
            clearHighlights(highlighter, false);
            deselect(false);
        }
    }

    protected void highlightSelection(IHighlighter highlighter)
    {
        highlighter.rememberSelection(selectedPiece);
        highlighter.rememberDestinations(availableDestinations);
    }

    protected void play(IChessboardSquare selected)
    {
        if (selectedPiece == null || availableDestinations == null) { return; }
        boolean found = false;
        for (var destination : availableDestinations)
        {
            found = destination.equals(selected);
            if (found)
            {
                match.playMove(selectedPiece, selected);
                deselect(true);
                team = match.getPlayer();
                break;
            }
        }
        if (!found) { deselect(false); }
    }

    protected void select(IChessboardSquare selected)
    {
        selectedPiece = match.get(selected);
        if (selectedPiece == null || availableMoves == null) { return; }
        availableDestinations = new ArrayList<>();
        for (var chessMove : availableMoves)
        {
            if (chessMove.owner().equals(selectedPiece))
            {
                availableDestinations.addAll(chessMove.possibilities());
            }
        }
    }

    public void playIn(int team, IChessMatch match)
    {
        this.team = team;
        this.match = match;
    }
}
