package gui;

import chess.interfaces.IChessBoardSquare;
import chess.interfaces.IChessMatch;
import chess.interfaces.IChessMove;
import chess.interfaces.IChessPiece;
import chess.units.Queen;
import gui.interfaces.IChessBoardGraphics;
import gui.interfaces.IHumanPlayer;
import gui.interfaces.IPromotionWindow;
import gui.utility.ChessBoardGraphics;
import gui.utility.PromotionWindow;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class HumanPlayer implements IHumanPlayer
{
    private IChessBoardGraphics boardGraphics;
    private List<IChessBoardSquare> availableDestinations;
    private IChessBoardSquare lastClicked;
    private List<IChessMove> legalMoves;
    private IChessMatch match;
    private int team;

    private void deselect()
    {
        lastClicked = null;
        availableDestinations = null;
        boardGraphics.setLastClicked(null);
        boardGraphics.setPossibleMoves(null);
    }

    private void determinePossibleMoves(IChessBoardSquare square)
    {
        availableDestinations = new ArrayList<>();
        for (IChessMove move : legalMoves)
        {
            if (move.owner().equals(square))
            {
                availableDestinations.addAll(move.possibilities());
            }
        }
        boardGraphics.setPossibleMoves(availableDestinations);
    }

    @Override public void mouseClicked(MouseEvent e) {}

    @Override public void mousePressed(MouseEvent e)
    {
        if ((team != -1 && team != match.getPlayer()) || legalMoves == null) { return; }

        int x = e.getX();
        int y = e.getY();
        IChessBoardSquare square = boardGraphics.determineSquare(x, y);

        if (square == null) { deselect(); }
        else if (lastClicked == null)
        {
            lastClicked = square;
            boardGraphics.setLastClicked(square);
            determinePossibleMoves(square);
        }
        else if (availableDestinations != null && availableDestinations.size() > 0)
        {
            for (IChessBoardSquare destination : availableDestinations)
            {
                if (destination.equals(square))
                {
                    IChessPiece selected = match.get(lastClicked);
                    match.playMove(selected, square);

                    if (selected.promotable())
                    {
                        //Todo: Ask to what type to promote.
                        IPromotionWindow window = new PromotionWindow();
                        char fen = window.getSelection();
                        if (team == 1) { fen = Character.toUpperCase(fen); }
                        IChessPiece queen = new Queen(fen, selected.file(), selected.rank());
                        match.promote(selected, queen);
                    }
                    break;
                }
            }
            deselect();
        }
        else { deselect(); }

        boardGraphics.updateUI();
    }

    @Override public void mouseReleased(MouseEvent e) {}

    @Override public void mouseEntered(MouseEvent e) {}

    @Override public void mouseExited(MouseEvent e) {}

    public void play()
    {
        if (team == -1) { legalMoves = match.legalMovesOf(match.getPlayer()); }
        else { legalMoves = match.legalMovesOf(team); }

        // If there are no moves left, the game is over.
        if (legalMoves.size() == 0) { throw new IllegalStateException("Have no moves left."); }
    }

    public void playIn(int team, IChessMatch match)
    {
        this.team = team;
        this.match = match;
    }

    public void setPlayground(IChessBoardGraphics boardGraphics) { this.boardGraphics = boardGraphics; }
}
