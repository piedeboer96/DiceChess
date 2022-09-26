package gui.utility;

import chess.interfaces.IChessBoardSquare;
import chess.interfaces.IChessPiece;
import chess.utility.ChessBoardSquare;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ChessBoardGraphics extends JPanel
{
    /**
     * Provides access to the chess piece images.
     **/
    private static final Cache cache = new Cache();

    /**
     * Provides a highlight color.
     **/
    private final Color highlightColor1 = new Color(180, 180, 18, 108);

    /**
     * Provides a 2nd highlight color.
     **/
    private final Color highlightColor2 = new Color(216, 180, 18, 108);

    /**
     * Provides the 1st square color.
     **/
    private final Color squareColor1 = new Color(235,235,208);

    /**
     * Provides the 2nd square color.
     **/
    private final Color squareColor2 = new Color(119,148,85);

    /**
     * Provides the x-coordinates of each vertical line on the board.
     **/
    private final int[] xs;

    /**
     * Provides the y-coordinates of each horizontal line on the chess board.
     **/
    private final int[] ys;

    /**
     * Represents the height of each square on the board.
     **/
    private final int sh;

    /**
     * Represents the width of each square on the board.
     **/
    private final int sw;

    /**
     * Represents the last square that has been clicked on the board.
     **/
    private IChessBoardSquare lastClicked;

    /**
     * Represents the list of 'un-captured' chess pieces on the board.
     **/
    private List<IChessPiece> pieces;

    /**
     * Represents the possible squares the chess piece on the last clicked square can move to.
     **/
    private List<IChessBoardSquare> possibleMoves;

    /**
     * @param xOffset The x-coordinate of the top-left corner of the chessboard.
     * @param yOffset The y-coordinate of the top-left corner of the chessboard.
     * @param squareWidth The width of each square on the board.
     * @param squareHeight The height of each square on the board.
     **/
    public ChessBoardGraphics(int xOffset, int yOffset, int squareWidth, int squareHeight)
    {
        // In total there are 9 horizontal and vertical lines.
        xs = new int[9];
        ys = new int[9];

        // 1st line of both axis start at the specified offset.
        xs[0] = xOffset;
        ys[0] = yOffset;

        // Remaining coordinates can easily be calculated using a loop.
        for (int iteration = 1; iteration < 9; iteration++)
        {
            int previous = iteration - 1;
            xs[iteration] = xs[previous] + squareWidth;
            ys[iteration] = ys[previous] + squareHeight;
        }

        sw = squareWidth;
        sh = squareHeight;
    }

    /**
     * Determines the square the mouse's position is in.
     * Remark: Method can be used for all mouse operations to determine the square it is at.
     * @return The chessboard square the mouse is in if the mouse is on the board. Null, otherwise.
     **/
    public IChessBoardSquare determineSquare(int mouseX, int mouseY)
    {
        int file = -1;
        for (int iteration = 1; iteration < 9; iteration++)
        {
            int previous = iteration - 1;
            int lowerBound = xs[previous];
            int upperBound = xs[iteration];
            if (lowerBound <= mouseX && mouseX <= upperBound)
            {
                file = previous;
                break;
            } else if (iteration == 8) { return null; }
        }
        int rank = -1;
        for (int iteration = 1; iteration < 9; iteration++)
        {
            int previous = iteration - 1;
            int lowerBound = ys[previous];
            int upperBound = ys[iteration];
            if (lowerBound <= mouseY && mouseY <= upperBound)
            {
                rank = previous;
                break;
            } else if (iteration == 8) { return null; }
        }
        return new ChessBoardSquare(file, rank);
    }

    /**
     * Draws the chess pieces on the board.
     **/
    public void drawChessPieces(Graphics g)
    {
        if (pieces == null) { return; }
        for (IChessPiece piece : pieces)
        {
            Image img = cache.getImage(piece.toFen());
            g.drawImage(img, xs[piece.file()], ys[piece.rank()], sw, sh, null);
        }
    }

    /**
     * Draws the squares with their respective color on the board.
     **/
    public void drawSquares(Graphics g)
    {
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if ((i + j) % 2 == 0) { g.setColor(squareColor1); }
                else { g.setColor(squareColor2); }
                g.fillRect(xs[i], ys[j], sw, sh);
            }
        }
    }

    /**
     * Highlights the last clicked square.
     **/
    public void highlightClick(Graphics g)
    {
        if (lastClicked == null) { return; }
        g.setColor(highlightColor1);

        // Note a rank number represent the y-offset on a 2-dimensional grid,
        // whereas a file number represents the x-offset on a 2-dimensional grid.
        g.fillRect(xs[lastClicked.file()], ys[lastClicked.rank()], sw, sh);
    }

    /**
     * Highlights the possibilities the chess piece on the last clicked square has.
     **/
    public void highlightPossibleMoves(Graphics g)
    {
        if (possibleMoves == null) { return; }
        g.setColor(highlightColor2);
        for (IChessBoardSquare square : possibleMoves)
        {
            g.fillRect(xs[square.file()], ys[square.rank()], sw, sh);
        }
    }

    public void paint(Graphics g)
    {
        drawSquares(g);
        drawChessPieces(g);
        highlightClick(g);
        highlightPossibleMoves(g);
    }

    /**
     * Sets the last clicked square that is going to get highlighted.
     **/
    public void setLastClicked(IChessBoardSquare lastClicked) { this.lastClicked = lastClicked; }

    /**
     * Sets the possible moves that are going to get highlighted.
     **/
    public void setPossibleMoves(List<IChessBoardSquare> possibleMoves) { this.possibleMoves = possibleMoves; }

    /**
     * Sets the chess piece list, so it can track any movement changes passively.
     * Remark: When letting the AI play a move instead of a player. You must call the refresh/update method of the window
     * containing this component, so it can actually display the move that has been played.
     **/
    public void trackChessPieceList(List<IChessPiece> pieces) { this.pieces = pieces; }
}
