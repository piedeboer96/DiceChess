package gui.utility;

import chess.interfaces.IChessBoardSquare;
import chess.interfaces.IChessMatch;
import chess.interfaces.IChessMove;
import chess.interfaces.IChessPiece;
import chess.units.Queen;
import gui.interfaces.IPlayer;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class Player implements IPlayer
{
    private final ChessBoardGraphics board;

    private IChessBoardSquare lastClicked;
    private List<IChessMove> legalMoves;
    private List<IChessBoardSquare> movePossibilities;
    private IChessPiece selectedPiece;
    private IChessMatch match;


    public Player(ChessBoardGraphics board) { this.board = board; }

    private void deselect()
    {
        lastClicked = null;
        selectedPiece = null;
        board.setLastClicked(null);
        board.setPossibleMoves(null);
    }

    @Override public void mouseClicked(MouseEvent e) {}

    @Override public void mousePressed(MouseEvent e)
    {
        int x = e.getX();
        int y = e.getY();

        // Let's start by getting the square we clicked on.
        IChessBoardSquare selected = board.determineSquare(x, y);

        // If we did not click on the board, then we can consider this action as a deselection.
        if (selected == null) { deselect(); }
        // If we clicked on the board and no square was clicked previously, then let's see what we can get.
        else if (lastClicked == null)
        {
            lastClicked = selected;
            board.setLastClicked(selected);
            if (match != null && legalMoves != null)
            {
                // If there are any legal moves at all, find the moves we can make for the selected square.
                // (Note: That's only possible when the clicked square contains a chess piece)
                selectedPiece = match.get(selected);
                if (selectedPiece != null)
                {
                    movePossibilities = new ArrayList<>();
                    for (IChessMove move : legalMoves)
                    {
                        if (move.owner().equals(lastClicked))
                        {
                            movePossibilities.addAll(move.possibilities());
                        }
                    }
                    if (movePossibilities.size() > 0) { board.setPossibleMoves(movePossibilities); }
                }
            }

        }
        else if (selected.equals(lastClicked)) { deselect(); }
        else if (match != null && selectedPiece != null && movePossibilities.size() > 0)
        {
            for (IChessBoardSquare square : movePossibilities)
            {
                if (square.equals(selected))
                {
                    match.playMove(selectedPiece, square);

                    if (selectedPiece.promotable())
                    {
                        char fen;
                        if (selectedPiece.team() == 0) { fen = 'q'; }
                        else { fen = 'Q'; }
                        IChessPiece queen = new Queen(fen, selected.file(), selected.rank());
                        match.promote(selectedPiece, queen);
                    }

                    legalMoves = match.legalMovesOf(match.getPlayer());
                }
            }
            deselect();
        } else { deselect(); }

        board.updateUI();
    }

    @Override public void mouseReleased(MouseEvent e) {}

    @Override public void mouseEntered(MouseEvent e) {}

    @Override public void mouseExited(MouseEvent e) {}

    public void playMatch(IChessMatch match)
    {
        board.trackChessPieceList(match.pieces());
        legalMoves = match.legalMovesOf(match.getPlayer());
        this.match = match;
        board.updateUI();
    }
}
