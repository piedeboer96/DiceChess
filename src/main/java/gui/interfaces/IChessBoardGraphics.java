package gui.interfaces;

import chess.interfaces.IChessBoardSquare;
import chess.interfaces.IChessPiece;

import java.util.List;

public interface IChessBoardGraphics
{
    /**
     * Determines whether there is a chessboard square located on a selected (x,y)-coordinate.
     * @param x The x-coordinate of the point that has been selected.
     *          (In most cases, this is the x-coordinate of the mouse pointer)
     * @param y The y-coordinate of the point that has been selected.
     *          (In most cases, this is the y-coordinate of the mouse pointer)
     * @return The chessboard square the point is located on. Null, if the point does not relate to a chessboard square.
     **/
    IChessBoardSquare determineSquare(int x, int y);

    /**
     * Sets the square that has been clicked to be highlighted.
     * Remark: This doesn't automatically update the UI. You have to call the updateUI() method.
     **/
    void setLastClicked(IChessBoardSquare square);

    /**
     * Sets the squares that need to be highlighted, where each highlighted square represents a possible destination.
     **/
    void setPossibleMoves(List<IChessBoardSquare> possiblileMoves);

    /**
     * Tracks the chess pieces that should be displayed on the board.
     **/
    void trackChessPieces(List<IChessPiece> pieces);

    /**
     * Updates the board graphics.
     **/
    void updateUI();
}
